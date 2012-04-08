import java.util.Random;
import java.util.ArrayList;


/**
 * Programming Assignment:
 * Homework 1: Question 2(c)
 * 
 * @author mbarrenecheajr
 */
public class RunKnightHeuristic {

	// FINAL
	private static final int NUM_PROBLEM_INSTANCES = 100;
	private static final int CHESSBOARD_LENGTH = 8;
	
	//GLOBAL
	private static Point _startState = new Point (0,0);
	private static Point _goalState = new Point (0,0);
	private static Random _rand = null;

	public static void main(String args[]) {

		_rand = new Random();
	
		System.out.println("Problem Instance #, Problem Time (ms), Number of Nodes Expanded, Solution Length:");
		for (int i = 1; i <= NUM_PROBLEM_INSTANCES; i++) {

			// Start our Timer
			double startTime = (double) System.currentTimeMillis();
			
			//Two Important Variables to Report
			int numNodesExpanded = 0;
			int solutionLength = 0;
			
			//Other Utilities
			ArrayList <Point> openList = new ArrayList <Point> ();
			ArrayList <Point> closedList = new ArrayList <Point> ();
			
			// Generate Random (x,y) GOAL State
			int x = _rand.nextInt(CHESSBOARD_LENGTH);
			int y = _rand.nextInt(CHESSBOARD_LENGTH);
			
			_goalState.setLocation(x, y);
			
			//Perform A* Search on this problem instance and return important results
			A_StarSearch(_goalState, _startState, openList, closedList);
			
			numNodesExpanded = closedList.size();
			Point point = _startState;
			
			//Get the Length of the Solution for this problem instance.
			while (point.getNextPoint() != null){
				solutionLength++;
				point = point.getNextPoint();
			}
			
			//Capture Problem Time
			double elapsedTime = Math.abs(System.currentTimeMillis() - startTime);
			reportData(i, _goalState, numNodesExpanded, solutionLength, elapsedTime);
		}

	}// end main method
	
	/**
	 * reportData:
	 * <p>
	 * This function accepts data generated from performing A* search and dumps them to the console.
	 * 
	 * @param i
	 * @param x
	 * @param y
	 * @param numNodesExpanded
	 * @param solutionLength
	 * @param problemTime
	 */
    private static void reportData (int i, Point goal, int numNodesExpanded, int solutionLength, double problemTime){
		System.out.println(i + ", " + problemTime + ", " + numNodesExpanded + ", " + solutionLength);
	}

	/**
	 * A_StarSearch:
	 * <p>
	 * This function accepts an (x,y) GOAL State Pair and an (a,b) current State Pair and performs A* on the chess board 8x8 state space. The Heuristic function f = g+h is admissible because
	 * it only evaluates the minimum allowed L-Shaped moves for the knight to move around in this space, and so finding this value should always be equal to or less than this underestimate.
	 * <p>
	 * @param x
	 * @param y
	 * @param a
	 * @param b
	 * @param numNodesExp
	 * @param solutionLength
	 */
	private static void A_StarSearch(Point goalState, Point currentState, ArrayList <Point> openList, ArrayList <Point> closedList){
		
		//if (_chessboard [currentState.x][currentState.y] == _chessboard [goalState.x][goalState.y]);
		if (currentState.x == goalState.x && currentState.y == goalState.y);
		else{
			
			//Focus on the current state: What is the cost from it to the goal state (H-value)?
			currentState.setHValue(Math.abs(currentState.x - goalState.x) + Math.abs(currentState.y - goalState.y));
			
			//Generate and Examine AT MOST 8 Possible Points from current State
			ArrayList <Point> possiblePairs = new ArrayList <Point> (8);
			for (int i = 0; i < 8; i++)
				possiblePairs.add(new Point (0,0));
			
			//Left L-Shaped Moves
			possiblePairs.get(0).setLocation(currentState.x - 2, currentState.y - 1);
			possiblePairs.get(1).setLocation(currentState.x - 2, currentState.y + 1);
			
			//Up L-Shaped Moves
			possiblePairs.get(2).setLocation(currentState.x - 1, currentState.y + 2);
			possiblePairs.get(3).setLocation(currentState.x + 1, currentState.y + 2);
			
			//Right L-Shaped Moves
			possiblePairs.get(4).setLocation(currentState.x + 2, currentState.y + 1);
			possiblePairs.get(5).setLocation(currentState.x + 2, currentState.y - 1);
			
			//Down L-Shaped Moves
			possiblePairs.get(6).setLocation(currentState.x + 1, currentState.y - 2);
			possiblePairs.get(7).setLocation(currentState.x - 1, currentState.y - 2);
			
			//Remove pairs that aren't possible.
			removeImpossiblePairs (possiblePairs, closedList);
			//Add this point to the Frontier (Open List)
			openList.addAll(possiblePairs);

			//Evaluate possible pairs with the Heuristic Function [f(n) = g (cost from initial node to n) + h (cost from n to goal state)]
			int minCost = 100;
			for (Point p: openList){
				
				//Keep these two points linked one-way. This helps to explore other solutions if the F-Value 
				//for a shallower node is less costly than a deeper one. This also helps to trace back the solution later.
				p.setPrevPoint(currentState);
				
				//Calculate this point's cost from the initial node to it.
				p.setGValue(currentState.getGValue() + Math.abs(p.x - currentState.x) + Math.abs(p.y - currentState.y));
				
				//Calculate this point's cost from itself to the goal state (H-Value).
				p.setHValue(Math.abs(p.x - goalState.x) + Math.abs(p.y - goalState.y));
				
				//Finally, calculate this point's F-Value to determine candidacy for the next Point.
				int fValue = p.getGValue() + p.getHValue();
				
				//Find the next Point with the smallest F-Value.
				if (fValue <= minCost == true){
					minCost = fValue;
					currentState.setNextPoint(p);
				}
			}

			//Remove the Next Point from the Frontier (Open List)
			openList.remove(currentState.getNextPoint());
			//Add the Current Point to the Closed List
			closedList.add(currentState);
			
			//Recursively Perform A* with the new settings, hopefully one-step closer to the goal state.
			A_StarSearch(goalState, currentState.getNextPoint(), openList, closedList);
			
		}
	}//end A*
	
	/**
	 * removeImpossiblePairs:
	 * <p>
	 * Helper Function for removing pairs that are out of bounds on the chess board. Also removes pairs that have already been expanded (Closed List).
	 * @param pairs
	 */
	private static void removeImpossiblePairs (ArrayList <Point> pairs, ArrayList <Point> closedList){
		
		//Determine if any pairs are NOT possible and remove them
		int count = 0;
		boolean allPossiblePairs = false;
		while (!allPossiblePairs){
			
			Point p = pairs.get(count);
			
			if (p.x < 0 || p.y < 0 || p.x > 7 || p.y > 7)
				pairs.remove(p);
			else if (closedList.contains(p) == true)
				pairs.remove(p);
			else
				count++;
			
			if(count == pairs.size())
				allPossiblePairs = true;
		}
	}//end removeImpossiblePairs

}// end class
