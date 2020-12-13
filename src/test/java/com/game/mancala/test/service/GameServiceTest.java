package com.game.mancala.test.service;

import com.game.mancala.entity.GameDetails;
import com.game.mancala.entity.Player;
import com.game.mancala.service.GameDetailsService;
import com.game.mancala.service.PlayerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import com.game.mancala.entity.Game;
import com.game.mancala.repository.GameRepository;
import com.game.mancala.service.GameService;

import java.util.ArrayList;
import java.util.List;

/**
 * Test class for GameService
 *
 */
@RunWith(SpringRunner.class)
public class GameServiceTest {

	@InjectMocks
	private GameService service;
	@Mock
	private  PlayerService playerService;
	@Mock
	private  GameDetailsService gameDetailsService;

	@Mock
	private GameRepository gameRepository;

	@Test
	public void createGame() {

		Game obj = new Game();
		obj.setId(1);
		List<Player> players = new ArrayList<>();
		List<GameDetails> gameDetails = new ArrayList<>();
		Mockito.when(playerService.initializeGamePlayers(ArgumentMatchers.any(Game.class))).thenReturn(players);
		Mockito.when(gameDetailsService.initializeGameDetails(ArgumentMatchers.any(Game.class))).thenReturn(gameDetails);
		Mockito.when(gameRepository.saveAndFlush(ArgumentMatchers.any(Game.class))).thenReturn(obj);

		service.createGame();

	}
}