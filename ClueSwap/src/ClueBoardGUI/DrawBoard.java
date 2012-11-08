package ClueBoardGUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Map;

import javax.swing.*;

import main.Board;
import main.RoomCell;
import main.RoomCell.DoorDirection;

public class DrawBoard extends JPanel {
	private Board board;
	private int xSize; //block size of the cell
	private int ySize;
	private int x1, y1;
	private int x2, y2;
	private int xTileSize, yTileSize;
	private static final int DOOR_THICKNESS = 3;

	public DrawBoard(Board board, int xSize, int ySize)
	{
		this.board = board;
		this.xSize = xSize;
		this.ySize = ySize;
		xTileSize = (xSize/board.getNumColumns());
		yTileSize = (ySize/board.getNumRows());
	}

	public void paintComponent(Graphics g)
	{	
		Map<Character, String> rooms = board.getRooms();
		//System.out.println("rows " + board.getNumRows() + " columns " + board.getNumColumns() + " xSize " + xSize + " ySize " + ySize + " xTileSize " + xTileSize + " yTileSize " + yTileSize);
		super.paintComponent(g);

		int rowcount=0;
		int colcount=0;

		x1 = 0;
		y1 = 0;

		//drawing the board: walkways, room, closet, and labeling rooms
		for (int i = 0; i < board.getCells().size(); ++i) {			
			int[] coords = board.calcCoords(i);
			y2 = (coords[0] + 1);	//first value in coords will be the row. +1 for use in drawing logic below
			x2 = (coords[1] + 1);	//second value in coords will be the column. +1 for use in drawing logic below

			if (board.getCells().get(i).isWalkway()) {
				//draws a walkway cell
				g.setColor(Color.YELLOW);
				g.fillRect(x1, y1, x2*xTileSize, y2*yTileSize);
				g.setColor(Color.BLACK);
				g.drawRect(x1, y1, x2*xTileSize, y2*yTileSize);

			} else if (board.getCells().get(i).isRoom()) {
				if (board.getRoomCellAt(coords[0], coords[1]).getInitial() == 'X') {
					//coloring the closet cell
					g.setColor(Color.RED);
					g.fillRect(x1, y1, x2*xTileSize, y2*yTileSize);
				} else {
					//draw a room cell
					g.setColor(Color.GRAY);
					g.fillRect(x1, y1, x2*xTileSize, y2*yTileSize);
					//check if room is doorway, then draw appropriate doorway
					if (board.getCells().get(i).isDoorway()) {
						RoomCell room = board.getRoomCellAt(coords[0], coords[1]);
						if (room.getDoorDirection() == DoorDirection.LEFT)  {
							//draws a vertical doorway to left
							g.setColor(Color.RED);
							g.fillRect(x1, y1, DOOR_THICKNESS, yTileSize);
							//g.drawLine(x1, y1, x1, y2*size);
						}  else if (room.getDoorDirection() == DoorDirection.RIGHT) {
							//draws a vertical doorway to the right
							g.setColor(Color.RED);
							g.fillRect(x1+xTileSize-2, y1, DOOR_THICKNESS, yTileSize);
						} else if(room.getDoorDirection() == DoorDirection.UP){
							//draws a horizontal doorway for the upward entry
							g.setColor(Color.RED);
							g.fillRect(x1, y1, xTileSize, DOOR_THICKNESS);
						} else if(room.getDoorDirection() == DoorDirection.DOWN) {
							//draws a horizontal doorway for downward entry
							g.setColor(Color.RED);
							g.fillRect(x1, y1+yTileSize-2, xTileSize, DOOR_THICKNESS);
						}
					}
				}
				if (board.getRoomCellAt(coords[0], coords[1]).isName()) {
					g.setColor(Color.PINK);
					char key = board.getRoomCellAt(coords[0], coords[1]).getInitial();
					g.setFont(new Font("Chiller", Font.BOLD, 20));
					g.drawString(rooms.get(key), x1, y1);
					
				}
			}
			x1= x2*xTileSize;
			colcount++;
			if (colcount == board.getNumColumns()) {
				y1 = y2*yTileSize;
				colcount = 0;
				x1 = 0;
				rowcount++;
			}			
		}



		/**draws a player
	  g.setColorColor.MAGENTA);
	  g.fillOval(x, y, 25, 25);
	  g.setColor( Color.BLACK);
	  g.drawOval(x, y, 25, 25);*/

	}
	public void translate(int dx, int dy)
	{
		x1 += dx;
		y1 += dy;
		// Must include this to see changes
		repaint();
	}

}
