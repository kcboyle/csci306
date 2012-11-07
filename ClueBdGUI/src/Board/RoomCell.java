package Board;

public class RoomCell extends BoardCell {
	
	public enum DoorDirection{UP,DOWN,LEFT,RIGHT,NONE;}
	
	DoorDirection doorDirection = DoorDirection.NONE;
	
	public void setDoorDirection(DoorDirection d) {
		this.doorDirection = d;
	}

	char initial;
	
	public char getInitial() {
		return initial;
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public void setInitial(char initial) {
		this.initial = initial;
	}

	public Boolean isRoom(){
		return true;
	}
	
	public Boolean isDoorway(){
		if (doorDirection == DoorDirection.NONE)
			return false;
		else return true;
	}
}
