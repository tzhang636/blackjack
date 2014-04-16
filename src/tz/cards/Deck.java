package tz.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Represents the cards in a standard 52-card deck.
 * @author Thomas Zhang
 * 
 */
public class Deck {
	/**
	 * Cards in the deck. Top card is the first index.
	 */
	public List<Card> deck;
	
	/**
	 * Size of a standard 52-card deck.
	 */
	public static final int DECK_SIZE = 52;

	/**
	 * Constructs a new deck of cards.
	 */
	public Deck() {
		deck = new ArrayList<Card>(DECK_SIZE);
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				deck.add(new Card(suit, rank));
			}
		}
	}
	
	/**
	 * Shuffles the deck.
	 */
	public void shuffle() {
		long seed = System.nanoTime();
		Collections.shuffle(deck, new Random(seed));
	}
	
	/**
	 * Deals the top card.
	 * @return the top card
	 */
	public Card deal() {
		if (deck.isEmpty()) {
			System.err.println("Deck is empty.");
			System.exit(1);
		}
		return deck.remove(0);
	}
	
	/**
	 * Gets number of cards in deck.
	 * @return number of cards remaining
	 */
	public int getNumCards() {
		return deck.size();
	}
	
	@Override
	public String toString() {
		StringBuffer str = new StringBuffer();
		for (Card card : deck) {
			str.append(card.toString());
			str.append("\n");
		}
		return str.toString();
	}
}