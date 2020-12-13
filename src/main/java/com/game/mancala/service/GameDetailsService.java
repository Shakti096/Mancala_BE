package com.game.mancala.service;

import com.game.mancala.constants.ApiServiceConstants;
import com.game.mancala.entity.Game;
import com.game.mancala.entity.GameDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameDetailsService {

    public List<GameDetails> initializeGameDetails(Game game) {
        List<GameDetails> gameDetails = new ArrayList<>();
        //initialize all the pits, positions and user details
        for (int i = 1; i <= 14; i++) {
            GameDetails gameDetail = new GameDetails();
            gameDetail.setGame(game);
            if (i == 7 || i == 14) {
                gameDetail.setCount(ApiServiceConstants.INTEGER_ZERO);
            } else {
                gameDetail.setCount(ApiServiceConstants.INTEGER_SIX);
            }
            gameDetail.setPosition(i);
            gameDetails.add(gameDetail);
        }
        return gameDetails;
    }
}
