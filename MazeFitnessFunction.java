package ridley.alistair.maze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

public class MazeFitnessFunction extends FitnessFunction {

	/* This function evaluates the fitness of a chromosome by considering the number of steps required in 
	 * an A* solution of the maze (hence a maze which makes A* performance poor with respect to the heuristic of distance to end of maze is a more fit solution) */ 
	public double evaluate(IChromosome ic){
		
		/* Retrieve the chromosome's corresponding maze */ 
		boolean[][][] maze = Maze.get3dBool(ic); 
		
		
		/* Create a node for each square on the maze */ 
		Node[][] dists = new Node[Maze.MAZE_WIDTH][Maze.MAZE_HEIGHT];
		for(int i = 0; i < dists.length; i++){
			for(int j = 0; j < dists[0].length; j++){
				dists[i][j] = new Node(i,j);
			}
		}
		
		/* Standard A* Implementation */ 
		dists[0][0].setTentativeDistance(0, null);
		dists[0][0].setVisited(true); 
		Node current = dists[0][0];
		List<Node> closedSet = new ArrayList<Node>(); 
		List<Node> openSet = new ArrayList<Node>(); 
		openSet.add(dists[0][0]);
		int numConnections = 0;
		for(int i = 0; i < Maze.MAZE_WIDTH; i++){
			for(int j = 0; j < Maze.MAZE_HEIGHT; j++){
				dists[i][j].setDistToGoal(Math.sqrt(Math.pow((Maze.MAZE_WIDTH - 1) - i,2) + Math.pow((Maze.MAZE_HEIGHT - 1) - j, 2))); //Distance based on euclidean distance
				if(maze[i][j][0]||(j>0&&maze[i][j-1][2])){
					dists[i][j].addNode(dists[i][j-1]);
					numConnections++;
				}
				if(maze[i][j][1]||(i<(Maze.MAZE_WIDTH-1)&& maze[i+1][j][3])){
					dists[i][j].addNode(dists[i+1][j]);
					numConnections++;
				}
			}
		}
		
		int numIterations = 0;
		while(openSet.size()>0){
			current = openSet.remove(min(openSet));
			numIterations++;
			if(current == dists[Maze.MAZE_WIDTH-1][Maze.MAZE_HEIGHT-1]){
				/* A solved maze has fitness which is given by numIterations^2 / (100*numConnections), hence meaning larger iterations increases fitness as does fewer connections (A denser maze) */ 
				return (Math.pow(numIterations,2) / ((double) numConnections))* 100; 
			}
			
			openSet.remove(current);
			closedSet.add(current);
					
			for(Node n: current.getConnected()){
				if(closedSet.contains(n)){
					continue; 
				}
				
				n.setTentativeDistance(current.getTentativeDistance() + 1, current);
				
				if(!openSet.contains(n)){
					openSet.add(n);
				}
			}
		}
		/* If the maze cannot be solved fitness is based on increased ability to traverse the maze (hence selecting for solutions more likely to be solvable) */ 
		return numConnections; 

	}
	
	/* Intermediate function to calculate the minimum value of a list of nodes */
	public int min(List<Node> l){
		int min = 0; 
		double minValue = l.get(0).getCost(); 
		/* Iterate through each node to see if it has a lower value than previously seen */
		for(int i = 1; i < l.size(); i++){ 
			if(l.get(i).getCost()<minValue){
				min = i;
				minValue = l.get(i).getCost();
			}
		}
		return min;
	}
	
}
