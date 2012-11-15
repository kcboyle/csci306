package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Map;

public class RoomCell extends BoardCell {
	private DoorDirection doorDirection;
	private boolean isName = false;
	final static public int DOOR_THICKNESS = 3;			//thickness of door rectangle
	
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
			case 'N': isName = true;
				break;
			}
		} else {
			this.doorDirection = DoorDirection.NONE;
		}

	}
	
	public boolean isName() {
		return isName;
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
	
	//Draw the room cell
	public void draw(Graphics g, int row, int col, Map<Character, String> rooms) {
		Graphics2D room = (Graphics2D) g;
		//color the closet differently 
		if (getInitial() == 'X') {
			Color roomColor = new Color(193, 46, 54); 
			room.setColor(roomColor);
			room.fillRect(row*size, col*size, size, size);
			
		} else {			//draw a normal room cell
			room.setColor(Color.GRAY);
			room.fillRect(row*size, col*size, size, size);
		}
		//draw doorways
		if (doorDirection == DoorDirection.LEFT)  {
			//draws a vertical doorway to left
			room.setColor(Color.WHITE);
			room.fillRect(row*size, col*size, DOOR_THICKNESS, size);
			//g.drawLine(x1, y1, x1, y2*size);
		}  else if (doorDirection == DoorDirection.RIGHT) {
			//draws a vertical doorway to the right
			room.setColor(Color.WHITE);
			room.fillRect(row*size + size-2, col*size, DOOR_THICKNESS, size);
		} else if(doorDirection == DoorDirection.UP){
			//draws a horizontal doorway for the upward entry
			room.setColor(Color.WHITE);
			room.fillRect(row*size, col*size, size, DOOR_THICKNESS);
		} else if(doorDirection == DoorDirection.DOWN) {
			//draws a horizontal doorway for downward entry
			room.setColor(Color.WHITE);
			room.fillRect(row*size, col*size +size-2, size, DOOR_THICKNESS);
		}	
		//write the room name in the appropriate room
		if (isName()) {
			room.setColor(Color.ORANGE);
			char key = getInitial();
			room.setFont(new Font("Chiller", Font.BOLD, 18));
			room.drawString(rooms.get(key), row*size, col*size);
			
		}
	
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public char getInitial() {
		return cellType;
	}
}

