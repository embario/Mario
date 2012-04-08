package game;

import java.util.ArrayList;

public class Player {
	
	private ArrayList <Card> m_hand;
	private String m_name;
	private double m_money;
	private ChipStash m_chipstash;
	private int m_gamesPlayed;
	public final static double PLAYER_AMOUNT = 500.00;
	
	private static Player m_dealer = new Player("dealer", 10000.00);
	
	public Player(String name, double amount){
		
		this.m_name = name;
		this.m_money = amount;
		this.m_gamesPlayed = 0;
		
		this.m_hand = new ArrayList <Card>();
	}
	
	//Dealer Singleton
	public static Player getDealer(){
		return Player.m_dealer;
	}
	
	//Getters
	public String getName(){ return this.m_name;}
	public double getMoney(){ return this.m_money;}
	public int getGamesPlayed(){ return this.m_gamesPlayed;}
	public ArrayList <Card> getHand(){ return this.m_hand;}
	
	//Setters
	public void setMoney(double amount){ this.m_money = amount;}
	public void setName(String name){ this.m_name = name;}
	
	//Supplements
	public void addMoney(double amount){ this.m_money += amount;}
	public void subtractMoney(double amount){ this.m_money -= amount;}
	public void incrementGamesPlayed(){ this.m_gamesPlayed++;}
	
	public void sortMoney(){
		
		double money = this.getMoney();
		int redCount = 0;
		int greenCount = 0;
		int blackCount = 0;
		
	}

}
