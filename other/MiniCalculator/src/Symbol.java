
public class Symbol {
	
	public String symbol = "";
	public boolean isNumber = false;
	public boolean isOperator = false;
	
	public Symbol(String s, boolean isN){
		
		symbol = s;
		
		if(isN)
			isNumber = true;
		else
			isOperator = true;
		
	}

}
