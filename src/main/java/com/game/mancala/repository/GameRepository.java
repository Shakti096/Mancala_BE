package com.game.mancala.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.game.mancala.entity.Game;

/**
 * Repository class for Game table
 */
public interface GameRepository extends JpaRepository<Game, Integer> {

	/**
	 * This method is used to fetch the active game data from a given ID
	 * 
	 * @param id - primary key of the table Game
	 * @param isActive - check if the game is still active
	 * @return the database record of game
	 */
	Game findByIdAndIsActive(Integer id, String isActive);

}
