package code.generic;

import code.mission.Agent;
import code.mission.MissionImpossibleState;
import code.mission.Position;

public abstract class State {
	public Position position;
	public boolean isSubmarine;
	public boolean hasAgent;
	public int agentsLeftSoFar;
	public Agent[] agents;
	
	public abstract boolean equals(MissionImpossibleState s1,MissionImpossibleState s2);

	@Override
	public abstract String toString();
	
	

	

	

}
