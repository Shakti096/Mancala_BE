package com.game.mancala.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.*;

/**
 * Entity definition for table PLAYER
 */
@Entity(name = "PLAYER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "chance_flag")
	private String chanceFlag;
	
	@ManyToOne
	@JoinColumn(name = "game_id", referencedColumnName = "id")
	private Game gameList;

	public Player(String userName,String chanceFlag,Game gameList){
		this.userName = userName;
		this.chanceFlag = chanceFlag;
		this.gameList = gameList;
	}
}
