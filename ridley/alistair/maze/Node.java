package ridley.alistair.maze;

import java.util.HashSet;
import java.util.Set;

public class Node {
	private Set<Node> connectedNodes = new HashSet<Node>();
	private boolean visited; 
	private int distance;
	private double distToGoal; 
	public int x; 
	public int y;
	public Node cameFrom; 
	
	public Node(int x, int y){
		this.x = x; 
		this.y = y;
		visited = false;
		distance = Integer.MAX_VALUE; 
	}
	
	public void setDistToGoal(double distToGoal){
		this.distToGoal = distToGoal;
	}
	
	public void addNode(Node n){ 
		if(connectedNodes.add(n)){
			n.addNode(this);
		}
	}
	
	public void setVisited(boolean visited){
		this.visited = visited; 
	}
	
	public boolean getVisited(){
		return visited;
	}

	public void setTentativeDistance(int d, Node n){
		if(d < this.distance){
			this.distance = d;
			cameFrom = n;
		}
	}
	
	public int getTentativeDistance(){
		return distance; 
	}
	
	public double getCost(){
		return distance + distToGoal + ((Math.random()-0.5)/1000.0d); 
	}
	
	public Set<Node> getConnected(){
		return connectedNodes;
	}
}
