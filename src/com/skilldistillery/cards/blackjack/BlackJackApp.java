package com.skilldistillery.cards.blackjack;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.skilldistillery.cards.common.BlackjackLogic;
import com.skilldistillery.cards.common.Card;
import com.skilldistillery.cards.common.Dealer;
import com.skilldistillery.cards.common.Player;
import com.skilldistillery.cards.common.Table;

public class BlackJackApp {

	
	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);
		BlackJackApp app = new BlackJackApp();
		BlackjackLogic logic = new BlackjackLogic();
		boolean roundContinues = true;
		boolean keepPlaying = true;
		
		// Initial Game Setup
		Table table = app.setupGame(input);
		
		// Allows play for more than 1 round
		while (keepPlaying) {
			// Remove any cards from player hands and return them to the deck
			table.resetDeck();
			// Deal cards and check for Blackjack. End the game if applicable as a result of Blackjacks
			roundContinues = app.startRound(table, logic);
			// As long as Blackjacks haven't ended the round,
			//go through betting round and then print the final results once all players have completed their turn
			if (roundContinues) {
				app.bettingRound(table, input);
				logic.printResults(table);			
			}
			// Check with user whether they would like to play another round
			keepPlaying = app.keepPlaying(table, input);
		}
	}
	
	
	// Initial Game Setup
	public Table setupGame(Scanner input) {
		int numOfPlayers = 0;
		int numOfDecks = 0;
		
		System.out.println("Welcome to Blackjack!");
		System.out.print("How many players will be playing? Choose up to 4 NOT including the dealer: ");
		numOfPlayers = getUserIntInput(input, 4);
		System.out.print("How many decks will this game use?: ");
		numOfDecks = getUserIntInput(input, 100);
		Table table = new Table (numOfDecks, numOfPlayers, true);
		for (int i = 0; i < numOfPlayers; i++) {
			System.out.print("Please name Player " + (i+1) + ": ");
			String playerName = getUserStringInput(input);
			table.getPlayersAtTable().get(i).setPlayerName(playerName);
		}
		return table;		
	}
	
	
	
	// Deal 2 cards to all players and check for Blackjack
	public boolean startRound(Table table, BlackjackLogic logic) {
		table.getDeck().shuffleDeck();
		for (int i = 0; i < 2; i++) {
			for (Player player : table.getPlayersAtTable()) {
				player.getPlayerHand().addCardToHand(table.getDeck().dealCard());
			}
		}	
		table.printBlackjackTable(true);
		logic = new BlackjackLogic();
		return logic.checkForBlackJack(table);
	}
	
	
	
	// For each players turn, ask for actions
	// Once all players have gone, automatically perform dealer actions
	public void bettingRound(Table table, Scanner input) {
		
		for (Player player : table.getPlayersAtTable()) {
			
			String playerName = player.getPlayerName();
			int playerCardTotal = player.getPlayerHand().getValueOfCardsInHand();
			boolean playerStays = false;
			
			if (playerCardTotal == 21) {
				playerStays = true;
				continue;
			}
			
			// Dealer Turn
			if (player instanceof Dealer) {
				table.printBlackjackTable(false); // false flag reveals dealers first card at the start of their turn
				while (!playerStays) {
					if (playerCardTotal > 16) {
						System.out.println(playerName + " stays with " + playerCardTotal);
						playerStays = true;
					}
					else {
						Card cardDrawn = table.getDeck().dealCard();
						player.getPlayerHand().addCardToHand(cardDrawn);
						playerCardTotal += cardDrawn.getValue();
						System.out.println(playerName + " hits and got a " + cardDrawn.toString() + " and now has " + playerCardTotal);
						if (playerCardTotal > 21) {
							System.out.println(playerName + " Busts!");
							playerStays = true;
						}
					}
				}
			}
			
			// Player turn
			else {
				table.printBlackjackTable(true); // true flag keeps dealers first card hidden on table
				while (!playerStays) {
					System.out.print(playerName + " - Would you like to Hit or Stay? Enter 'H' or 'S': ");
					String userResponse = getEitherOrReponse(input, "h", "s");
					// Player hits
					if (userResponse.equals("h")) {
						Card cardDrawn = table.getDeck().dealCard();
						player.getPlayerHand().addCardToHand(cardDrawn);
						playerCardTotal += cardDrawn.getValue();
						System.out.println(playerName + " hits and got a " + cardDrawn.toString() + " and now has " + playerCardTotal);
						// If card drawn causes bust
						if (playerCardTotal > 21) {
							System.out.println(playerName + " Busts!");
							playerStays = true;
						}
						// If card drawn brings total to 21
						if (playerCardTotal == 21) {
							System.out.println(playerName + " stays with 21");
							playerStays = true;
						}
					}
					// Player stays
					else {
						System.out.println(playerName + " stays with " + playerCardTotal);
						playerStays = true;
					}
				}
			}
		}
	}
	
	
	
	// Check with user whether they want to play another round or not
	public Boolean keepPlaying(Table table, Scanner input) {
		System.out.println();
		System.out.print("Would you like to play another Round? 'Y' or 'N': ");
		String userReponse = getEitherOrReponse(input, "y", "n");
		if (userReponse.equals("y")) {
			return true;
			
		}
		else {
			System.out.println("Thank you for playing! Have a great day!");
			return false;
		}
	}

	
	// -----------------------------------------------------
	// ------ User Response Methods Below Here -------------
	// -----------------------------------------------------
	
	public int getUserIntInput(Scanner input, int max) {
		int userResponse;
		boolean validEntry = false;

		while (!validEntry) {
			try {
				userResponse = input.nextInt();
				if (userResponse > 4) {
					throw new InputMismatchException();
				}
				validEntry = true;
				input.nextLine();
				return userResponse;
			}
			catch (InputMismatchException ime) {
				System.err.println("Invalid Entry! Please try again");
				input.nextLine();
			}
		}
		return -1;
	}
	
	
	
	public String getUserStringInput(Scanner input) {
		String userResponse;
		boolean validEntry = false;
		
		while (!validEntry) {
			try {
				userResponse = input.nextLine();
				validEntry = true;
				return userResponse;
			}
			catch (InputMismatchException ime) {
				System.err.println("Invalid Entry! Please try again");
			}
		}
		return null;
	}
	
	
	
	public String getEitherOrReponse(Scanner input, String option1, String option2) {
		String userResponse;
		boolean validEntry = false;
		
		while (!validEntry) {
			try {
				userResponse = input.nextLine();
				if (!userResponse.toLowerCase().equals(option1) && !userResponse.toLowerCase().equals(option2)) {
					throw new InputMismatchException();
				}
				validEntry = true;
				return userResponse.toLowerCase();
			}
			catch (InputMismatchException ime) {
				System.err.println("Invalid Entry! Please try again");
			}
		}
		return null;
	}
	

}