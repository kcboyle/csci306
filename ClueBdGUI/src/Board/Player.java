package Board;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

import Board.Card;

public class Player {
	//uml based
	protected String name;
	protected String color;
	protected int initpos;
	ArrayList<Card> cards;

	public Player() {}

	public Player(String name, String color, int initpos) {
		this.name = name;
		this.color = color;
		this.initpos = initpos;
	}

	public Color convertColor(String strColor) {
		Color color; 
		try {     
			// We can use reflection to convert the string to a color
			java.lang.reflect.Field field = Class.forName("java.awt.Color").getField(strColor.trim());     
			color = (Color)field.get(null); } 
		catch (Exception e) {  
			color = null; // Not defined } 
		}
		return color;
	}

	//getters
	public String getName() {
		return name;
	}

	public String getColor() {
		return color;
	}

	public int getPosition(){
		return initpos;
	}

	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}

	public Card disproveSuggestion(String person, String weapon, String room) {
		//an array list that has the cards you can disprove
		ArrayList<Card> hasCards = new ArrayList<Card>();

		//goes through all of the players cards
		for (int i=0; i<cards.size(); i++) {
			//case 1: the card you have is a person
			if (cards.get(i).getName().equals(person)) {
				hasCards.add(cards.get(i));
				//case 2: the card you have is a weapon
			} else if (cards.get(i).getName().equals(weapon)) {
				hasCards.add(cards.get(i));
				//case 3: the card you have is a room
			} else if (cards.get(i).getName().equals(room)) {
				hasCards.add(cards.get(i));
			} 
		}
		//assuming that you have the cards, shuffle them up and return the first one
		if (hasCards.size() != 0) {
			Collections.shuffle(hasCards);
			return hasCards.get(0);
		} else {
			//you can't disprove 
			return null;	
		}
	}

	public ArrayList<Card> getCards() {
		return cards;
	}

}
