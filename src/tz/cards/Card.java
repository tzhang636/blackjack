package tz.cards;

/**
 * Represents a playing card instance.
 * @author Thomas Zhang
 *
 */
public class Card {
	/**
	 * One of four possible suits for this card.
	 */
	private Suit suit;
	
	/**
	 * One of 13 possible ranks for this card (Ace, 2-10, Jack-King).
	 */
	private Rank rank;
	
	/**
	 * Card constructor.
	 * @param suit - suit of this card
	 * @param rank - rank of this card
	 */
	public Card(Suit suit, Rank rank) {
		this.suit = suit;
		this.rank = rank;
	}
	
	/**
	 * Returns the suit of this card.
	 * @return suit
	 */
	public Suit getSuit() {
		return suit;
	}
	
	/**
	 * Returns the rank of this card.
	 * @return rank
	 */
	public Rank getRank() {
		return rank;
	}
	
	@Override
	public String toString() {
		return "[" + rank.toString() + " of " + suit.toString() + "]";
	}
}