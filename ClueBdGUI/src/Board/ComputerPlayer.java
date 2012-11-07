package Board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import Board.Card.CardType;

public class ComputerPlayer extends Player {
	//uml based
	private char lastRoomVisited;
	ArrayList<String> seen = new ArrayList<String>();

	public ComputerPlayer() {
		super();
	}

	public ComputerPlayer(String name, String color, int initpos) {
		this.name = name;
		this.color = color;
		this.initpos = initpos;
		seen.add(name);
	}

	public BoardCell pickLocation(Set<BoardCell> targets){
		List<BoardCell> list = new ArrayList<BoardCell>(targets);

		for (BoardCell spot: targets) {
			//if you can go into that room and you haven't been in that room you must go in
			if (spot.isDoorway() && ((RoomCell) spot).getInitial() != lastRoomVisited) {
				return spot;
				//if you have visited you don't want to go in
			} else if (spot.isDoorway() && ((RoomCell) spot).getInitial() == lastRoomVisited) {
				//grab the spot of the room
				int i= list.indexOf(spot);
				list.remove(i);
				Collections.shuffle(list);
				//return the first random spot , must be walkway
				return list.get(0);
			}
		}
		//assuming that a room is not an option, just shuffle the list and pick the first one
		Collections.shuffle(list);
		return list.get(0);
	}

	public ArrayList<Card> createSuggestion(BoardCell roomsugg){
		//make a suggestion based on what is seen
		//creating a new board
		Board board = new Board();

		//Array list of cards that can be returned
		ArrayList<Card> suggcards = new ArrayList<Card>();
		ArrayList<Card> suggcomp = new ArrayList<Card>();

		//the computer suggestion has no choice but to choose the room it is currently in
		if (roomsugg.isRoom()) {
			String cardname = board.findMapValue(((RoomCell) roomsugg).getInitial());
			Card card = new Card(cardname, CardType.ROOM);
			suggcomp.add(card);
		}

		//go through all cards in deck
		for (int i=0; i<board.getDeck().size(); i++) {
			Card eachCard = board.getDeck().get(i);
			//if seen array does not contain a card, add it to possible suggested cards
			if (!seen.contains(eachCard.getName())) {
				if (eachCard.getCardtype() == CardType.PERSON) {
					suggcards.add(eachCard);
				}
				if (eachCard.getCardtype() == CardType.WEAPON) {
					suggcards.add(eachCard);
				}
			}
		}

		Collections.shuffle(suggcards);

		//to make sure it doesn't suggest 2 weapons or 2 people
		boolean suggPerson = false;
		boolean suggWeapon = false;
		
		//picking possible person or weapon for the suggestion
		for (int i=0; i < suggcards.size(); i++) {
			if(suggPerson == false && suggcards.get(i).getCardtype() == CardType.PERSON) {
				suggcomp.add(suggcards.get(i));
				suggPerson=true;
			}
			if(suggWeapon == false && suggcards.get(i).getCardtype() == CardType.WEAPON) {
				suggcomp.add(suggcards.get(i));
				suggWeapon=true;
			}
			if(suggcomp.size()==3) {
				break;
			}
		}

		return suggcomp;
	}

	public void updateSeen(String seencard){
		seen.add(seencard);
	}

	public ArrayList<String> getSeen() {
		return seen;
	}

	public char getLastRoomVisited() {
		return lastRoomVisited;
	}

	public void setLastRoomVisited(char rm) {
		lastRoomVisited = rm;
	}

}
