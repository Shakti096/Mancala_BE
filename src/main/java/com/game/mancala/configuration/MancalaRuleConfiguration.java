package com.game.mancala.configuration;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@ConfigurationProperties(prefix="mancala")
@Getter
@Setter
public class MancalaRuleConfiguration {

	private Map<Integer,BoardRule> boardRule;
	
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class BoardRule{
		private Integer position;
		private String skipable;
		private Integer nextPosition;
	}
}
