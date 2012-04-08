import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JEditorPane;



public class CalculatorPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	miniCalculator calc;
	
	JPanel operations = new JPanel();
	JPanel numbers = new JPanel();
	JPanel buttons = new JPanel();
	
	JEditorPane window = new JEditorPane();
	
	JButton zero = new JButton("0");
	JButton one = new JButton("1");
	JButton two = new JButton("2");
	JButton three = new JButton("3");
	JButton four = new JButton("4");
	JButton five = new JButton("5");
	JButton six = new JButton("6");
	JButton seven = new JButton("7");
	JButton eight = new JButton ("8");
	JButton nine = new JButton ("9");
	
	JButton multiply = new JButton ("x");
	JButton divide = new JButton("/");
	JButton add = new JButton ("+");
	JButton subtract = new JButton ("-");
	JButton equal = new JButton("=");
	JButton clear = new JButton("C");
	
	
	
	public CalculatorPanel(){
	
		//TODO: Choose a LAYOUT!
		calc = new miniCalculator();
		setButtons();
		setLayout(new GridLayout(2,0));
		window.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
		this.add(window);
		this.add(buttons);
		
		
	}

	public void actionPerformed(ActionEvent e){
		
		String old, current = "";
		boolean complete = false;
		
		//Is our button a number?
		JButton j = (JButton)e.getSource();
		String s = j.getText();
		int number = -1;
		try{ number = Integer.parseInt(s);}
		catch(NumberFormatException ex){}
		
		//If our Button pressed was a number..
		if(number >= 0){
			
			old = window.getText();
			window.setText(current = old + number);
			window.requestFocus();
			
		}
		//If our button pressed was the clear button..
		else if(e.getSource() == clear){
			
			window.setText("");
			window.requestFocus();
			calc.clearList();
		}
		//If our button was an operator...
		else if(e.getSource() == add || e.getSource() == subtract){
			
			old = window.getText();
			if(e.getSource() == add)
				window.setText(current = old + "+");
			else
				window.setText(current = old + "-");
			window.requestFocus();
			
		}
		//If our button was an operator...
		else if(e.getSource() == multiply || e.getSource() == divide){
			
			old = window.getText();
			if(e.getSource() == multiply)
				window.setText(current = old + "*");
			else
				window.setText(current = old + "/");
			
			window.requestFocus();
		}
		
		else if(e.getSource() == equal){
			
			old = window.getText();
			
			//Perform Computation
			current = "\n" + miniCalculator.compute(old) + "\n";
			window.setText(old + current);
			window.requestFocus();
			complete = true;
			
			calc.clearList();
		}
		
		if(!complete)
			calc.getList().add(current);	
		
	}
	
	public void setButtons(){
		
		window.setText("");
		operations.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		numbers.setLayout(new GridLayout(4,3));
		
		zero.addActionListener(this);
		one.addActionListener(this);
		two.addActionListener(this);
		three.addActionListener(this);
		four.addActionListener(this);
		five.addActionListener(this);
		six.addActionListener(this);
		seven.addActionListener(this);
		eight.addActionListener(this);
		nine.addActionListener(this);
		
		add.addActionListener(this);
		equal.addActionListener(this);
		subtract.addActionListener(this);
		multiply.addActionListener(this);
		divide.addActionListener(this);
		clear.addActionListener(this);
		
		
		numbers.add(seven);
		numbers.add(eight);
		numbers.add(nine);
		numbers.add(four);
		numbers.add(five);
		numbers.add(six);
		numbers.add(one);
		numbers.add(two);
		numbers.add(three);
		numbers.add(zero);
		
		
		operations.add(add,gc);
		operations.add(subtract,gc);
		operations.add(multiply,gc);
		operations.add(divide,gc);
		operations.add(equal,gc);
		operations.add(clear,gc);
		
		buttons.add(numbers);
		buttons.add(operations);
	}
	
	public String toString(){ return calc.toString();}
	
}
