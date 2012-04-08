package blackjack;

import java.util.Collections;
import java.util.Random;

import game.AbstractDeck;
import game.Card;
import game.Card.COLOR;
import game.Card.FACE;
import game.Card.NUMBER;
import game.Card.SUIT;

/** BlackJackDeck:
 * 
 * An Implementation of the AbstractDeck class for BlackJack. Call fillDeck() once this deck is created.
 * <p>
 * 
 * @author mbarrenecheajr
 *
 */
public class BlackJackDeck extends AbstractDeck{

	@Override
	public void fillDeck() {
		
		//Aces
		this.m_deck.add(Card.createCard(SUIT.CLUB, FACE.ACE, COLOR.BLACK, NUMBER.ONE_OR_ELEVEN));
		this.m_deck.add(Card.createCard(SUIT.SPADE, FACE.ACE, COLOR.BLACK, NUMBER.ONE_OR_ELEVEN));
		this.m_deck.add(Card.createCard(SUIT.HEART, FACE.ACE, COLOR.RED, NUMBER.ONE_OR_ELEVEN));
		this.m_deck.add(Card.createCard(SUIT.DIAMOND, FACE.ACE, COLOR.RED, NUMBER.ONE_OR_ELEVEN));
		
		//Twos
		this.m_deck.add(Card.createCard(SUIT.CLUB, FACE.NONE, COLOR.BLACK, NUMBER.TWO));
		this.m_deck.add(Card.createCard(SUIT.SPADE, FACE.NONE, COLOR.BLACK, NUMBER.TWO));
		this.m_deck.add(Card.createCard(SUIT.HEART, FACE.NONE, COLOR.RED, NUMBER.TWO));
		this.m_deck.add(Card.createCard(SUIT.DIAMOND, FACE.NONE, COLOR.RED, NUMBER.TWO));
		
		//Threes
		this.m_deck.add(Card.createCard(SUIT.CLUB, FACE.NONE, COLOR.BLACK, NUMBER.THREE));
		this.m_deck.add(Card.createCard(SUIT.SPADE, FACE.NONE, COLOR.BLACK, NUMBER.THREE));
		this.m_deck.add(Card.createCard(SUIT.HEART, FACE.NONE, COLOR.RED, NUMBER.THREE));
		this.m_deck.add(Card.createCard(SUIT.DIAMOND, FACE.NONE, COLOR.RED, NUMBER.THREE));
		
		//Fours
		this.m_deck.add(Card.createCard(SUIT.CLUB, FACE.NONE, COLOR.BLACK, NUMBER.FOUR));
		this.m_deck.add(Card.createCard(SUIT.SPADE, FACE.NONE, COLOR.BLACK, NUMBER.FOUR));
		this.m_deck.add(Card.createCard(SUIT.HEART, FACE.NONE, COLOR.RED, NUMBER.FOUR));
		this.m_deck.add(Card.createCard(SUIT.DIAMOND, FACE.NONE, COLOR.RED, NUMBER.FOUR));
		
		//Fives
		this.m_deck.add(Card.createCard(SUIT.CLUB, FACE.NONE, COLOR.BLACK, NUMBER.FIVE));
		this.m_deck.add(Card.createCard(SUIT.SPADE, FACE.NONE, COLOR.BLACK, NUMBER.FIVE));
		this.m_deck.add(Card.createCard(SUIT.HEART, FACE.NONE, COLOR.RED, NUMBER.FIVE));
		this.m_deck.add(Card.createCard(SUIT.DIAMOND, FACE.NONE, COLOR.RED, NUMBER.FIVE));
		
		//Sixes
		this.m_deck.add(Card.createCard(SUIT.CLUB, FACE.NONE, COLOR.BLACK, NUMBER.SIX));
		this.m_deck.add(Card.createCard(SUIT.SPADE, FACE.NONE, COLOR.BLACK, NUMBER.SIX));
		this.m_deck.add(Card.createCard(SUIT.HEART, FACE.NONE, COLOR.RED, NUMBER.SIX));
		this.m_deck.add(Card.createCard(SUIT.DIAMOND, FACE.NONE, COLOR.RED, NUMBER.SIX));
		
		//Sevens
		this.m_deck.add(Card.createCard(SUIT.CLUB, FACE.NONE, COLOR.BLACK, NUMBER.SEVEN));
		this.m_deck.add(Card.createCard(SUIT.SPADE, FACE.NONE, COLOR.BLACK, NUMBER.SEVEN));
		this.m_deck.add(Card.createCard(SUIT.HEART, FACE.NONE, COLOR.RED, NUMBER.SEVEN));
		this.m_deck.add(Card.createCard(SUIT.DIAMOND, FACE.NONE, COLOR.RED, NUMBER.SEVEN));
		
		//Eights
		this.m_deck.add(Card.createCard(SUIT.CLUB, FACE.NONE, COLOR.BLACK, NUMBER.EIGHT));
		this.m_deck.add(Card.createCard(SUIT.SPADE, FACE.NONE, COLOR.BLACK, NUMBER.EIGHT));
		this.m_deck.add(Card.createCard(SUIT.HEART, FACE.NONE, COLOR.RED, NUMBER.EIGHT));
		this.m_deck.add(Card.createCard(SUIT.DIAMOND, FACE.NONE, COLOR.RED, NUMBER.EIGHT));
		
		//Nines
		this.m_deck.add(Card.createCard(SUIT.CLUB, FACE.NONE, COLOR.BLACK, NUMBER.NINE));
		this.m_deck.add(Card.createCard(SUIT.SPADE, FACE.NONE, COLOR.BLACK, NUMBER.NINE));
		this.m_deck.add(Card.createCard(SUIT.HEART, FACE.NONE, COLOR.RED, NUMBER.NINE));
		this.m_deck.add(Card.createCard(SUIT.DIAMOND, FACE.NONE, COLOR.RED, NUMBER.NINE));
		
		//Tens
		this.m_deck.add(Card.createCard(SUIT.CLUB, FACE.NONE, COLOR.BLACK, NUMBER.TEN));
		this.m_deck.add(Card.createCard(SUIT.SPADE, FACE.NONE, COLOR.BLACK, NUMBER.TEN));
		this.m_deck.add(Card.createCard(SUIT.HEART, FACE.NONE, COLOR.RED, NUMBER.TEN));
		this.m_deck.add(Card.createCard(SUIT.DIAMOND, FACE.NONE, COLOR.RED, NUMBER.TEN));
		
		//Jacks
		this.m_deck.add(Card.createCard(SUIT.CLUB, FACE.JACK, COLOR.BLACK, NUMBER.TEN));
		this.m_deck.add(Card.createCard(SUIT.SPADE, FACE.JACK, COLOR.BLACK, NUMBER.TEN));
		this.m_deck.add(Card.createCard(SUIT.HEART, FACE.JACK, COLOR.RED, NUMBER.TEN));
		this.m_deck.add(Card.createCard(SUIT.DIAMOND, FACE.JACK, COLOR.RED, NUMBER.TEN));
		
		//Queens
		this.m_deck.add(Card.createCard(SUIT.CLUB, FACE.QUEEN, COLOR.BLACK, NUMBER.TEN));
		this.m_deck.add(Card.createCard(SUIT.SPADE, FACE.QUEEN, COLOR.BLACK, NUMBER.TEN));
		this.m_deck.add(Card.createCard(SUIT.HEART, FACE.QUEEN, COLOR.RED, NUMBER.TEN));
		this.m_deck.add(Card.createCard(SUIT.DIAMOND, FACE.QUEEN, COLOR.RED, NUMBER.TEN));
		
		//Kings
		this.m_deck.add(Card.createCard(SUIT.CLUB, FACE.KING, COLOR.BLACK, NUMBER.TEN));
		this.m_deck.add(Card.createCard(SUIT.SPADE, FACE.KING, COLOR.BLACK, NUMBER.TEN));
		this.m_deck.add(Card.createCard(SUIT.HEART, FACE.KING, COLOR.RED, NUMBER.TEN));
		this.m_deck.add(Card.createCard(SUIT.DIAMOND, FACE.KING, COLOR.RED, NUMBER.TEN));
		
	}

	@Override
	public void shuffle() {
		
		//Pointer Index to 0
		this.reset();
		Random rand = this.getRandomGenerator();
		Collections.shuffle(this.m_deck, rand);
		
		//Shuffle once more, just for kicks
		Collections.shuffle(this.m_deck);
	}

}
