package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class WalkwayCell extends BoardCell{
	
	public WalkwayCell() {
		super();
		cellType = 'W';
	}
	public void draw(Graphics g, int row, int col) {
		Graphics2D walkway = (Graphics2D) g;
		//draws a walkway cell
		walkway.setColor(Color.PINK);
		walkway.fillRect(row*size, col*size, size, size);
		walkway.setColor(Color.BLACK);
		walkway.drawRect(row*size, col*size, size, size);
		/**float thickness = 1;
		Stroke oldStroke = walkway.getStroke();
		walkway.setStroke(new BasicStroke(thickness));
		walkway.drawRect(row*size, col*size, size, size);
		walkway.setStroke(oldStroke);*/
		
	}
	
	public boolean isWalkway() {
		return true;
	}
	
	public boolean isDoorway() {
		return false;
	}
	
	public void draw() {}
}
