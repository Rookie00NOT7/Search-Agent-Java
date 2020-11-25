package code.mission;

import java.util.Arrays;

import code.generic.State;

public class MissionImpossibleState extends State{

	public MissionImpossibleState() {
		// TODO Auto-generated constructor stub
	}

	public MissionImpossibleState(Position pos , int i) {
		// TODO Auto-generated constructor stub
		this.position = pos;
		this.agentsLeftSoFar = i;
	}
	public boolean isSubmarine() {
		return isSubmarine;
	}

	public void setSubmarine(boolean isSubmarine) {
		this.isSubmarine = isSubmarine;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public boolean isHasAgent() {
		return hasAgent;
	}

	public void setHasAgent(boolean hasAgent) {
		this.hasAgent = hasAgent;
	}

	public int getAgentsLeftSoFar() {
		return agentsLeftSoFar;
	}

	public void setAgentsLeftSoFar(int agentsLeftSoFar) {
		this.agentsLeftSoFar = agentsLeftSoFar;
	}
	
	
	public boolean equals(MissionImpossibleState s1,MissionImpossibleState s2) {
		if(s1.position.equals(s2.position) && s1.hasAgent == s2.hasAgent && s1.isSubmarine == s2.isSubmarine && s1.agentsLeftSoFar == s2.agentsLeftSoFar) {
			return s1.agents.equals(s2.agents);
		}
		return false;
	}

	@Override
	public String toString() {
		String res = "State [position=" + position.toString() + ", isSubmarine=" + isSubmarine + ", hasAgent=" + hasAgent 
				+", agentsLeftSoFar=" + agentsLeftSoFar + ", agentsAvailable=" + Arrays.deepToString(agents) + "]";
		
//		if(this.agent!=null) res = res+ ", agent=" + agent.toString();
		
		
		return  res ;
	}

	public Agent[] getAgents() {
		return agents;
	}

	public void setAgents(Agent[] agents) {
		this.agents = agents.clone();
	}

}
