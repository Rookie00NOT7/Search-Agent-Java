package code.mission;

public class Position {
	int x;
	int y;
	
	
	public Position() {
		
	}
	public Position(int x, int y){
		this.x = x;
		this.y = y;
	}
	public boolean equals(Position p) {
		if(this.x == p.x && this.y == p.y)
			return true;
		return false;
	}
	@Override
	public String toString() {
		return "Position [x=" + x + ", y=" + y + "]";
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
}
