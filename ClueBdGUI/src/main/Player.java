/** Nicola Hetrick
 * Kira Combs
 * 10/26/12
 */
package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.lang.reflect.Field;
import java.util.ArrayList;

public abstract class Player {
	private String name;
	private ArrayList<Card> cards = new ArrayList<Card>();
	private String color;
	private int startRow;
	private int startCol;
	private int targetLocation;
	private int currentLocation;
	final static public int size = 30;
	public static final int diameter = 29;
	
	
	//draws a player. used in the gui
	public void draw(Graphics g) {
		Graphics2D player = (Graphics2D) g;
		player.setColor(nonStringPlayerColor(color));
		player.fillOval(startRow*size,  startCol*size, diameter,  diameter);
		player.setColor(Color.BLACK);
		player.drawOval(startRow*size, startCol*size, diameter, diameter);
	}
	
	//converts the color read from the file to a color in the Graphics color library
	private Color nonStringPlayerColor(String color2) {
		Color color;
		try {     
			Field field = Class.forName("java.awt.Color").getField(color2.trim());     
			color = (Color)field.get(null); } 
		catch (Exception e) {  
			color = null; 
		}
		return color;
	}

	public int getTargetLocation() {
		return targetLocation;
	}
	public void setTargetLocation(int targetLocation) {
		this.targetLocation = targetLocation;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public int getStartCol() {
		return startCol;
	}
	public void setStartCol(int startCol) {
		this.startCol = startCol;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Card> getCards() {
		return cards;
	}
	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}
	public void addCards(Card card) {
		cards.add(card);
	}
	public int getCurrentLocation() {
		return currentLocation;
	}
	public void setCurrentLocation(int currentLocation) {
		this.currentLocation = currentLocation;
	}
	
}
