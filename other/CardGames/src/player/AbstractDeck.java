package game;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**AbstractDeck:
 * 
 * This class implements the Iterable interface so that we can classify it
 * as a list of Card objects with which we can do useful things like shuffle,
 * point to the next or previous card, and reset.
 * <p>
 * 
 * @author mbarrenecheajr
 *
 */
public abstract class AbstractDeck implements Iterable <Card>{

	protected LinkedList <Card> m_deck = new LinkedList <Card> ();
	private Random m_rand = new Random();
	private int m_index = 0;

	public AbstractDeck(){}
	
	@Override
	public Iterator<Card> iterator() { return this.m_deck.iterator();}
	
	public Card nextCard(){ return this.m_deck.get(this.m_index++);}
	
	public Card previousCard(){ return this.m_deck.get(this.m_index--);}
	
	public boolean hasNext(){ return (this.m_index < this.m_deck.size());}
	
	public void reset(){ this.m_index = 0;}
	
	//Getters
	public Random getRandomGenerator(){ return this.m_rand;}
	
	/** Shuffle the Deck **/
	public abstract void shuffle();
	
	/** Fill the Deck with Cards **/
	public abstract void fillDeck();

	
}
