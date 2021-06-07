package com.skilldistillery.cards.common;

public class Dealer extends Player {

	public Dealer(String name) {
		super(name);
	}

	public Dealer() {
		super("Dealer");
	}

	public Card getDealerSecondCard() {
		return this.getPlayerHand().getCardsInHand().get(1);
	}
	
	
	
}
