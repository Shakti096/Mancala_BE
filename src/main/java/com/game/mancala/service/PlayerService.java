package com.game.mancala.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.game.mancala.entity.Player;
import com.game.mancala.validator.PlayerMoveValidator;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.game.mancala.constants.ApiServiceConstants;
import com.game.mancala.entity.Game;
import com.game.mancala.exception.ApiServiceException;
import com.game.mancala.repository.GameRepository;

/**
 * This is the class to perform the mancala moves
 *
 */
@Service
public class PlayerService {

	private final GameRepository gameRepository;
	private final PlayerMoveService playerMoveService;

	public PlayerService(GameRepository gameRepository,
						 PlayerMoveService playerMoveService) {
		this.gameRepository = gameRepository;
		this.playerMoveService = playerMoveService;
	}

	/**
	 * This method is used to play the game
	 * 
	 * @param gameId - unique Id of the game
	 * @param pitId  - Pit number to start the game
	 * @return Map<String, String> - containing the position and count of coins in each position
	 */
	public Map<String, String> playMove(int gameId, int pitId) {

		try {
			Game game = gameRepository.findByIdAndIsActive(gameId,
					Boolean.TRUE.toString());
			if (game != null) {
				PlayerMoveValidator.validatePlayerTurn(game.getPlayers(), pitId);
				return playerMoveService.moveStones(pitId, game);
			} else {
				throw new ApiServiceException(HttpStatus.NOT_FOUND.getReasonPhrase(),
						ApiServiceConstants.GAME_LIST_TABLE_ERROR_MSG);
			}
		} catch (DataAccessException e) {
			throw new ApiServiceException(HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase(),
					ApiServiceConstants.GAME_DATA_UNAVAILABLE);
		}
	}

	public List<Player> initializeGamePlayers(Game game) {
		//create player 1 and  player 2 record
		// and add it to the list and return the list
		List<Player> players = new ArrayList<>();
		players.add(new Player(ApiServiceConstants.PLAYER1_NAME,ApiServiceConstants.FLAG_Y,game));
		players.add(new Player(ApiServiceConstants.PLAYER2_NAME,ApiServiceConstants.FLAG_N,game));

		return players;
	}

}
