/** Nicola Hetrick
 * Kira Combs
 * 10/26/12
 */
package clueTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import junit.framework.Assert;

import main.Board;
import main.BoardCell;
import main.Card;
import main.Card.CardType;
import main.ComputerPlayer;

import org.junit.BeforeClass;
import org.junit.Test;

public class GameActionTests {
	private static Board board;
	ComputerPlayer player;
	
	@BeforeClass
	public void setup() {
		board = new Board("roomLegend.txt", "craigAndLarsConfig.txt", "players.csv", "cards.csv");
		player = new ComputerPlayer();
		//randomly generated cards
		Card card1 = new Card("Gandalf", CardType.PERSON);
		Card card2 = new Card("Daniel Jackson", CardType.PERSON);
		Card card3 = new Card("Library", CardType.ROOM);
		Card card4 = new Card("Dalek", CardType.WEAPON);
		//add all the cards to playerCards
		player.getCards().add(card1);
		player.getCards().add(card2);
		player.getCards().add(card3);
		player.getCards().add(card4);
		ArrayList<String> a = new ArrayList<String>();			//temp answer array
		a.add(card1.getName());
		a.add("Kitchen");
		a.add("Reaver");
		board.setAnswers(a);									//sets answers as the above arrayList
	}
	
	@Test
	public void accusationTest() {
		//all three elements in Answers MUST equal the three elements in Accusations in order to be true
		if (board.getAccusations().get(0).equals(board.getAnswers().get(0)) && board.getAccusations().get(1).equals(board.getAnswers().get(1)) && board.getAccusations().get(2).equals(board.getAnswers().get(2))) {
			Assert.assertTrue(board.isWon());
		} else if (!(board.getAccusations().get(0).equals(board.getAnswers().get(0)) && board.getAccusations().get(1).equals(board.getAnswers().get(1)) && board.getAccusations().get(2).equals(board.getAnswers().get(2)))) {
			Assert.assertFalse(board.isWon());					//else the accusation will always be false (even if only 1 element does not match)
		}
	}
	
	@Test
	public void testTargetRandomSelection() {
		//tests a random location
		// Pick a location with no rooms in target, just three targets
		board.calcTargets(3, 2);		//calcs the targets from index 3 with 2 steps
		int loc_2_3Tot = 0;
		int loc_1_4Tot = 0;
		// Run the test 100 times
		for (int i=0; i<100; i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(board.calcIndex(2,3)))
				loc_2_3Tot++;
			else if (selected == board.getCellAt(board.calcIndex(1,4)))
				loc_1_4Tot++;
			else
				fail("Invalid target selected");
		}
		// Ensure we have 100 total selections (fail should also ensure)
		assertEquals(100, loc_2_3Tot + loc_1_4Tot);
		// Ensure each target was selected more than once
		assertTrue(loc_2_3Tot > 10);
		assertTrue(loc_1_4Tot > 10);	

		//test ensures that the room is always selected if it isn't the last visited
		// Pick a location with a room in target, just three targets
		board.calcTargets(board.calcIndex(14, 11), 4);		//calcs the targets from point (14,11) with 4 steps
		// Run the test 50 times to simulate randomness
		for (int i=0; i<50; i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			//this test should never fail if the room is always being chosen
			Assert.assertTrue(selected == board.getCellAt(board.calcIndex(14,13)));			
		}
		
		//test ensures that if the room was the last visited, a random choice is made
		//pick a room cell 
		board.calcTargets(board.calcIndex(8, 12), 3);					//random point with door to Lounge (O) three steps away
		player.setLastRoomVisited('O');									//simulate that computer has already visited this room
		// Run the test 100 times to simulate randomness
		for (int i=0; i<100; i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			//this test should never fail if because a room should never be chosen as it was last visited
			Assert.assertFalse(selected == board.getCellAt(board.calcIndex(6,13)));			
		}
	}
	
	@Test
	public void disproveSuggestionTest() {
		
	}

}
