package main;

public class RoomCell extends BoardCell {
	private DoorDirection doorDirection;
	
	public RoomCell(String cellType) {
		super();
		this.cellType = cellType.charAt(0);
		if( cellType.length() > 1 ) {
			switch(cellType.charAt(1)) {
			case 'U': this.doorDirection = DoorDirection.UP;
				break;
			case 'D': this.doorDirection = DoorDirection.DOWN;
				break;
			case 'L': this.doorDirection = DoorDirection.LEFT;
				break;
			case 'R': this.doorDirection = DoorDirection.RIGHT;
				break;			
			}
		} else {
			this.doorDirection = DoorDirection.NONE;
		}

	}
	
	public boolean isRoom() {
		return true;
	}
	
	public boolean isDoorway() {
		if( doorDirection == DoorDirection.NONE ) return false;
		else return true;
	}

	public enum DoorDirection {
		UP('U'), DOWN('D'), LEFT('L'), RIGHT('R'), NONE(' ');
		char symbol;
		DoorDirection(char symbol) {
			this.symbol = symbol;
		}
	}
	
	//Draw the gui
	public void draw() {
		
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public char getInitial() {
		return cellType;
	}
}

