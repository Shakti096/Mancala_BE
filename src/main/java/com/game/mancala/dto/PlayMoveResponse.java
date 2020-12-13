package com.game.mancala.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * DTO class for handling player move request
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayMoveResponse {
	private String id;
	private String url;
	private Map<Integer, Integer> statusP1;
	private Map<Integer, Integer> statusP2;
	private Integer homeP1;
	private Integer homeP2;
}
