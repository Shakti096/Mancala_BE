package com.game.mancala.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.game.mancala.constants.ApiServiceConstants;
import com.game.mancala.dto.GameExceptionResponse;

/**
 * Controller advice  for the application,
 * It will handle all the generic exceptions of the application
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * This method handles the exceptions for methods in controllers
	 * 
	 * @param ex ApiServiceException - exception to be thrown
	 * @return ResponseEntity<GameExceptionResponse>
	 */
	@ExceptionHandler(ApiServiceException.class)
	public ResponseEntity<GameExceptionResponse> customExceptionHandler(ApiServiceException ex) {
		GameExceptionResponse errorResponse = new GameExceptionResponse();
		errorResponse.setStatusCode(ex.getErrorCode());
		errorResponse.setStatusMsg(ApiServiceConstants.ERROR_MSG);
		errorResponse.setDescription(ex.getErrorMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
}
