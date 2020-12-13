package com.game.mancala.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO class for handling exception
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameExceptionResponse {
	private String statusCode;
	private String statusMsg;
	private String description;
}
