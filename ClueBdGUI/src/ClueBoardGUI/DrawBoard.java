package ClueBoardGUI;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;

import Board.Board;
import Board.RoomCell;
import Board.RoomCell.DoorDirection;

public class DrawBoard extends JPanel {
	private Board board;
	private int x1 = 0;
	private int y1 = 0;
	private int x2, y2;

	public DrawBoard(Board board)
	{
		this.board = board;
	}

	public void paintComponent(Graphics g)
	{	
		super.paintComponent(g);

		int rowcount=0;
		int colcount=0;
		for (int i = 0; i < board.getCells().size(); ++i) {			
			int[] coords = board.calcCoords(i);
			y2 = (coords[0] + 1);	//first value in coords will be the row. +1 for use in drawing logic below
			x2 = (coords[1] + 1);	//second value in coords will be the column. +1 for use in drawing logic below

			if (y1 == 0) {
				if (board.getCells().get(i).isWalkway()) {
					//draws a walkway cell
					g.setColor(Color.YELLOW);
					g.fillRect(x1, y1, x2*25, y2*25);
					g.setColor(Color.BLACK);
					g.drawRect(x1, y1, x2*25, y2*25);

				} else if (board.getCells().get(i).isRoom()) {
					//draw a room cell
					g.setColor(Color.GRAY);
					g.fillRect(x1, y1, x2*25, y2*25);
					//check if room is doorway, then draw appropriate doorway
					if (board.getCells().get(i).isDoorway()) {
						RoomCell room = board.getRoomCellAt(coords[0], coords[1]);
						if (room.getDoorDirection() == DoorDirection.LEFT || room.getDoorDirection() == DoorDirection.RIGHT) {
							//draws a vertical doorway
							g.setColor(Color.RED);
							g.drawLine(x1, y1, x1, y2*25);
						} else if(room.getDoorDirection() == DoorDirection.UP || room.getDoorDirection() == DoorDirection.DOWN) {
							//draws a horizontal doorway
							g.setColor(Color.RED);
							g.drawLine(x1, y1, x2*25, y1);
						}
					}
				}
				x1= x2*25;
				colcount++;
				if (colcount == board.getNumColumns()) {
					y1 = y2*25;
					colcount = 0;
					x1 = 0;
					rowcount++;
				}
			} else {
				if (board.getCells().get(i).isWalkway()) {
					//draws a walkway cell
					g.setColor(Color.YELLOW);
					g.fillRect(x1, y1, x2*25, y2*25);
					g.setColor(Color.BLACK);
					g.drawRect(x1, y1, x2*25, y2*25);

				} else if (board.getCells().get(i).isRoom()) {
					//draw a room cell
					g.setColor(Color.GRAY);
					g.fillRect(x1, y1, x2*25, y2*25);
					//check if room is doorway, then draw appropriate doorway
					if (board.getCells().get(i).isDoorway()) {
						RoomCell room = board.getRoomCellAt(coords[0], coords[1]);
						if (room.getDoorDirection() == DoorDirection.LEFT || room.getDoorDirection() == DoorDirection.RIGHT) {
							//draws a vertical doorway
							g.setColor(Color.RED);
							g.drawLine(x1, y1, x1, y2*25);
						} else if(room.getDoorDirection() == DoorDirection.UP || room.getDoorDirection() == DoorDirection.DOWN) {
							//draws a horizontal doorway
							g.setColor(Color.RED);
							g.drawLine(x1, y1, x2*25, y1);
						}
					}
				}
				x1= x2*25; //sets next col index for drawing
				colcount++;
				if (colcount == board.getNumColumns()) {
					y1 = y2*25;			//set the next row index for drawing
					colcount = 0;
					x1 = 0;				//return col index to zero
					rowcount++;
				}
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
