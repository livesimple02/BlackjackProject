package com.skilldistillery.cards.common;

import java.util.ArrayList;
import java.util.List;

public class Table {

	Deck deck;
	List<Player> playersAtTable = new ArrayList<>();

	
	public Table(int deckSize, int numOfPlayers, boolean hasDealer) {
		this.deck = new Deck(deckSize);
		for (int i = 0; i < numOfPlayers; i++) {
			playersAtTable.add(new Player());
		}
		if (hasDealer == true) {
			playersAtTable.add(new Dealer());
		}
	}
	
	
	public List<Player> getPlayersAtTable() {
		return playersAtTable;
	}
	
	
	public Deck getDeck() {
		return deck;
	}
	
	public void resetDeck() {
		for (Player player : playersAtTable) {
			for (Card card : player.getPlayerHand().getCardsInHand()) {
				deck.addCardToDeck(card);
			}
		}
		for (Player player: playersAtTable) {
			player.getPlayerHand().resetHand();
		}
	}
	
	
	public void printBlackjackTable(boolean firstRound) {
		String player1 = "";
		String player2 = "";
		String player3 = "";
		String player4 = "";
		String dealer = "";
		String player1Count = "";
		String player2Count = "";
		String player3Count = "";
		String player4Count = "";
		String dealerCount = "";
		String player1Cards = "";
		String player2Cards = "";
		String player3Cards = "";
		String player4Cards = "";
		String dealerCards = "";
	
		int numOfPlayersAtTable = playersAtTable.size() - 1;
		for (int i = 0; i <= numOfPlayersAtTable; i++) {
			if (i == 0 && i != numOfPlayersAtTable) {
				player1 = playersAtTable.get(i).getPlayerName();
				player1Count = "Total = " + playersAtTable.get(i).getPlayerHand().getValueOfCardsInHand();
				player1Cards = playersAtTable.get(i).getPlayerHand().getCardsInHand().toString();
			}
			if (i == 1 && i != numOfPlayersAtTable) {
				player2 = playersAtTable.get(i).getPlayerName();
				player2Count = "Total = " + playersAtTable.get(i).getPlayerHand().getValueOfCardsInHand();
				player2Cards = playersAtTable.get(i).getPlayerHand().getCardsInHand().toString();
			}
			if (i == 2 && i != numOfPlayersAtTable) {
				player3 = playersAtTable.get(i).getPlayerName();
				player3Count = "Total = " + playersAtTable.get(i).getPlayerHand().getValueOfCardsInHand();
				player3Cards = playersAtTable.get(i).getPlayerHand().getCardsInHand().toString();
			}
			if (i == 3 && i != numOfPlayersAtTable) {
				player4 = playersAtTable.get(i).getPlayerName();
				player4Count = "Total = " + playersAtTable.get(i).getPlayerHand().getValueOfCardsInHand();
				player4Cards = playersAtTable.get(i).getPlayerHand().getCardsInHand().toString();
			}
			if (i == numOfPlayersAtTable) {
				Dealer playerDealer = (Dealer)playersAtTable.get(i);
				dealer = playersAtTable.get(i).getPlayerName();
				if (firstRound) {
					dealerCount = "Total = " + playerDealer.getDealerSecondCard().getValue();
					dealerCards = playerDealer.getDealerSecondCard().toString();
					
				}
				else {
					dealerCount = "Total = " + playersAtTable.get(i).getPlayerHand().getValueOfCardsInHand();
					dealerCards = playersAtTable.get(i).getPlayerHand().getCardsInHand().toString();
				}
			}
		}
		
		System.out.println();
		System.out.printf("--------------------------------------------------\n");
		System.out.printf("| %26s                     |\n", dealer);
		System.out.printf("| %28s                   |\n", dealerCount);
		System.out.printf("| %28s                   |\n", dealerCards);
		System.out.println("|                                                |");
		System.out.printf("| %-23s%23s |\n", player4, player1);
		System.out.printf("| %-23s%23s |\n", player4Count, player1Count);
		System.out.printf("| %-23s%23s |\n", player4Cards, player1Cards);
		System.out.println("|                                                |");
		System.out.printf("| %18s          %-18s |\n", player3, player2);
		System.out.printf("| %18s          %-18s |\n", player3Count, player2Count);
		System.out.printf("| %18s          %-18s |\n", player3Cards, player2Cards);
		System.out.println("|                                                |");
		System.out.printf("--------------------------------------------------\n");
		
		
	}
	

}
