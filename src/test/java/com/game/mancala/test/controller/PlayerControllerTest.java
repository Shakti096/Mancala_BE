package com.game.mancala.test.controller;

import com.game.mancala.controller.PlayerController;
import com.game.mancala.dto.PlayMoveResponse;
import com.game.mancala.service.PlayerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class PlayerControllerTest {

    @InjectMocks
    private PlayerController controller;

    @Mock
    private PlayerService playerService;

    @Test
    public void makeMove() {
        HashMap<String, String> responseMap= new HashMap();
        responseMap.put("7","0");
        responseMap.put("14","0");
        Mockito.when(playerService.playMove(1, 2)).thenReturn(responseMap);
        ResponseEntity<PlayMoveResponse> resp = controller.makeMove(1, 2);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
    }
}
