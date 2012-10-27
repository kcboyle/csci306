/** Nicola Hetrick
 * Kira Combs
 * 10/26/12
 */
package main;

import java.util.ArrayList;
import java.util.Set;

public class ComputerPlayer extends Player {
	private char lastRoomVisited;	//for use with GUI
	
	public ComputerPlayer() {
	}
	public BoardCell pickLocation(Set<BoardCell> targets) {
		BoardCell locCell = new WalkwayCell();				//chooses walkway as default
		return locCell;										//returns the locCell
		//picks a location from a list of targets
	}
	public ArrayList<String> createSuggestion(String person, String room, String weapon) {
		ArrayList<String> a = new ArrayList<String>();
		return a;
		//for use with GUI
	}
	public void updateSeen(Card seen) {
		//for use with GUI
	}
	//for use with GUI
	public char getLastRoomVisited() {
		return lastRoomVisited;
	}
	//for use with GUI
	public void setLastRoomVisited(char lastRoomVisited) {
		this.lastRoomVisited = lastRoomVisited;
	}
	

}
