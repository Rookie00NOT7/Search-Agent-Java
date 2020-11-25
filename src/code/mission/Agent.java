package code.mission;

public class Agent {
	Position position;
	int healthLevel;
	
	public Agent(Position pos, int health) {
		this.position = pos;
		this.healthLevel = health;
		
		// TODO Auto-generated constructor stub
	}

	public Position getPos() {
		return position;
	}

	public void setPos(Position pos) {
		this.position = pos;
	}
	
	

	public int getHealthLevel() {
		return healthLevel;
	}

	public void setHealthLevel(int healthLevel) {
		this.healthLevel = healthLevel;
	}

	@Override
	public String toString() {
		return "Agent [pos=" + position.toString() + ", healthLevel=" + healthLevel + "]";
	}
	
	public boolean equals(Agent a) {
		if(this.position.equals(a.getPos()) && this.healthLevel == a.healthLevel ) 
			return true;
		return false;
	}
	
	
	

}
