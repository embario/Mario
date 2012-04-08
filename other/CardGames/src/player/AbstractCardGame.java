package game;

import java.util.List;

/** AbstractCardGame:
 * 
 * 	This class implements the Playable interface and represents a casino card game
 * 	that takes advantage of several structures that specific card games can inherit from.
 * 	Design and Implementation go hand in hand here.
 * 	<p>
 * 
 * @author mbarrenecheajr
 *
 */
public abstract class AbstractCardGame implements Playable{

	/** Our Deck to Use **/
	protected AbstractDeck m_deck;
	
	/** List of Players **/
	protected List <Player> m_players;
	
	/** Boolean flag to determine if game is over **/
	protected boolean m_isGameOver = false;
	
	/** Override from Playable Interface **/
	@Override
	abstract public void playGame();
	@Override
	abstract public void endGame();

	/** Other Abstract Methods **/
	abstract public void dealCards();
	
	/** Other Implemented Methods **/
	public void shuffleDeck(){ this.m_deck.shuffle();}
	
	//Getters
	public AbstractDeck getDeck(){ return this.m_deck;}
	public boolean isGameOver(){ return this.m_isGameOver;}
	public List <Player> getPlayers(){ return this.m_players;}
	
	//Setters
	public void setGameOver(boolean b){ this.m_isGameOver = b;}
	

}
