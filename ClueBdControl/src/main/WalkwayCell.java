package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Map;

public class WalkwayCell extends BoardCell{
	
	public WalkwayCell() {
		super();
		cellType = 'W';
	}
	public void draw(Graphics g, int row, int col, Map<Character, String> rooms) {
		Graphics2D walkway = (Graphics2D) g;
		//draws a walkway cell
		Color color = new Color(145, 213, 161);
		walkway.setColor(color);
		walkway.fillRect(row*size, col*size, size, size);
		walkway.setColor(Color.BLACK);
		walkway.drawRect(row*size, col*size, size, size);
	}
	
	public boolean isWalkway() {
		return true;
	}
	
	public boolean isDoorway() {
		return false;
	}
	
	public void draw() {}
}
