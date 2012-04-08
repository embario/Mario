
public class Point extends java.awt.Point{
	
	private int _hValue = 0;
	private int _gValue = 0;
	private Point _nextPoint = null;
	private Point _prevPoint = null;
	
	
	public Point (Point p){
		super(p.x, p.y);
		this.setHValue(this._hValue);
		this.setGValue(this._gValue);
	}
	
	public Point (int x, int y){
		super(x,y);
	}
	
	protected int getHValue() { return this._hValue;}
	protected void setHValue(int val){ this._hValue= val;}
	
	protected int getGValue() { return this._gValue;}
	protected void setGValue(int val){ this._gValue= val;}
	
	protected Point getNextPoint(){ return this._nextPoint;}
	protected void setNextPoint (Point p){ this._nextPoint = p;}
	
	protected Point getPrevPoint(){ return this._prevPoint;}
	protected void setPrevPoint (Point p){ this._prevPoint = p;}
	
	public String toString () { return "("+ this.x + "," + this.y + ")";}

}
