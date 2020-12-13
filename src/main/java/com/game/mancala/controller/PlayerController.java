package com.game.mancala.controller;

import com.game.mancala.dto.PlayMoveResponse;
import com.game.mancala.service.PlayerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Rest controller class for Player move
 */
@RestController
@RequestMapping("/games")
public class PlayerController {

    private final PlayerService playerService;
    private final String serverPort;

    public PlayerController(PlayerService playerService,
                            @Value("${server.port}") String serverPort){
        this.playerService = playerService;
        this.serverPort = serverPort;
    }

    /**
     * Move method of the game will be invoked
     * @param gameId id of the game
     * @param pitId selection of pit Id to play make the move
     * @return ResponseEntity
     */
    @PostMapping(value = "{gameId}/pits/{pitId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayMoveResponse> makeMove(@PathVariable(name="gameId")  Integer gameId,
                                                     @PathVariable(name="pitId") @Min(value=1, message ="Invalid pit selection" )
                                                     @Max(value=14, message ="Invalid pit selection" ) Integer pitId) {
        Map<String, String> responseMap = playerService.playMove(gameId, pitId);

        Map<Integer, Integer> status1 = responseMap.entrySet().stream()
                .filter(e -> Integer.parseInt(e.getKey()) < 7)
                .collect(Collectors.toMap(x -> Integer.valueOf(x.getKey()), x -> Integer.valueOf(x.getValue())));
        Map<Integer, Integer> status2 = responseMap.entrySet().stream()
                .filter(e -> Integer.parseInt(e.getKey()) > 7 && Integer.parseInt(e.getKey()) <14)
                .collect(Collectors.toMap(x -> Integer.valueOf(x.getKey()), x -> Integer.valueOf(x.getValue())));
        status1.entrySet().stream().sorted();
        status2.entrySet().stream().sorted();
        PlayMoveResponse response = new PlayMoveResponse();
        response.setId(gameId.toString());
        response.setStatusP1(status1);
        response.setStatusP2(status2);
        response.setHomeP1(Integer.parseInt(responseMap.get("7")));
        response.setHomeP2(Integer.parseInt(responseMap.get("14")));
        response.setUrl("http://localhost:" + serverPort + "/games/" + gameId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
