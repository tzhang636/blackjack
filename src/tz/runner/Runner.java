package tz.runner;

import java.util.InputMismatchException;
import java.util.Scanner;

import tz.cards.Deck;
import tz.players.Player;

/**
 * The main blackjack game loop.
 * @author Thomas Zhang
 *
 */
public class Runner {
	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);
		
		// intro
		System.out.println("BLACKJACK");
		System.out.println("by Thomas Zhang");
		System.out.println();
		
		// main game loop
		while (true) {
			System.out.println("Main Menu");
			System.out.println("[1] Start a new game.");
			System.out.println("[2] Quit.");
			System.out.println("Please choose an option by typing in the number.");
			int option = -1;
			try {
				option = reader.nextInt();
			} catch (InputMismatchException e) { // not an integer
				reader.next(); // skip input
			}
			System.out.println();
			if (option < 1 || option > 2) { // invalid input
				System.out.println("Please enter a valid number.");
				System.out.println();
				continue;
			}
			if (option == 2) { // exit game loop
				System.out.println("Exiting game...");
				System.out.println("Goodbye.");
				System.out.println();
				break;
			}
			
			// start new game
			System.out.println("Starting a new game...");
			System.out.println("Creating a new player for you...");
			System.out.println();
			int initBuyIn = 100;
			Player player = new Player(initBuyIn);
			Player dealer = new Player(initBuyIn);
			
			// game menu
			while (true) {
				System.out.println("Game Menu");
				System.out.println("[1] Begin a new round.");
				System.out.println("[2] View bankroll.");
				System.out.println("[3] Cash out to main menu.");
				System.out.println("Please choose an option by typing in the number.");
				option = -1;
				try {
					option = reader.nextInt();
				} catch (InputMismatchException e) { // not an integer
					reader.next(); // skip input
				}
				System.out.println();
				int currBuyIn = player.getBuyIn();
				int diff = currBuyIn - initBuyIn;
				if (option < 1 || option > 3) { // invalid input
					System.out.println("Please enter a valid number.");
					System.out.println();
					continue;
				}
				else if (option == 1) {
					if (currBuyIn <= 0) {
						System.out.println("You have no more chips left.");
						System.out.println("Please cash out and start a new game.");
						System.out.println();
						continue;
					}
				}
				else if (option == 2) {
					System.out.println("Player bankroll (chips): " + currBuyIn);
					System.out.println("Gains/Losses (chips): " + diff);
					System.out.println();
					continue;
				}
				else { // option 3, cash out
					System.out.println("You cashed out with " + currBuyIn + 
							" chips, a gain/loss of " + diff + " chips.");
					System.out.println();
					break;
				}
				
				// begin new round
				System.out.println("Creating and shuffling deck...");
				System.out.println();
				Deck deck = new Deck();
				deck.shuffle();
				dealer.clearHand();
				player.clearHand();
				
				// place bet
				System.out.println("Please place amount to bet in chips.");
				System.out.println("You have " + player.getBuyIn() + " chips to bet with.");
				int betAmt = -1;
				while (true) {
					try {
						betAmt = reader.nextInt();
					} catch (InputMismatchException e) { // not an integer
						reader.next(); // skip input
					}
					System.out.println();
					if (player.bet(betAmt) < 0) {
						System.out.println("Invalid bet. Please enter another bet.");
						System.out.println("You have " + player.getBuyIn() + " chips to bet with.");
					}
					else {
						System.out.println("You bet " + betAmt + " chips.");
						System.out.println("You have " + player.getBuyIn() + " chips left.");
						break;
					}
				}
				System.out.println();

				// deal cards
				System.out.println("Dealing cards...");
				System.out.println();
				player.addCard(deck.deal());
				dealer.addCard(deck.deal());
				player.addCard(deck.deal());
				dealer.addCard(deck.deal());
				
				// display hands
				System.out.println("Dealer's hand:");
				System.out.println(dealer.getHand(true));
				System.out.println("Your hand:");
				System.out.println(player.getHand(false));
				
				// check for blackjacks
				boolean playerBlackjack = (player.getHandSum() == 21) ? true : false;
				boolean dealerBlackjack = (dealer.getHandSum() == 21) ? true : false;
				if (playerBlackjack && !dealerBlackjack) {
					System.out.println("You hit a blackjack!");
					System.out.println("You win " + 3*betAmt/2 + " chips from the dealer.");
					System.out.println();
					player.win(betAmt + 3*betAmt/2);
					continue;
				}
				else if (!playerBlackjack && dealerBlackjack) {
					System.out.println("Dealer's hand:");
					System.out.println(dealer.getHand(false));
					System.out.println("Dealer hits a blackjack!");
					System.out.println("Dealer wins " + betAmt + " chips from you.");
					System.out.println();
					continue;
				}
				else if (playerBlackjack && dealerBlackjack ){
					System.out.println("Tough luck! You and the dealer both hit blackjacks!");
					System.out.println("You receive your bet of " + betAmt + " chips back.");
					System.out.println();
					player.win(betAmt);
					continue;
				}
				
				// player hand loop
				boolean busted = false;
				while (player.getHandSum() <= 21) {
					System.out.println("Hit or stay? (Enter h or s)");
					String input = reader.next();
					System.out.println();
					if (input.equals("s")) {
						System.out.println("You stay.");
						System.out.println();
						break;
					}
					else if (input.equals("h")) {
						System.out.println("You hit.");
						System.out.println();
						busted = player.addCard(deck.deal());
						System.out.println("Your hand:");
						System.out.println(player.getHand(false));
					}
					else {
						System.out.println("Invalid input. Please try again.");
						System.out.println();
					}
				}
				
				// print out final hand
				System.out.println("Your final hand:");
				System.out.println(player.getHand(false));
				
				// print out final sum
				int sum = player.getHandSum();
				System.out.println("Your final hand sum is " + sum + ".");
				System.out.println();
				
				// if you busted
				if (busted) {
					System.out.println("You busted. Dealer wins!");
					System.out.println("Dealer wins " + betAmt + " chips from you.");
					System.out.println();
					continue;
				}
				
				// if you didn't bust, it's the dealer's turn
				// dealer hits until his hand sum >= 17
				boolean dealerBusted = false;
				while (dealer.getHandSum() < 17) {
					System.out.println("Dealer hits.");
					System.out.println();
					dealerBusted = dealer.addCard(deck.deal());
		
					System.out.println("Dealer's hand:");
					System.out.println(dealer.getHand(false));
				}
				
				// only print this out if the dealer does not bust
				if (!dealerBusted) {
					System.out.println("Dealer stays.");
					System.out.println();
				}
				
				// print out dealer's final hand
				System.out.println("Dealer's final hand:");
				System.out.println(dealer.getHand(false));
				
				// print dealer's final hand sum
				int dealerSum = dealer.getHandSum();
				System.out.println("Dealer's final hand sum is " + dealerSum + ".");
				System.out.println();
				
				// if dealer busted
				if (dealerBusted) {
					System.out.println("Dealer busted. You win!");
					System.out.println("You win " + betAmt + " chips from the dealer.");
					System.out.println();
					player.win(2*betAmt);
					continue;
				}
				
				// if dealer has a greater sum
				if (dealerSum > sum) {
					System.out.println("Dealer wins by a score of " + dealerSum + " to " + sum + "!");
					System.out.println("Dealer wins " + betAmt + " chips from you.");
				}
				// if you have a greater sum
				else if (sum > dealerSum) {
					System.out.println("You win by a score of " + sum + " to " + dealerSum + "!");
					System.out.println("You win " + betAmt + " chips from the dealer.");
					player.win(2*betAmt);
				}
				// push
				else {
					System.out.println("A push of " + sum + ".");
					System.out.println("You receive your bet of " + betAmt + " chips back.");
					player.win(betAmt);
				}
				System.out.println();
				
			}
		}
	
		reader.close();
	}
}