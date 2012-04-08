package blackjack;

import game.Playable;

public class BlackJack {
	
	private BlackJackDeck m_deck;
	private BlackJackGame m_game;
	
	public BlackJack (Playable game){
		
		this.m_game = (BlackJackGame) game;
		
		//Create, Fill, and Shuffle Deck of Cards for BlackJack
		this.m_deck = (BlackJackDeck) this.m_game.getDeck();
		this.m_deck.fillDeck();
		this.m_deck.shuffle();
	}
	
	
	

}
