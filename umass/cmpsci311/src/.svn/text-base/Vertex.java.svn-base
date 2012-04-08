import java.util.ArrayList;

	public class Vertex{
		
		int value;    //Value/Number of Vertex.
		int distance; //Distance relative to source vertex when performing BFS.
		Color color;  
		ArrayList <Vertex> adjacentVertices;
		
		public Vertex(){}
		public Vertex (int val, Color col, int dist){
			this.value = val;
			this.color = col;
			this.distance = dist;
			this.adjacentVertices= new ArrayList <Vertex> ();
		}
		
		public String toString(){
			String result = "" + this.value;
			for(Vertex v : this.adjacentVertices)
				result += " --- " + v.value;
			return result;
		}
		  
	}//end Vertex class