
package com.game.mancala.service;

import com.game.mancala.configuration.MancalaRuleConfiguration;
import com.game.mancala.configuration.MancalaRuleConfiguration.BoardRule;
import com.game.mancala.constants.ApiServiceConstants;
import com.game.mancala.entity.Game;
import com.game.mancala.entity.GameDetails;
import com.game.mancala.entity.Player;
import com.game.mancala.exception.ApiServiceException;
import com.game.mancala.repository.GameRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This is the service class which perform the movement of the coins from pits
 */
@Service
public class PlayerMoveService {

    private final GameRepository gameRepository;
    private final MancalaRuleConfiguration mancalaRuleConfiguration;

    public PlayerMoveService(GameRepository gameRepository,
                             MancalaRuleConfiguration mancalaRuleConfiguration) {
        this.gameRepository = gameRepository;
        this.mancalaRuleConfiguration = mancalaRuleConfiguration;
    }

    /**
     * This method perform the movement of stones from a pit
     *
     * @param startPosition - String value provided from user input
     * @param game          object of game
     * @return Map<String, String> - containing the position and count of coins in each position
     * @throws ApiServiceException - Complex object thrown in case of exception
     */
    @Transactional
    public Map<String, String> moveStones(Integer startPosition, Game game) throws ApiServiceException {
        Integer position = startPosition;
        int stonesInPosition;
        try {
            //STEP 1 : fetch the all GameDetails(pits) data for the given game
			Map<Integer, GameDetails> gameDetailsMap = game.getGameDetails().stream()
                    .collect(Collectors.toMap(GameDetails::getPosition, gameDetails -> gameDetails));


			if (gameDetailsMap.isEmpty()  || gameDetailsMap.get(position) == null || gameDetailsMap.get(position).getCount()==0) {
                throw new ApiServiceException(ApiServiceConstants.USER_ACTION_NOT_ALLOWED,
                        ApiServiceConstants.NO_STONES_IN_PIT);
            }

            //STEP 2 : remove the pit stones from given position
			GameDetails gameDetails = gameDetailsMap.get(position);
            stonesInPosition = gameDetails.getCount();
            gameDetails.setCount(ApiServiceConstants.INTEGER_ZERO);

			gameDetailsMap.put(position,gameDetails);
            Player player = getCurrentPlayerData(game);

            //STEP 3: move the stones in order
            for (int i = 0; i < stonesInPosition; i++) {
                //find the next pit details from game rule
                BoardRule gameRule = mancalaRuleConfiguration.getBoardRule().get(position);
                position = gameRule.getNextPosition();

                //drop a stone in the next pit if allowed
                GameDetails gameDetails1 = gameDetailsMap.get(position);
                if (ApiServiceConstants.PIT_7==position
                        && ApiServiceConstants.PLAYER2_NAME.equalsIgnoreCase(player.getUserName())) {
                    i = i - 1;
                } else if (ApiServiceConstants.PIT_14==position
                        && ApiServiceConstants.PLAYER1_NAME.equalsIgnoreCase(player.getUserName())) {
                    i = i - 1;
                } else {
                    gameDetails1.setCount(gameDetails1.getCount() + 1);
                }
                gameDetailsMap.put(position, gameDetails1);

                //check for termination conditions
                if (shouldContinue(stonesInPosition, i, gameDetails1)
                        && !isValidMove(position)) {
                    stonesInPosition = gameDetails1.getCount();
                    gameDetailsMap.put(position,resetCounter(gameDetails1));
                    i = -1;
                } else if (shouldTerminate(stonesInPosition, i, gameDetails1)
                        && !(isValidMove(position))) {
                    updateKalah(position, gameDetailsMap, player, gameDetails1);
                    updatePlayerStatus(game);
                }
            }
            game.setGameDetails(new ArrayList<>(gameDetailsMap.values()));
            updateGameTerminationFlag(gameDetailsMap,game);
            gameRepository.save(game);
            return createResponseMap(game);
        } catch (DataAccessException e) {
            throw new ApiServiceException(ApiServiceConstants.MOVE_TABLE_ERROR_CODE,
                    ApiServiceConstants.MOVE_TABLE_ERROR_MSG);
        }
    }

    private void updateGameTerminationFlag(Map<Integer, GameDetails> gameDetailsMap, Game game) {

        boolean player1Status = false;
        boolean player2Status = false;
        for (Map.Entry<Integer, GameDetails> integerGameDetailsEntry : gameDetailsMap.entrySet()) {
            if (integerGameDetailsEntry.getKey() > 0 && integerGameDetailsEntry.getKey() < 7) {
                if (integerGameDetailsEntry.getValue().getCount() != 0) {
                    player1Status = true;
                }
            }
            else if (integerGameDetailsEntry.getKey() > 7 && integerGameDetailsEntry.getKey() < 14) {
                if (integerGameDetailsEntry.getValue().getCount() != 0) {
                    player2Status = true;
                }
            }
        }
        boolean result = player1Status || player2Status;
        game.setIsActive(Boolean.toString(result));

    }

    private void updatePlayerStatus(Game game) {
        List<Player> playerList = updatePlayerMoveFlag(game);
        if(!playerList.isEmpty()){
            game.setPlayers(playerList);
        }
    }

    private void updateKalah(Integer position, Map<Integer, GameDetails> gameDetailsMap, Player player, GameDetails gameDetails1) {
        if (ApiServiceConstants.PLAYER1_NAME.equalsIgnoreCase(player.getUserName())
                && position >= 1 && position <= 6) {
            //add stones from position and opposite position to house7
            updateKalahOnTermination(position, gameDetailsMap, gameDetails1, ApiServiceConstants.PIT_7);
        } else if (ApiServiceConstants.PLAYER2_NAME.equalsIgnoreCase(player.getUserName())
                && position >= 8 && position <= 13) {
            //add stones from position and opposite position to house14
            updateKalahOnTermination(position, gameDetailsMap, gameDetails1, ApiServiceConstants.PIT_14);
        }
    }

    private void updateKalahOnTermination(Integer position, Map<Integer, GameDetails> gameDetailsMap, GameDetails gameDetails1, Integer kalahPosition) {
        Integer oppPosition = getOppositePitPosition(position);
        GameDetails detailsOfOppositePit = gameDetailsMap.get(oppPosition);
        int count = gameDetails1.getCount() + detailsOfOppositePit.getCount();
        gameDetailsMap.put(oppPosition, resetCounter(detailsOfOppositePit));
        gameDetailsMap.put(position, resetCounter(gameDetails1));
        GameDetails kalahDetails = gameDetailsMap.get(kalahPosition);
        kalahDetails.setCount(kalahDetails.getCount() + count);
        gameDetailsMap.put(kalahPosition, kalahDetails);
    }

    private Integer getOppositePitPosition(Integer position) {
        return ApiServiceConstants.PIT_14 - position;
    }

    private boolean shouldTerminate(int stonesInPosition, int i, GameDetails gameDetails1) {
        return i == (stonesInPosition - 1) && gameDetails1.getCount() == 1;
    }

    /*
        check for last i value i.e. end of stones and the stone should be only one stone in that pit
     */
    private boolean shouldContinue(int stonesInPosition, int i, GameDetails gameDetails1) {
        return i == (stonesInPosition - 1) && gameDetails1.getCount() != 1;
    }

    private boolean isValidMove(Integer position) {
        return ApiServiceConstants.PIT_7 == position
                || ApiServiceConstants.PIT_14 == position;
    }

    private Map<String, String> createResponseMap(Game game) {

        Map<String, String> response = new HashMap<>();
        for (GameDetails gamedetails : game.getGameDetails()) {
            response.put(gamedetails.getPosition().toString(), gamedetails.getCount().toString());
        }
        return response;
    }

    private Player getCurrentPlayerData(Game game) {
        return game.getPlayers().stream().filter(
                userChanceRec -> ApiServiceConstants.FLAG_Y.equalsIgnoreCase(userChanceRec.getChanceFlag()))
                .findAny().orElse(null);
    }

    private GameDetails resetCounter(GameDetails gameDetails) {
        gameDetails.setCount(ApiServiceConstants.INTEGER_ZERO);
        return gameDetails;
    }

    private List<Player> updatePlayerMoveFlag(Game game) {
        List<Player> playerList = new ArrayList<>();
        for (Player player : game.getPlayers()) {
            if (ApiServiceConstants.FLAG_Y.equalsIgnoreCase(player.getChanceFlag())) {
                player.setChanceFlag(ApiServiceConstants.FLAG_N);
            } else {
                player.setChanceFlag(ApiServiceConstants.FLAG_Y);
            }
            playerList.add(player);
        }
        return playerList;

    }

}
