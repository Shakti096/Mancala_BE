package com.game.mancala.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This is the parent exception class for the application
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String errorCode;
	private String errorMessage;
}
