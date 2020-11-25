package code.generic;

import code.mission.MissionImpossibleNode;
import code.mission.MissionImpossibleState;

public abstract class Node {
	public MissionImpossibleState state;
	public MissionImpossibleNode parentNode;
	public String operator;
	public int depth;
	public int pathCost;
	public int heuristic;
	public int costAndHeuristic;
	
	public abstract String toString() ;

	public abstract boolean equals(MissionImpossibleNode n1,MissionImpossibleNode n2);



	
	

}
