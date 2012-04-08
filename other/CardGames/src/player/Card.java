package game;

/**
 * Card
 * <p>
 * This class represents the Card object that includes enumerations like SUIT, FACE, COLOR, and NUMBER. Different card games have different rules,
 * so it's important to be flexible when it comes to setting unique rules such as value for face cards.
 * <p>
 * 
 * @author mbarrenecheajr
 *
 */
public class Card {

	private SUIT m_suit;
	private FACE m_face;
	private COLOR m_color;
	private NUMBER m_number;
	
	//Other variables
	private boolean m_hidden;
	
	private Card(SUIT suit, FACE face, COLOR color, NUMBER number){
		
		this.m_suit = suit;
		this.m_face = face;
		this.m_color = color;
		this.m_number = number;
		this.m_hidden = false;
		
	}
	
	//Factory Method for creating Cards
	public static Card createCard (SUIT suit, FACE face, COLOR color, NUMBER number){ 
		return new Card (suit, face, color, number);
		}
	
	//Getters
	public SUIT getSuit(){ return this.m_suit;}
	public FACE getFace(){ return this.m_face;}
	public COLOR getColor(){ return this.m_color;}
	public NUMBER getNumber(){ return this.m_number;}
	public boolean isHidden(){ return this.m_hidden;}
	
	//Setters
	public void setSuit(SUIT suit){ this.m_suit = suit;}
	public void setFace(FACE face){ this.m_face = face;}
	public void setColor(COLOR color){ this.m_color = color;}
	public void setNumber(NUMBER number){ this.m_number = number;}
	public void setHidden(boolean b){ this.m_hidden = b;}
	
	
	public String toString(){
		
		if(this.isHidden() == true)
			return "Hidden\n";
		if(this.m_face == FACE.NONE)
			return (m_number + " Card: " + m_suit + ", " + m_color + "\n");
		else
			return (m_face + " Card: " + m_suit + ", " + m_color + "\n");
	}
	
	/** Enumerations for Identifying Unique Actual Cards **/
	public enum SUIT{ SPADE, CLUB, DIAMOND, HEART;};
	public enum FACE{ JACK, QUEEN, KING, ACE, NONE;};
	public enum COLOR{ BLACK, RED;};	
	public enum NUMBER{ TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, ONE_OR_ELEVEN;};

}
