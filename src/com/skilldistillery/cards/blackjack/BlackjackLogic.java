package com.skilldistillery.cards.blackjack;

import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.cards.common.Dealer;
import com.skilldistillery.cards.common.Player;

public class BlackjackLogic {

	public boolean checkForBlackJack(BlackjackTable table) {
		List<Player> playersWithBlackjack = new ArrayList<>();
		boolean dealerHasBlackjack = false;
		boolean roundContinues = true;
		
		// Add all players who have blackjack to list and update flag for dealer if they have blackjack
		for (Player player : table.getPlayersAtTable()) {
			if (player.getPlayerHand().getValueOfCardsInHand() == 21) {
				playersWithBlackjack.add(player);
				if (player instanceof Dealer) {
					dealerHasBlackjack = true;
				}
			}
		}
		
		int numOfPlayersWithBlackjack = playersWithBlackjack.size();
		int numOfPlayersAtTable = table.getPlayersAtTable().size();
		
		
		// If any players have blackjack
		if (numOfPlayersWithBlackjack > 0) {
			
			// If only dealer has blackjack
			if (numOfPlayersWithBlackjack == 1 && dealerHasBlackjack == true) {
				System.out.println("Dealer has Blackjack. All players lose!");
				table.printBlackjackTable(false);
				roundContinues = false;	
			}
			
			// If some but not all players (excluding dealer) have blackjack
			if (numOfPlayersWithBlackjack < numOfPlayersAtTable - 1) {
				if (dealerHasBlackjack == false) {
					somePlayersHaveBlackjack(playersWithBlackjack, numOfPlayersWithBlackjack);
				}
				else {
					somePlayersAndDealerHaveBlackjack(playersWithBlackjack, numOfPlayersWithBlackjack);
					roundContinues = false;
				}
			}
		
			// If all players (excluding dealer) at table have blackjack
			if (numOfPlayersWithBlackjack == numOfPlayersAtTable -1) {
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
		
		// Nobody has blackjack
		else {
			System.out.println("Nobody has Blackjack!");
		}
		return roundContinues;
		
	} // End of checkForBlackjack method
	
	
	
	
	// Method to build and print output if some but not all players have blackjack (excluding dealer)
	public void somePlayersHaveBlackjack(List<Player> playersWithBlackjack, int numOfPlayersWithBlackjack) {
		StringBuilder sb = new StringBuilder();
		
		// One player has blackjack
		if ( numOfPlayersWithBlackjack == 1) {
			sb.append(playersWithBlackjack.get(0).getPlayerName());
			sb.append(" has Blackjack and wins! Play will continue for the rest of the players.");
			System.out.println(sb.toString());
			return;
		}
		
		
		// Multiple players have blackjack
		for (int i = 0; i < numOfPlayersWithBlackjack; i++) {
			Player currentPlayer = playersWithBlackjack.get(i);
			
			// If last player in list
			if (i == numOfPlayersWithBlackjack -1) {
				sb.append("and ");
				sb.append(currentPlayer.getPlayerName());
				sb.append(" have Blackjack and win. Play will continue for the rest of the players");
			}
			
			else {
				sb.append(currentPlayer.getPlayerName());
				sb.append(", ");
			}
		}
		System.out.println(sb.toString());
		
	} // End of somePlayersHaveBlackjack method
	
	
	
	// Method to build and print output if some but not all players have blackjack and dealer has blackjack
	public void somePlayersAndDealerHaveBlackjack(List<Player> playersWithBlackjack, int numOfPlayersWithBlackjack) {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < numOfPlayersWithBlackjack - 1; i++) {
			Player currentPlayer = playersWithBlackjack.get(i);
			
			// Last player in list
			if (i == numOfPlayersWithBlackjack -1) {
				sb.append("and ");
				sb.append(currentPlayer.getPlayerName());
				sb.append(" push with the Dealer who also has Blackjack! All other players lose!");
			}
			
			else {
				sb.append(currentPlayer.getPlayerName());
				sb.append(", ");
			}
		}
		System.out.println(sb.toString());
	
	} // End of somePlayersAndDealerHaveBlackjack method
	
	
	
	public void printResults(BlackjackTable table) {
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
		
	} // End of printBlackjackResults method
	
	
}
