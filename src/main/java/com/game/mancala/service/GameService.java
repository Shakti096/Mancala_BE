package com.game.mancala.service;

import org.springframework.stereotype.Service;

import com.game.mancala.entity.Game;
import com.game.mancala.repository.GameRepository;

/**
 * This is the service class for creating a game
 *
 */
@Service
public class GameService {

	private final GameRepository gameRepository;
	private final PlayerService playerService;
	private final GameDetailsService gameDetailsService;

	public GameService(GameRepository gameRepository,
					   PlayerService playerService,
					   GameDetailsService gameDetailsService){
		this.gameRepository = gameRepository;
		this.playerService = playerService;
		this.gameDetailsService = gameDetailsService;
	}

	/**
	 * This method is used to create a game for 2 players
	 * 
	 * @return Integer - ID of table MancalaGameList
	 */
	public Game createGame() {
		Game game = createNewGame();
		game.setPlayers(playerService.initializeGamePlayers(game));
		game.setGameDetails(gameDetailsService.initializeGameDetails(game));
		return gameRepository.save(game);
	}

	private Game createNewGame() {
		Game game = new Game();
		game.setIsActive(Boolean.TRUE.toString());
		return game;
	}
}
