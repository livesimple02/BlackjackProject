package com.skilldistillery.cards.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

	private List<Card> deck = new ArrayList<>();
	
	public Deck() {
//		List<Card> deck = new ArrayList<>();
		Rank[] ranks = Rank.values();
		Suit[] suits = Suit.values();
		
		for (Suit suit : suits) {
			for (Rank rank : ranks) {
				Card cardToAdd = new Card(suit, rank);
				this.deck.add(cardToAdd);
			}
		}
	}
	
	public int checkDeckSize() {
		return deck.size();
	}
	
	public Card dealCard() {
		if (deck.size() > 0) {
			return deck.remove(0);
		}
		return null;
	}
	
	public void shuffle() {
		Collections.shuffle(deck);
	}
	
	
}
