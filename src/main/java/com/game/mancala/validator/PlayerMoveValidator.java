package com.game.mancala.validator;

import com.game.mancala.constants.ApiServiceConstants;
import com.game.mancala.entity.Player;
import com.game.mancala.exception.ApiServiceException;
import org.springframework.stereotype.Service;

import java.util.List;


public class PlayerMoveValidator {

    public static void validatePlayerTurn(List<Player> players, int pitId) {
        for (Player player : players) {
            if (ApiServiceConstants.FLAG_Y.equalsIgnoreCase(player.getChanceFlag())) {
                if (ApiServiceConstants.PLAYER1_NAME.equalsIgnoreCase(player.getUserName())
                        && pitId > 6) {
                    throw new ApiServiceException(ApiServiceConstants.USER_ACTION_NOT_ALLOWED,
                            ApiServiceConstants.INCORRECT_PIT_SELECTION);
                } else if (ApiServiceConstants.PLAYER2_NAME.equalsIgnoreCase(player.getUserName())
                        && (pitId <= 7 || pitId > 13)) {
                    throw new ApiServiceException(ApiServiceConstants.USER_ACTION_NOT_ALLOWED,
                            ApiServiceConstants.INCORRECT_PIT_SELECTION);
                }
            }
        }
    }
}
