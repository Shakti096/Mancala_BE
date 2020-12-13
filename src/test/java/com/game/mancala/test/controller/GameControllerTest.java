package com.game.mancala.test.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.game.mancala.controller.GameController;
import com.game.mancala.dto.GameResponse;
import com.game.mancala.entity.Game;
import com.game.mancala.service.GameService;
@RunWith(SpringRunner.class)
public class GameControllerTest {

	@InjectMocks
	private GameController controller;
	@Mock
	private GameService gameService;

	@Test
	public void initializeGame() {
		Mockito.when(gameService.createGame()).thenReturn(new Game(1,"Y",null,null));
		ResponseEntity<GameResponse> resp = controller.startGame();
		assertEquals(HttpStatus.OK, resp.getStatusCode());
	}

}