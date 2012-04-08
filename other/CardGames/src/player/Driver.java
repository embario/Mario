package game;

import java.util.Scanner;

/** CardGames!
 *  <p>
 *  This is a game suite of card games developed by Mario Barrenechea
 *  in Fall of 2009 (while handling tough Computer Science courses!).
 *  <p>
 *  Basic Flow Layout: 
 *  <p>
 *  [Main] -> [getPlayers(Scanner, Arguments)] -> [startGameLoop]
 *  <p>
 *  startGameLoop() begins our card game depending on what the player wants to play.
 *  <p>
 *  
 * @author mbarrenecheajr **/

public class Driver {
	
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		Player [] players = getPlayers(scan, args);
		
		if(players == null)
			System.exit(0);
		
		startGameLoop(players, scan);

	}
	
	public static Player [] getPlayers(Scanner scan, String [] args){
		
		if(args == null){
			System.out.println("We need the Number of Players to play Cards!");
			return null;
		}
		
		//Number of Players
		int numOfPlayers = 0;
		
		try{ 
			//Get Number of Players from First Argument
			numOfPlayers = Integer.parseInt(args[0]);
			if(numOfPlayers < 1)
				throw new NumberFormatException();
		}
		catch(NumberFormatException ex){
			System.out.println("Invalid Input Parameter");
			return null;
		}
		
		Player [] players = new Player [numOfPlayers];
		
		for(int i = 0; i < numOfPlayers; i++){
			
			System.out.println("Hello there! Welcome to CardGames!");
			System.out.print("May I have your name? : ");
			String name = scan.nextLine();
			players[i] = new Player(name.trim(), Player.PLAYER_AMOUNT);
			
		}
		
		return players;
	}//end getPlayers();
	
	
	public static void startGameLoop(Player [] players, Scanner scan){
		
		String phrase;
		Player player = players[0]; //Naturally, the only person playing this game (You) 
		boolean donePlaying = false;
		
		while(donePlaying == false){
			
			System.out.println("\n" + player.getName() + ", you have $" + player.getMoney() + " in the house. What kind of Card Game would you like to play?");
			System.out.println("Your choices are: {Poker, Blackjack}\n");	
			System.out.print(":>");
			Playable game; //Our Game to Play
			phrase = scan.nextLine().trim();
			
			if(phrase.equalsIgnoreCase("quit") || phrase.equalsIgnoreCase("exit")){
				
				System.out.println("Are you sure you want to quit?");
				phrase = scan.nextLine().trim();
				
				if(phrase.equalsIgnoreCase("yes"))
					donePlaying = true;
				
				continue;
			}//end if quit/exit conditional
			
			game = GameFactory.createGame(phrase, players);
			
			if(game == null)
				System.out.println("Invalid Input... \n");
			else
				game.playGame();
			
		}//end while loop
		
	}

}
