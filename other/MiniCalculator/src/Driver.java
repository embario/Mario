
public class Driver {
	
	static miniCalculator calc;
	static CalculatorPanel f;
	
	public static void main(String [] args){
		
		calc = new miniCalculator();
		DisplayWindow d = new DisplayWindow("miniCalculator",400,400);
		CalculatorPanel cp = new CalculatorPanel();
		d.addPanel(cp);
		d.showFrame(400,400);
	}

}
