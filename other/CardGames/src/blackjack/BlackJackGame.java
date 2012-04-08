package blackjack;

import java.util.ArrayList;
import java.util.Scanner;

import game.AbstractCardGame;
import game.Card;
import game.Playable;
import game.Player;

/**BlackJackGame:
 * 
 * Central class that extends AbstractCardGame and implements the Playable interface (so that it's classified as a game we can play).
 * The benefits of extending the AbstractCardGame abstract class is to inherit several members and functions like dealCards() 
 * that make sense in Blackjack as a casino card game.
 * <p>
 * 
 * @author mbarrenecheajr
 *
 */
public class BlackJackGame extends AbstractCardGame implements Playable{	
	
	/** Constructor **/
	public BlackJackGame(Player [] players){

		//Instantiate Key Items
		this.m_deck = new BlackJackDeck();
		this.m_players = new ArrayList <Player> ();
		
		//Add the players to this List
		for(Player p: players)
			this.m_players.add(p);
		
		//Prepare the card deck
		this.m_deck.fillDeck();
		this.m_deck.shuffle();
	}
	
	@Override
	public void playGame() {
		
		//TODO: Logic Please!
		//TODO: Figure out Betting Rules!
		//TODO: Continue Loops!
		
		Card c;
		Player dealer = Player.getDealer();
		while(this.isGameOver() == false){
			
			//The Player and the Dealer's Hand
			ArrayList<Card> pHand, dHand; 
			
			//For Each Player in the Game...
			for(Player p: this.getPlayers()){
				
				this.makeBet(p);
				
				//Player
				pHand = p.getHand();
				c = this.m_deck.nextCard();
				c.setHidden(true);
				pHand.add(c);
				
				//Dealer
				dHand = dealer.getHand();	 
				c = this.m_deck.nextCard();
				c.setHidden(true);
				dHand.add(c);
	
			}
			
		}//End Game Over Loop
		
		
	}

	
	public void makeBet(Player p){
		
		Scanner scan = new Scanner(System.in);
		while(true){
			
			System.out.println(p.getName() + ", Please make your bet");
			System.out.println("(1) 1 Black Chip ($25)");
			//System.out.println("(")
			//TODO: Finish this!
			
			
		}
		
	}
	@Override
	public void endGame() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void dealCards() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	

}
