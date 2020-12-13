package com.game.mancala.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.*;

/**
 * Entity definition for table GAME_DETAILS
 */
@Entity
@Table(name = "GAME_DETAILS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "position")
	private Integer position;

	@Column(name = "count")
	private Integer count;
	
	@ManyToOne
	@JoinColumn(name = "game_id", referencedColumnName = "id")
	private Game game;

}
