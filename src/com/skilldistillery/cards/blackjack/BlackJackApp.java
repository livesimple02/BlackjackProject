package com.skilldistillery.cards.blackjack;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.cards.common.Card;
import com.skilldistillery.cards.common.Dealer;
import com.skilldistillery.cards.common.Player;
import com.skilldistillery.cards.common.Table;

public class BlackJackApp {

	
	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);
		BlackJackApp app = new BlackJackApp();
		boolean roundContinues = true;
		boolean keepPlaying = true;
		
		Table table = app.setupGame(input);
		
		while (keepPlaying) {
			table.resetDeck();
			roundContinues = app.startRound(table);
			if (roundContinues) {
				app.bettingRound(table, input);
				app.printResults(table);			
			}
			keepPlaying = app.keepPlaying(table, input);

		}
		
	}
	
	public Boolean keepPlaying(Table table, Scanner input) {
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
	
	
	
	
	public Table setupGame(Scanner input) {
		int numOfPlayers = 0;
		int numOfDecks = 0;
		
		System.out.println("Welcome to Blackjack!");
		System.out.println("How many players will be playing?");
		numOfPlayers = getUserInt(input);
		System.out.println("How many decks will this game use?");
		numOfDecks = getUserInt(input);
		Table table = new Table (numOfDecks, numOfPlayers, true);
		for (int i = 0; i < numOfPlayers; i++) {
			System.out.print("Please name Player " + (i+1) + ": ");
			String playerName = getUserString(input);
			table.getPlayersAtTable().get(i).setPlayerName(playerName);
		}
		return table;		
	}
	
	
	
	public boolean startRound(Table table) {
		table.getDeck().shuffleDeck();
		for (int i = 0; i < 2; i++) {
			for (Player player : table.getPlayersAtTable()) {
				player.getPlayerHand().addCardToHand(table.getDeck().dealCard());
			}
		}	
		table.printBlackjackTable(true);
		return checkForBlackJack(table);
	}
	
	
	public void bettingRound(Table table, Scanner input) {
		for (Player player : table.getPlayersAtTable()) {
			boolean playerStays = false;
			if (player.getPlayerHand().getValueOfCardsInHand() == 21) {
				playerStays = true;
			}
			
			if (player instanceof Dealer) {
				table.printBlackjackTable(false);
				while (!playerStays) {
					if (player.getPlayerHand().getValueOfCardsInHand() > 16) {
						System.out.println(player.getPlayerName() + " stays with " + player.getPlayerHand().getValueOfCardsInHand());
						playerStays = true;
					}
					else {
						Card cardDrawn = table.getDeck().dealCard();
						player.getPlayerHand().addCardToHand(cardDrawn);
						System.out.println(player.getPlayerName() + " hits and got a " + cardDrawn.toString() + " and now has " + player.getPlayerHand().getValueOfCardsInHand());
						if (player.getPlayerHand().getValueOfCardsInHand() > 21) {
							System.out.println(player.getPlayerName() + " Busts!");
							playerStays = true;
						}
					}
				}
			}
			
			else {
				table.printBlackjackTable(true);
				while (!playerStays) {
					System.out.print(player.getPlayerName() + " - Would you like to Hit or Stay? Enter 'H' or 'S': ");
					String userResponse = getEitherOrReponse(input, "h", "s");
					if (userResponse.equals("s")) {
						System.out.println(player.getPlayerName() + " stays with " + player.getPlayerHand().getValueOfCardsInHand());
						playerStays = true;
					}
					else {
						Card cardDrawn = table.getDeck().dealCard();
						player.getPlayerHand().addCardToHand(cardDrawn);
						System.out.println(player.getPlayerName() + " hits and got a " + cardDrawn.toString() + " and now has " + player.getPlayerHand().getValueOfCardsInHand());
						if (player.getPlayerHand().getValueOfCardsInHand() > 21) {
							System.out.println(player.getPlayerName() + " Busts!");
							playerStays = true;
						}
					}
				}
			}
		}
	}
	
	
	
	public boolean checkForBlackJack(Table table) {
		List<Player> playersWithBlackjack = new ArrayList<>();
		boolean dealerHasBlackjack = false;
		boolean roundContinues = true;
		
		for (Player player : table.getPlayersAtTable()) {
			if (player.getPlayerHand().getValueOfCardsInHand() == 21) {
				playersWithBlackjack.add(player);
				if (player instanceof Dealer) {
					dealerHasBlackjack = true;
				}
			}
		}
		
		
		if (playersWithBlackjack.size() > 0) {
			
			if (playersWithBlackjack.size() == 1 && dealerHasBlackjack == true) {
				System.out.println("Dealer has Blackjack. All players lose!");
				table.printBlackjackTable(false);
				roundContinues = false;	
			}
			
			if (playersWithBlackjack.size() < table.getPlayersAtTable().size() - 1 && dealerHasBlackjack == false) {
				if (dealerHasBlackjack == false) {
					somePlayersHaveBlackjack(playersWithBlackjack);
				}
				else {
					somePlayersAndDealerHaveBlackjack(playersWithBlackjack);
					roundContinues = false;
				}
			}
		
			if (playersWithBlackjack.size() == table.getPlayersAtTable().size() -1 && dealerHasBlackjack == false) {
				if (dealerHasBlackjack == false) {
					System.out.println("All players have Blackjack and beat the dealer!");;
					roundContinues = false;
				}
				else {
					System.out.println("All players have Blackjack but push with the dealer who also has Blackjack!");
					roundContinues = false;
				}
			}
		}
		else {
			System.out.println("Nobody has Blackjack!");
		}
		return roundContinues;
	}
	
	
	public void somePlayersHaveBlackjack(List<Player> playersWithBlackjack) {
		StringBuilder sb = new StringBuilder();
		
		if (playersWithBlackjack.size() == 1) {
			sb.append(playersWithBlackjack.get(0).getPlayerName());
			sb.append(" has Blackjack and wins! Play will continue for the rest of the players.");
			System.out.println(sb.toString());
			return;
		}
		
		
		
		for (int i = 0; i < playersWithBlackjack.size(); i++) {
			if (i == playersWithBlackjack.size() -1) {
				sb.append("and ");
				sb.append(playersWithBlackjack.get(i).getPlayerName());
				sb.append(" have Blackjack and win. Play will continue for the rest of the players");
			}
			else {
				sb.append(playersWithBlackjack.get(i).getPlayerName());
				sb.append(", ");
			}
		}
		System.out.println(sb.toString());
	}
	
	
	public void somePlayersAndDealerHaveBlackjack(List<Player> playersWithBlackjack) {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < playersWithBlackjack.size() - 1; i++) {
			if (i == playersWithBlackjack.size() -1) {
				sb.append("and ");
				sb.append(playersWithBlackjack.get(i).getPlayerName());
				sb.append(" push with the Dealer who also has Blackjack! All other players lose!");
			}
			else {
				sb.append(playersWithBlackjack.get(i).getPlayerName());
				sb.append(", ");
			}
		}
		System.out.println(sb.toString());
	}
	
	
	public void printResults(Table table) {
		int tableSize = table.getPlayersAtTable().size();
		Dealer dealer = (Dealer)(table.getPlayersAtTable().get(tableSize -1));
		int dealerTotal = dealer.getPlayerHand().getValueOfCardsInHand();

		table.printBlackjackTable(false);
		System.out.println("Dealer finishes with: " + dealerTotal);
		
				
		for (Player player : table.getPlayersAtTable()) {
			StringBuilder sb = new StringBuilder();
			if (!(player instanceof Dealer)) {
				int playerTotal = player.getPlayerHand().getValueOfCardsInHand();
				sb.append(player.getPlayerName());
				sb.append(" - ");
				if (player.getPlayerHand().getCardsInHand().size() == 2 && playerTotal == 21) {
					sb.append("Blackjack - Win");
				}
				else if (playerTotal > dealerTotal && playerTotal <= 21) {
					sb.append(playerTotal);
					sb.append(" - Win");
				}
				else if (playerTotal > 21) {
					sb.append(" Bust - Lose");
				}
				else if(playerTotal < dealerTotal && dealerTotal <= 21) {
					sb.append(playerTotal);
					sb.append(" - Lose");
				}
				else if(playerTotal == dealerTotal && dealerTotal <= 21) {
					sb.append(playerTotal);
					sb.append(" - Push");
				}
				else {
					sb.append(playerTotal);
					sb.append(" - Win");
				}
				System.out.println(sb.toString());	
			}
		}
	}
	
	
	

	

	
	public int getUserInt(Scanner input) {
		int userResponse;
		boolean validEntry = false;

		while (!validEntry) {
			try {
				userResponse = input.nextInt();
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
	
	public String getUserString(Scanner input) {
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