package com.game.mancala.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO class for handling create game request
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameResponse {
	private Integer id;
	private String uri;
}
