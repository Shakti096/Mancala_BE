package com.game.mancala.test.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.game.mancala.validator.PlayerMoveValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.test.context.junit4.SpringRunner;

import com.game.mancala.constants.ApiServiceConstants;
import com.game.mancala.entity.Game;
import com.game.mancala.entity.Player;
import com.game.mancala.exception.ApiServiceException;
import com.game.mancala.repository.GameRepository;
import com.game.mancala.service.PlayerMoveService;
import com.game.mancala.service.PlayerService;

/**
 * Test class for PlayerMoveService
 *
 */
@RunWith(SpringRunner.class)
public class PlayerServiceTest {

	@InjectMocks
	private PlayerService service;

	@Mock
	private GameRepository gameRepository;

	@Mock
	private PlayerMoveService playerMoveService;

	/**
	 * Test method playGame
	 */
	@Test
	public void playGame() {

		Game gameList = new Game();

		List<Player> userChanceList = new ArrayList<>();
		Player obj = new Player(1, ApiServiceConstants.PLAYER1_NAME,
				ApiServiceConstants.FLAG_Y, gameList);
		Player obj2 = new Player(1, ApiServiceConstants.PLAYER2_NAME,
				ApiServiceConstants.FLAG_N, gameList);
		userChanceList.add(obj);
		userChanceList.add(obj2);

		gameList.setPlayers(userChanceList);

		Mockito.when(gameRepository.findByIdAndIsActive(1, Boolean.TRUE.toString())).thenReturn(gameList);
		Mockito.when(playerMoveService.moveStones(2, gameList)).thenReturn(new HashMap<>());

		service.playMove(1, 2);

	}

	@Test
	public void playGame_Exception_NoRec() {

		Mockito.when(gameRepository.findByIdAndIsActive(1, Boolean.TRUE.toString())).thenReturn(null);
		try {
			service.playMove(1, 2);
		} catch (ApiServiceException e) {
			assertEquals(ApiServiceConstants.GAME_LIST_TABLE_ERROR_MSG, e.getErrorMessage());
		}
	}
	
	@Test
	public void playGame_Exception_InvalidPit() {

		Game gameList = new Game();

		List<Player> userChanceList = new ArrayList<>();
		Player obj = new Player(1, ApiServiceConstants.PLAYER1_NAME,
				ApiServiceConstants.FLAG_Y, gameList);
		Player obj2 = new Player(1, ApiServiceConstants.PLAYER2_NAME,
				ApiServiceConstants.FLAG_N, gameList);
		userChanceList.add(obj);
		userChanceList.add(obj2);

		gameList.setPlayers(userChanceList);

		Mockito.when(gameRepository.findByIdAndIsActive(1, Boolean.TRUE.toString())).thenReturn(gameList);
		Mockito.when(playerMoveService.moveStones(9, gameList)).thenReturn(new HashMap<>());
		try {
			service.playMove(1, 9);
		} catch (ApiServiceException e) {
			assertEquals(ApiServiceConstants.INCORRECT_PIT_SELECTION, e.getErrorMessage());
		}

	}
	
	@Test
	public void playGame_Exception_InvalidPit_U1() {

		Game gameList = new Game();

		List<Player> userChanceList = new ArrayList<>();
		Player obj = new Player(1, ApiServiceConstants.PLAYER1_NAME,
				ApiServiceConstants.FLAG_Y, gameList);
		Player obj2 = new Player(1, ApiServiceConstants.PLAYER2_NAME,
				ApiServiceConstants.FLAG_N, gameList);
		userChanceList.add(obj);
		userChanceList.add(obj2);

		gameList.setPlayers(userChanceList);

		List<Player> players = new ArrayList();
		Mockito.when(gameRepository.findByIdAndIsActive(1, Boolean.TRUE.toString())).thenReturn(gameList);
		Mockito.when(playerMoveService.moveStones(0, gameList)).thenReturn(new HashMap<>());
		try {
			service.playMove(1, 0);
		} catch (ApiServiceException e) {
			assertEquals(ApiServiceConstants.INCORRECT_PIT_SELECTION, e.getErrorMessage());
		}

	}
	

	@Test
	public void playGame_Exception_InvalidPit_U2() {

		Game gameList = new Game();

		List<Player> userChanceList = new ArrayList<>();
		Player obj = new Player(1, ApiServiceConstants.PLAYER1_NAME,
				ApiServiceConstants.FLAG_N, gameList);
		Player obj2 = new Player(1, ApiServiceConstants.PLAYER2_NAME,
				ApiServiceConstants.FLAG_Y, gameList);
		userChanceList.add(obj);
		userChanceList.add(obj2);

		gameList.setPlayers(userChanceList);

		Mockito.when(gameRepository.findByIdAndIsActive(1, Boolean.TRUE.toString())).thenReturn(gameList);
		Mockito.when(playerMoveService.moveStones(2, gameList)).thenReturn(new HashMap<>());
		try {
			service.playMove(1, 2);
		} catch (ApiServiceException e) {
			assertEquals(ApiServiceConstants.INCORRECT_PIT_SELECTION, e.getErrorMessage());
		}

	}
	
	@Test
	public void playGame_Exception_InvalidPit_U2_New() {

		Game gameList = new Game();

		List<Player> userChanceList = new ArrayList<>();
		Player obj = new Player(1, ApiServiceConstants.PLAYER1_NAME,
				ApiServiceConstants.FLAG_N, gameList);
		Player obj2 = new Player(1, ApiServiceConstants.PLAYER2_NAME,
				ApiServiceConstants.FLAG_Y, gameList);
		userChanceList.add(obj);
		userChanceList.add(obj2);

		gameList.setPlayers(userChanceList);

		Mockito.when(gameRepository.findByIdAndIsActive(1, Boolean.TRUE.toString())).thenReturn(gameList);
		Mockito.when(playerMoveService.moveStones(15, gameList)).thenReturn(new HashMap<>());
		try {
			service.playMove(1, 15);
		} catch (ApiServiceException e) {
			assertEquals(ApiServiceConstants.INCORRECT_PIT_SELECTION, e.getErrorMessage());
		}

	}
}
