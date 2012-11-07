package Board;

public class WalkwayCell extends BoardCell {
	
	char initial = 'W';
	
	public void setInitial(char initial) {
		this.initial = initial;
	}
	
	public char getInitial() {
		return initial;
	}

	public Boolean isWalkway(){
		return true;
		}
}
