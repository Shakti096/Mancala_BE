package com.game.mancala;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.game.mancala.configuration.MancalaRuleConfiguration;

/**
 * This is the main startup class of the application
 *
 */

@SpringBootApplication
@EnableAutoConfiguration
@EnableConfigurationProperties({MancalaRuleConfiguration.class})
public class MancalaServiceApp {

	/**
	 * this method starts the spring boot application
	 * @param args - String arguments to be passed as input if any
	 */
	public static void main(String[] args) {
		SpringApplication.run(MancalaServiceApp.class, args);
	}

}
