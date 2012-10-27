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
import main.HumanPlayer;
import main.Player;

import org.junit.BeforeClass;
import org.junit.Test;

public class GameActionTests {
	private static Board board;
	ComputerPlayer player;
	Card card1;
	Card card2;
	Card card3;
	Card card4;
	String cardShown;
	
	@BeforeClass
	public void setup() {
		board = new Board("roomLegend.txt", "craigAndLarsConfig.txt", "players.csv", "cards.csv");
		player = new ComputerPlayer();
		//randomly generated cards
		card1 = new Card("Gandalf", CardType.PERSON);
		card2 = new Card("Daniel Jackson", CardType.PERSON);
		card3 = new Card("Library", CardType.ROOM);
		card4 = new Card("Dalek", CardType.WEAPON);		
	}
	
	@Test
	public void accusationTest() {
		//all three elements in Answers MUST equal the three elements in Accusations in order to be true
		if (board.getAccusations().get(0).equals(board.getAnswers().get(0)) && board.getAccusations().get(1).equals(board.getAnswers().get(1)) && board.getAccusations().get(2).equals(board.getAnswers().get(2))) {
			Assert.assertTrue(board.isWon());
		//This else statement should cover all possible card failures!!! (wrong room, wrong weapon, wrong person, wrong all together!! The wonders of AND statements)
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
	public void disproveSuggestion_SinglePlayer_Test() {
		//add all the cards to playerCards
		player.getCards().add(card1);
		player.getCards().add(card2);
		player.getCards().add(card3);
		player.getCards().add(card4);
		ArrayList<String> a = new ArrayList<String>();			//temp answer array
		a.add(card1.getName());
		a.add("Kitchen");
		a.add("Reaver");
		board.setAccusations(a);
		
		//This is a single player with a single match (W, P, or R)
		cardShown = board.disproveSuggestion(board.getAccusations());
		Assert.assertEquals(card1.getName(), cardShown);
		
		//Reset Accusation
		a.clear();
		a.add("Jack O'Neill");
		a.add("Kitchen");
		a.add("Reaver");
		board.setAccusations(a);
		
		// This is if it is null... (bad guess)
		cardShown = board.disproveSuggestion(board.getAccusations());
		Assert.assertEquals(null, cardShown);
		
		//Reset Accusation
		a.clear();
		a.add(card2.getName());
		a.add(card3.getName());
		a.add(card4.getName());
		board.setAccusations(a);
		
		//This is a single player with multiple matches
		int str_1 = 0;
		int str_2 = 0;
		int str_3 = 0;
		// Run the test 100 times
		for (int i=0; i<100; i++) {
			cardShown = board.disproveSuggestion(board.getAccusations());
			Assert.assertTrue(a.contains(cardShown));			//makes sure that an irrelevant card is not shown, ie card shown is in the accusation
			if (cardShown.equals(player.getCards().get(1).getName()))
				str_1++;
			else if (cardShown.equals(player.getCards().get(2).getName()))
				str_2++;
			else if (cardShown.equals(player.getCards().get(3).getName()))
				str_3++;
			else
				fail("Invalid target selected");
		}
		// Ensure we have 100 total selections (fail should also ensure)
		assertEquals(100, str_1 + str_2 + str_3);
		// Ensure each card was selected
		assertTrue(str_1 > 10);
		assertTrue(str_2 > 10);
		assertTrue(str_3 > 10);
	}
	
	public void disproveSuggestion_AllPlayer_Test() {
		ComputerPlayer cp1 = new ComputerPlayer();
		ArrayList<Card> cd = new ArrayList<Card>();
		cd.add(card1);
		cp1.setCards(cd);
		ComputerPlayer cp2 = new ComputerPlayer();
		cd.clear();
		cd.add(card2);
		cp2.setCards(cd);
		ComputerPlayer cp3 = new ComputerPlayer();
		cd.clear();
		cd.add(card3);
		cp3.setCards(cd);
		ArrayList<ComputerPlayer> cp = new ArrayList<ComputerPlayer>();
		cp.add(cp1);
		cp.add(cp2);
		cp.add(cp3);
		HumanPlayer hp1 = new HumanPlayer();
		cd.clear();
		cd.add(card4);
		hp1.setCards(cd);
		board.setCompPlayers(cp);
		
		// ALL PLAYER, no card found
		ArrayList<String> a = new ArrayList<String>();
		a.add("Donna Noble");
		a.add("Conservatory");
		a.add("Mjolnir");
		board.setAccusations(a);
		
		// This is if it is null... (bad guess)
		cardShown = board.disproveSuggestion(board.getAccusations());
		Assert.assertEquals(null, cardShown);

		//ALL PLAYER, only human has THE card
		board.setCurrentPlayer(hp1);				//sets the currentPlayer to the human player
		//Reset Accusation
		a.clear();
		a.add("Donna Noble");
		a.add("Conservatory");
		a.add("Dalek");								//card exists 
		board.setAccusations(a);

		//only player who made accusation can disprove (this case the player is human)
		cardShown = board.disproveSuggestion(board.getAccusations());
		Assert.assertEquals(null, cardShown);
		

		//ALL PLAYER, only computer has THE card
		board.setCurrentPlayer(cp1);				//sets the currentPlayer to the computer player
		//Reset Accusation
		a.clear();
		a.add("Gandalf");							//card exists
		a.add("Conservatory");
		a.add("Mjolnir");
		board.setAccusations(a);

		//only player who made accusation can disprove (this case the player is computer)
		cardShown = board.disproveSuggestion(board.getAccusations());
		Assert.assertEquals(null, cardShown);


		//ALL PLAYER, only human has THE card
		board.setCurrentPlayer(cp1);				//sets the currentPlayer to the computer player
		//Reset Accusation
		a.clear();
		a.add("Gandalf");							//card exists
		a.add("Conservatory");
		a.add("Dalek");								//card exists
		board.setAccusations(a);

		//The player who made the accusation can disprove, but so can human, and the human's card should be returned
		cardShown = board.disproveSuggestion(board.getAccusations());
		Assert.assertEquals("Dalek", cardShown);
		
		//ALL PLAYER, 2 players have cards!
		board.setCurrentPlayer(cp2);				//sets the currentPlayer to the computer player
		//Reset Accusation
		a.clear();
		a.add("Gandalf");							//card exists
		a.add("Conservatory");
		a.add("Dalek");								//card exists
		board.setAccusations(a);

		int player_1 = 0;
		int player_2 = 0;
		// Run the test 100 times
		for (int i=0; i<100; i++) {
			//Count time seen for each card, cause the function need to be RANDOM!!! 
			cardShown = board.disproveSuggestion(board.getAccusations());
			if (cardShown.equals(cp1.getCards().get(0).getName()))
				player_1++;
			else if (cardShown.equals(hp1.getCards().get(0).getName()))
				player_2++;
			else
				fail("Invalid target selected");
		}
		// Ensure we have 100 total selections (fail should also ensure)
		assertEquals(100, player_1 + player_2);
		// Ensure each card was selected
		assertTrue(player_1 > 10);
		assertTrue(player_2 > 10);
	}
	
	@Test
	public void cardsSeenTest() {
		
		// This tests shows cards being updated in the "cardsSeen" ArrayList that each player has.
		ComputerPlayer comp1 = new ComputerPlayer();
		board.setCurrentPlayer(comp1);
		ArrayList<Card> cd = new ArrayList<Card>();
		cd.add(card1);
		cd.add(card2);
		cd.add(card3);
		comp1.setCards(cd);
		comp1.updateSeen(card1);
		comp1.updateSeen(card2);
		comp1.updateSeen(card3);
		ComputerPlayer comp2 = new ComputerPlayer();
		cd.clear();
		cd.add(card4);
		comp2.setCards(cd);
		ArrayList<String> a = new ArrayList<String>();
		a.add("Donna Noble");
		a.add("Conservatory");
		a.add("Dalek");									//card exists
		board.setAccusations(a);
		cardShown =  board.disproveSuggestion(board.getAccusations());
		Assert.assertTrue(comp1.getCardsSeen().contains(cardShown));
	}
	
	@Test
	public void suggestionTest() {
		// Checks that a suggestion must always be made in the player's current room
		//Suggestion has incorrect room
		ComputerPlayer comp3 = new ComputerPlayer();
		board.setCurrentPlayer(comp3);
		ArrayList<Card> cd = new ArrayList<Card>();
		cd.add(card1);
		cd.add(card2);
		cd.add(card3);
		comp3.setCards(cd);
		comp3.updateSeen(card1);
		comp3.updateSeen(card2);
		comp3.updateSeen(card3);
		comp3.setLastRoomVisited('L');
		board.setAccusations(comp3.createSuggestion("Donna Noble", "Conservatory", "Mjolnir"));
		Assert.assertEquals(null, board.getAccusations());										//may be changed later during implementation
		
		//Suggestion has a correct room testing random other values
		board.setAccusations(comp3.createSuggestion("Donna Noble", card3.getName(), "Mjolnir"));
		Assert.assertEquals("Library", board.getAccusations().get(1));
		
		//Suggestion does not include seen card
		board.setAccusations(comp3.createSuggestion(card1.getName(), card3.getName(), "Mjolnir"));
		Assert.assertEquals(null, board.getAccusations());
	}	
}
