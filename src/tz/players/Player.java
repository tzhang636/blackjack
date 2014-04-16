package tz.players;

import java.util.ArrayList;
import java.util.List;

import tz.cards.Card;
import tz.cards.Rank;
import tz.cards.Values;

/**
 * Represents a player instance.
 * @author Thomas Zhang
 *
 */
public class Player {
	/**
	 * Cards in the player's hand.
	 */
	private List<Card> hand;
	
	/**
	 * Player's buy-in amount (in chips).
	 */
	private int buyIn;
	
	/**
	 * Player constructor.
	 * @param buyIn - the initial buy-in
	 */
	public Player(int buyIn) {
		hand = new ArrayList<Card>();
		this.buyIn = buyIn;
	}
	
	/**
	 * Player bets.
	 * @param amount - the amount to bet
	 * @return -1 on failure, 0 on success
	 */
	public int bet(int amount) {
		if (amount <= 0 || amount > buyIn) {
			return -1;
		}
		buyIn -= amount;
		return 0;
	}
	
	/**
	 * Player wins chips.
	 * @param amount - the chips won
	 */
	public void win(int amount) {
		if (amount <= 0) {
			System.err.println("Cannot win fewer than 0 chips.");
			System.exit(1);
		}
		buyIn += amount;
	}
	
	/**
	 * Clears a player's hand.
	 */
	public void clearHand() {
		hand.clear();
	}

	/**
	 * Adds a card to the player's hand.
	 * @param card - the card to be added
	 * @return if player busted or not
	 */
	public boolean addCard(Card card) {
		hand.add(card);
		return getHandSum() > 21;
	}
	
	/**
	 * Gets the sum of values of player's hand.
	 * @return the hand sum
	 */
	public int getHandSum() {
		int sum = 0;
		int numSoftAces = 0;
		
		for (Card card : hand) {
			Rank rank = card.getRank();
			
			if (rank == Rank.ACE) {
				++numSoftAces;
				sum += Values.SOFTACEVAL;
			}
			else if (rank.ordinal() >= Rank.JACK.ordinal()) {
				sum += Values.FACEVAL;
			}
			else { // Two-Ten
				sum += rank.ordinal() + 1;
			}
		}
		
		while (sum > 21 && numSoftAces > 0) {
			sum -= Values.SOFTACEVAL;
			sum += Values.HARDACEVAL;
			--numSoftAces;
		}

		return sum;
	}
	
	/**
	 * Get number of hand in player's hand.
	 * @return number of hand
	 */
	public int getNumCards() {
		return hand.size();
	}
	
	/**
	 * Converts player's hand to a String.
	 * @param hideFirstCard - whether to show first card
	 * @return the player's hand as a String
	 */
	public String getHand(boolean hideFirstCard) {
		StringBuffer str = new StringBuffer();
		int i = 0;
		for (Card card : hand) {
			if (i == 0 && hideFirstCard) {
				str.append("[face-down]");
			} 
			else {
				str.append(card.toString());
			}
			str.append("\n");
			++i;
		}
		return str.toString();
	}
	
	/**
	 * Gets the amount of chips the player have remaining.
	 * @return amount of chips left
	 */
	public int getBuyIn() {
		return buyIn;
	}
}