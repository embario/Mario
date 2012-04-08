package game;

import blackjack.BlackJackGame;
import poker.PokerGame;

/**GameFactory:
 * 
 * Responsible for creating the game that is requested by the Driver class.
 * 
 * 
 * @author mbarrenecheajr
 *
 */
public class GameFactory{
	
	public static Playable createGame(String phrase, Player [] players){
		
		if(phrase.equalsIgnoreCase("poker"))
			return new PokerGame(players);
		else if(phrase.equalsIgnoreCase("blackjack"))
			return new BlackJackGame(players);
		else
			return null;
		
	}//end createGame();

}
