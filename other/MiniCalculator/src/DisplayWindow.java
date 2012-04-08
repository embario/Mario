import java.awt.*;
import javax.swing.*;

public  class DisplayWindow extends JFrame{


	private static final long serialVersionUID = 1L;
	private Container c;
	private int width, height;

	public DisplayWindow(String name, int w, int h){
		super(name);
		width = w;
		height = h;
		this.setLocationByPlatform(true);
		this.setResizable(false);
		c = this.getContentPane();
	}

	public void addPanel(JPanel p){
		p.setPreferredSize(new Dimension(width,height));
		c.add(p);
	}

	public void setFrame(int width, int height){//added method
		this.width = width;
		this.height = height;
	}

	public void showFrame(int w, int h){
		this.pack();
		this.setFrame(w, h);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
