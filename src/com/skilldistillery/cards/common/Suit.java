package com.skilldistillery.cards.common;

public enum Suit {

	HEARTS ((char)9829), 
	SPADES ((char)9824), 
	CLUBS ((char)9827), 
	DIAMONDS ((char)9830);
	
	Suit(char s) {
		symbol = s;
	}
	
	
	final private char symbol;
	
	
	public char getSymbol() {
		return symbol;
	}
	
	
}