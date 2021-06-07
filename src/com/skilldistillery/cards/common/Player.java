package com.skilldistillery.cards.common;

public class Player {

	private String name;
	private Hand playerHand = new Hand();
	
	
	public Player(String name) {
		this.name = name;
	}
	
	
	public Player() {
		this("Player");
	}
	
	
	public void setPlayerName(String name) {
		this.name = name;
	}
	
	
	public String getPlayerName() {
		return name;
	}
	
	public Hand getPlayerHand() {
		return playerHand;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	
}
