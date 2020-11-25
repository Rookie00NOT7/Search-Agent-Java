package code.generic;

import java.util.ArrayList;

import code.mission.Agent;
import code.mission.MissionImpossibleNode;
import code.mission.MissionImpossibleState;

public abstract class SearchProblem {
	protected ArrayList<String> operators;
	protected static MissionImpossibleNode initialState;
	protected ArrayList<MissionImpossibleState> stateSpace;
	protected MissionImpossibleState goalState;
	
	public abstract boolean statePresent(ArrayList<MissionImpossibleNode> states, MissionImpossibleNode s); 
		
	public abstract MissionImpossibleNode generalSearchProblem (String strategy,int truckSize); 
		
	public abstract boolean goalTest(MissionImpossibleNode node);

	public abstract ArrayList<MissionImpossibleNode> orderedInsert(ArrayList<MissionImpossibleNode> array,MissionImpossibleNode node);
	
	public abstract ArrayList<MissionImpossibleNode> expandNode(MissionImpossibleNode node, int agentsCollectedSoFar,int truckSize,String strategy);
		
	public abstract int findIndexAgentWithMaxHealth(Agent[] agentsArray) ;
	
	public abstract MissionImpossibleNode iterativeSearch(int truckSize) ;

	public abstract MissionImpossibleNode depthSearch(int truckSize) ;
	
	public abstract MissionImpossibleNode breadthFirst(int truckSize) ;
	
	public abstract MissionImpossibleNode uniformCostSearch(int truckSize) ;
	
	public abstract MissionImpossibleNode greedy1(int truckSize) ;
	
	public abstract MissionImpossibleNode greedy2(int truckSize) ;
	
	public abstract MissionImpossibleNode as1(int truckSize) ;
	
	public abstract MissionImpossibleNode as2(int truckSize) ;
	
	public abstract MissionImpossibleNode bestFirst(int truckSize,String strategy) ;

	
	
}
