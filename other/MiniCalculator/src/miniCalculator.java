import java.util.LinkedList;
import java.util.Stack;


public class miniCalculator {
	
	LinkedList <String> list;
	static Stack <Symbol> stack;
	
	
	public miniCalculator(){
		
		list = new LinkedList <String>();
		stack = new Stack <Symbol> ();
	}
	
	public static String compute(String expression){
		
		//Problem with Parsing expression: I keep getting the old unnecessary stuff. Need
		// to find a way to get rid of it.
		
		String [] symbols = expression.split("\n");
		symbols = expression.split("");
		
		for(String s: symbols){
			
			if(s.matches("[\\d]*"))
				stack.push(new Symbol(s,true));
			
			else if(s.equals("+") || s.equals("*") || s.equals("/") || s.equals("-"))
				stack.push(new Symbol(s, false));
		
		}
		
		
		stack.clear();
		return "STRING";
		
		
	}
	
	public void clearList(){ list.clear();}
	
	public LinkedList<String> getList(){ return list;}
	public String toString(){ return list.toString();}

}
