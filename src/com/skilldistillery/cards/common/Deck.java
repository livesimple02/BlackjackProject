package com.skilldistillery.cards.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

	private List<Card> deck = new ArrayList<>();
	
	
	public Deck(int numOfDecks) {
		Rank[] ranks = Rank.values();
		Suit[] suits = Suit.values();
		
		for (int i = 0; i < numOfDecks; i++) {
			for (Suit suit : suits) {
				for (Rank rank : ranks) {
					Card cardToAdd = new Card(suit, rank);
					this.deck.add(cardToAdd);
				}
			}
		}
	}
	
	public Deck() {
		this(1);
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
	
	public void shuffleDeck() {
		Collections.shuffle(deck);
	}
	
	public List<Card> getCardsInDeck() {
		return deck;
	}
	
	public void addCardToDeck(Card card) {
		deck.add(card);
	}
	
	
}
