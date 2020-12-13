package com.game.mancala.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.game.mancala.entity.Player;


/**
 * Repository class for Player table
 */
@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {
}
