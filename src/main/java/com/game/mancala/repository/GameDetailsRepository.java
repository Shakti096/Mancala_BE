package com.game.mancala.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.game.mancala.entity.GameDetails;

/**
 * Repository class for GameDetails table
 */
@Repository
public interface GameDetailsRepository extends JpaRepository<GameDetails, Integer> {
}
