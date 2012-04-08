import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ProgHW{
	
	public static void main (String args []){
		
		//Create Graph Object
		Graph graph = null;
		try{
			
			graph = processArgs (args);
			
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		if(graph != null){
			
			//Print out Adjacency List for correctness.
		    //graph->printAdjacencyList();

		    //set <Vertex, ltVertex>::iterator itr = graph->getVertices()->begin();

		    //Perform BFS
		    //graph->bfs(*itr);

		}
	}


	/**
	processArgs();
	This function accepts program arguments and sets up
	the Graph so that the vertex pairs from the input
	text file are represented in the returning Graph. 
	 * @throws FileNotFoundException  
	**/
	public static Graph processArgs (String args []) throws FileNotFoundException {
		
		//A new Graph object that we eventually return.
		Graph graph = null;

		if (args.length != 1){
			System.out.printf("Error processing args: Argument count invalid.\n");
			return null;
		}

		File file = new File(args[0]);
		Scanner infile = new Scanner(file);

		if(file.exists() == true){
			
		    //The First line specifies how many vertices there are in the Graph.
		    String line = infile.next();
		    int numVertices = Integer.parseInt(line);

		    //Result Graph to Return eventually
		    graph = new Graph (numVertices);
		    ArrayList <Vertex> vertices = graph.getVertices();

		    //Iterate and get a pair of vertices each time.
		    while(infile.hasNext() == true){

		      //Create Vertices from this line and connect them.
		      Vertex v1 = new Vertex();
		      Vertex v2 = new Vertex();
		      v1.color = Color.WHITE;
		      v2.color = Color.WHITE;	      
		      v1.value = infile.nextInt();
		      v2.value = infile.nextInt();

		      //TODO: Your next task is to think about redundancy in a sense: You're getting
		      //SEG FAULTS because your adjacent vertices are essentially dummies that represent
		      //other vertices in the graph (i.e. they have no adj lists). How can you duplicate
		      // information for all |V| vertices and maintain their connections?

		      Vertex found;
		      if(vertices.contains(v1) == true){
		    	  
		    	  found = vertices.get(vertices.lastIndexOf(v1));
		    	  found.adjacentVertices.add(v2);
		    	  
		      }else{
		    	  vertices.add(v1);
		    	  v1.adjacentVertices.add(v2);
		      }
		      if(vertices.contains(v2) == true){
		    	  
		    	  found = vertices.get(vertices.lastIndexOf(v2));
		    	  found.adjacentVertices.add(v1);
		    	  
		      }else{
		    	vertices.add(v2);
		    	v2.adjacentVertices.add(v1);
		      }

		    }//end while loop

		    infile.close();
		  }//end if
	  
	  else{
		  System.err.println("Unable to open file" + file);
		  return null;
	  }

	  return graph;
	}

	
	

}