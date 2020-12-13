package com.game.mancala.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Entity definition for table GAME
 */
@Entity(name="GAME")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "is_active")
	private String isActive;
	
	@OneToMany(cascade = {CascadeType.ALL}/*,fetch = FetchType.EAGER*/)
	@JoinColumn(name = "game_id", referencedColumnName = "id")
	private List<GameDetails> gameDetails;
	
	@OneToMany(cascade = {CascadeType.ALL}/*, fetch = FetchType.EAGER*/)
	@JoinColumn(name = "game_id", referencedColumnName = "id")
	private List<Player> players;
}
