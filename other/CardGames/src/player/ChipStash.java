package game;

import java.util.HashMap;
import java.util.Iterator;

public class ChipStash implements Iterable <Double>{
	
	private HashMap <String, Integer> m_chips; //TODO: The wrong data structure for holding chips!

	public ChipStash(double amount){
	
		this.m_chips = new HashMap <String, Integer>(4);
		ChipStash.sortChipStash(this);
	}
	
	//Factory Method for ChipStashes
	public static ChipStash createChipStash(double amount){
		return new ChipStash(amount);
	}
	
	public static void sortChipStash(ChipStash s){
		
		//TODO: Sort into Chips!
		
	}

	@Override
	public Iterator <Double> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public class Money{
		
		public static final double WHITE_CHIP = 1.00;
		public static final double RED_CHIP = 5.00;
		public static final double GREEN_CHIP = 25.00;
		public static final double BLACK_CHIP = 100.00;
		
	}//end Inner Class

	
}
