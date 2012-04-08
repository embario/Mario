import java.util.ArrayList;

//Our Coloring scheme for every vertex
enum Color{ WHITE, GREY, BLACK, RED, BLUE };


/**
Graph:
This class is an encapsulation of all the data structures involved
in maintaining a Graph.
 **/
public class Graph {
	
	
	private int numVertices;
	private ArrayList <Vertex> vertices = null;
	

	//Getters
	public ArrayList <Vertex> getVertices(){ return this.vertices;}
	public int getNumberVertices(){ return this.numVertices;}
	  
	//Constructor for Graph	  
	public Graph(int numVs){

		this.numVertices = numVs;
		this.vertices = new ArrayList <Vertex> ();

	    //Initialize our set of vertices
	    for(int i = 1; i <= this.numVertices; i++){
	      Vertex v = new Vertex (i, Color.WHITE, 0);
	      this.vertices.add(v);
	    }
	  }	
	
	/**** Functions ****/
	
	
	/** The bfs function performs Breadth-First Search on this Graph object. **/
	public void bfs(Vertex source){

	    /*
	    cout << endl << "Breadth-First Search (BFS) starting at Vertex (" << source.value << "):" << endl;
	    //Initialization
	    queue <Vertex> nextV;
	    source.color = GREY;
	    nextV.push(source);

	    //Now we iterate through the Graph
	    //Until the Queue is empty.
	    while (nextV.empty() == false){

	      Vertex v = nextV.front();
	      list <Vertex>::iterator vitr;

	      cout <<  "(" << v.value << ", " << this->getColorString(v) << ")";

	      //For every vertex adjV in ADJACENT(v) do...
	      for(vitr = v.adjacentVertices->begin(); vitr != v.adjacentVertices->end(); vitr++){

		Vertex adjV = *vitr;
		cout << "---- (" << adjV.value << ", " << this->getColorString(adjV) << ")" << endl;
		if(adjV.color == WHITE){
		  
		  adjV.color = GREY;
		  nextV.push(adjV);
		  adjV.distance += v.distance + 1;
		}
	      }//end for loop

	      v.color = BLACK;
	      nextV.pop();
	    }//end while
	    */
	  }

	/** Print Subgraph based on depth **/
	void printSubGraph(int depth){


	}

	/** Print the Adjacency List Representation for this Graph object. **/
	void printAdjacencyList(){
	    /*
	    cout << "Adjacency List for Graph: " << endl;
	    set <Vertex *, ltVertex> * vertices = this->getVertices();
	    set <Vertex * , ltVertex> :: iterator itr;

	    for(itr = vertices->begin(); itr != vertices->end(); itr++){
	   
	      Vertex  * v = *itr;
	      list <Vertex *> adjV = *(v->adjacentVertices);
	      list <Vertex * >::iterator vitr;

	      cout << v.value;
	      for(vitr = adjV.begin(); vitr != adjV.end(); vitr++){
		Vertex * adjvertex = *vitr;
		cout << " --- " << adjvertex->value;
	      }

	      cout << endl;
	    }//end for loop
	    */
	 } 
	  
	
}