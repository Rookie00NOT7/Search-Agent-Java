package code.mission;

import java.util.Arrays;

import code.generic.Node;

public class MissionImpossibleNode extends Node{

	public MissionImpossibleNode() {
		// TODO Auto-generated constructor stub
	}

	public MissionImpossibleState getState() {
		return state;
	}
	public void setState(MissionImpossibleState state) {
		this.state = state;
	}
	
	
	public Node getParentNode() {
		return parentNode;
	}
	public void setParentNode(MissionImpossibleNode parentNode) {
		this.parentNode = parentNode;
	}
	
	
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	
	public int getDepth() {
		return depth;
	}
	public int getCostAndHeuristic() {
		return costAndHeuristic;
	}
	
	
	public void setCostAndHeuristic(int costAndHeuristic) {
		this.costAndHeuristic = costAndHeuristic;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	
	
	public int getPathCost() {
		return pathCost;
	}
	public void setPathCost(int pathCost) {
		this.pathCost = pathCost;
	}

	public int getHeuristic() {
		return heuristic;
	}


	public void setHeuristic(int heuristic) {
		this.heuristic = heuristic;
	}
	@Override
	public String toString() {
//		return "Node [state=" + state.toString() + ", parentNode=" + parentNode + ", operator=" + operator + ", depth=" + depth
//				+ ", pathCost=" + pathCost + "]"+"\n";
//		return "Node [pathCost=" +pathCost+ ", heuristic= "+heuristic+", costAndHeuristic="+costAndHeuristic+ ", state=["+state.position.toString()+", "+ state.agentsLeftSoFar+"], operator=" +operator +"]";
		return "Node [state=["+state.position.toString()+", "+ state.agentsLeftSoFar+", agents= "+ Arrays.deepToString(state.agents)+"], operator=" +operator +"]";
	}

	public boolean equals(MissionImpossibleNode n11,MissionImpossibleNode n22) {
		if(n11.state.equals(n22.state) &&  n11.depth == n22.depth && n11.pathCost == n22.pathCost && n11.heuristic == n22.heuristic ) 
			if(n11.parentNode!=null){
				if(n11.parentNode.equals(n22.parentNode))
					return true;
			}
			else {
				if(n22.parentNode == null)
					return true;
			}
		
				
		
		return false;
	}


	

	
}
