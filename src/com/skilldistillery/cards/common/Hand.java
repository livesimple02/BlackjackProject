package com.skilldistillery.cards.common;

import java.util.ArrayList;
import java.util.List;

public class Hand {

	private List<Card> cardsInHand = new ArrayList<>();

		
	public Hand() {
		super();
	}
	
	
	public List<Card> getCardsInHand () {
		return cardsInHand;
	}
	
	
	public void addCardToHand(Card card) {
		cardsInHand.add(card);
	}
	
	
	public int getValueOfCardsInHand() {
		int handValue = 0;
		for (Card card : cardsInHand) {
			handValue += card.getValue();
		}
		return handValue;
	}
	
	
	public void printCardsInHand() {
		for (Card card : cardsInHand) {
			System.out.print(card.toString());
		}
	}
	
	
	public void printHandValue() {
		System.out.println("Hand Value: " + getValueOfCardsInHand());
	}
	
	
	public void resetHand() {
		cardsInHand.removeAll(getCardsInHand());
	}

}
