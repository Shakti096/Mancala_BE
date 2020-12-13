package com.game.mancala.controller;

import com.game.mancala.dto.GameResponse;
import com.game.mancala.entity.Game;
import com.game.mancala.service.GameService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller class for the starting a new Game
 */
@RestController
@RequestMapping("/games")
public class GameController {

	private final GameService gameService;
	private final String serverPort;

	public GameController(GameService gameService,
						  @Value("${server.port}") String serverPort){
		this.gameService = gameService;
		this.serverPort = serverPort;
	}

	/**
	 * This method is used to start a new game.
	 * Method type is PUT
	 * 
	 * @return GameResponse object
	 */
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GameResponse> startGame(){
		Game game = gameService.createGame();
		GameResponse gameResponse = new GameResponse(game.getId(), "http://localhost:"+serverPort+"/games/"+game.getId());
		return ResponseEntity.status(HttpStatus.OK).body(gameResponse);
	}
}
