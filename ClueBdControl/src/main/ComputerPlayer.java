/** Nicola Hetrick
 * Kira Combs
 * 10/26/12
 */
package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Set;

import ClueBoardGUI.ClueBoardGUI;

import main.Card.CardType;

public class ComputerPlayer extends Player {
	private ArrayList<String> cardsSeen;
	
	public ComputerPlayer() {
		ArrayList<String> seen = new ArrayList<String>();
		setCardsSeen(seen);
		setLastRoomVisited('X');
	}
	
	public BoardCell pickLocation(Set<BoardCell> targets) {
		//if it's room, you must enter unless previously entered room otherwise pick random room
		ArrayList<BoardCell> nonRooms = new ArrayList<BoardCell>();
		Random rand = new Random();
		for(BoardCell key : targets) {
			//System.out.println("key: " + key);
			if (key.isRoom() && (key.getCellType() != getLastRoomVisited())) {
				return key;
			} else {
				if (key.isRoom() == false)
					nonRooms.add(key);
			}
		}
		int randInt = rand.nextInt(nonRooms.size());
		return nonRooms.get(randInt);
	}
	public void makeMove(Set<BoardCell> targets) {
		BoardCell loc = pickLocation(targets);
		setCol(loc.getCol()); 
		setRow(loc.getRow());
	}
	
	public ArrayList<String> createSuggestion(String room) {
		//bools to check if weapon and person card is in suggestion
		boolean hasPersonCard = false;
		boolean hasWeaponCard = false;
		String weapon = " ";
		String person = " ";
		
		
		ArrayList<String> suggestion = new ArrayList<String>();
		suggestion.add(room);
		ArrayList<Card> cards = ClueBoardGUI.getBoard().getAllCards();
		Collections.shuffle(cards);
		for (int i = 0; i < cards.size(); i++) {
			if (hasPersonCard == false && cards.get(i).getCardType() == CardType.PERSON && !cardsSeen.contains(cards.get(i).getName())) {
				person = cards.get(i).getName();
				hasPersonCard = true;
			}
			if (hasWeaponCard == false && cards.get(i).getCardType() == CardType.WEAPON && !cardsSeen.contains(cards.get(i).getName())) {
				weapon = cards.get(i).getName();
				hasWeaponCard = true;
			}
		}
		suggestion.add(weapon);
		suggestion.add(person);
		return suggestion;
	}
	public void updateSeen(String seen) {
		cardsSeen.add(seen);
	}

	public ArrayList<String> getCardsSeen() {
		return cardsSeen;
	}
	public void setCardsSeen(ArrayList<String> cardsSeen) {
		this.cardsSeen = cardsSeen;
	}
	

}
