package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Set;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import Board.Board;
import Board.BoardCell;
import Board.ComputerPlayer;
import Board.HumanPlayer;
import Board.Player;
import Board.Solution;
import Board.Card;

public class GameActionsTest {
	//declaring objects
	public static Board board;
	public static ComputerPlayer comp;
	public static Card card;
	public static Player player;

	public static Card mustardCard;
	public static Card scarlettCard;
	public static Card knifeCard;
	public static Card wrenchCard;
	public static Card revolverCard;
	public static Card candlestickCard;
	public static Card greenCard;
	public static Card aldersonCard;
	public static Card pipeCard;
	public static Card strattonCard;
	public static Card chauvenetCard;
	public static Solution solution;

	//do this before anything else!
	@BeforeClass
	public static void setUp(){
		//creating new  Board object
		board = new Board();
		//possible solution
		solution = new Solution("Professor Plum", "Berthoud", "Candlestick");

		mustardCard = new Card("Colonel Mustard", Card.CardType.PERSON);
		scarlettCard = new Card("Miss Scarlett", Card.CardType.PERSON);
		knifeCard = new Card("Knife", Card.CardType.WEAPON);
		wrenchCard = new Card("Wrench", Card.CardType.WEAPON);
		greenCard = new Card("Green Center", Card.CardType.ROOM);
		aldersonCard = new Card("Alderson", Card.CardType.ROOM);
		revolverCard = new Card("Revolver", Card.CardType.WEAPON);
		candlestickCard = new Card("Candlestick", Card.CardType.WEAPON);
		pipeCard = new Card("Pipe", Card.CardType.WEAPON);
		strattonCard = new Card("Stratton", Card.CardType.ROOM);
		chauvenetCard = new Card("Chauvenet", Card.CardType.ROOM);
	}

	//test making an accusation
	@Test
	public void testMakeAnAccusation() {
		board.setSolution(solution);

		//tests correct accusation
		Assert.assertTrue(board.checkAccusation("Professor Plum", "Berthoud", "Candlestick"));
		//Case1:tests incorrect name accusation
		Assert.assertFalse(board.checkAccusation("Miss Scarlett", "Berthoud", "Candlestick"));
		//Case2:tests incorrect room accusation
		Assert.assertFalse(board.checkAccusation("Professor Plum", "Library", "Candlestick"));
		//Case3:tests incorrect weapon accusation
		Assert.assertFalse(board.checkAccusation("Professor Plum", "Berthoud", "Dagger"));
		//Case4:tests if all three are wrong
		Assert.assertFalse(board.checkAccusation("Miss Scarlett", "Guggenheim", "Lead Pipe"));
	}

	//test selecting a target location
	@Test
	public void testTargetRoomPreference() {
		//if room is last visited, random choice is made
		ComputerPlayer compPlayer = new ComputerPlayer();
		//choose spot on board where room is a target
		board.calcTargets(board.calcIndex(0,6), 2);
		//set this room to already visited
		compPlayer.setLastRoomVisited('G');
		//run test 100 times
		int good=0;
		for(int i=0; i<100; i++) {
			BoardCell choice = compPlayer.pickLocation(board.getTargets());
			if (choice.isWalkway() && !choice.isRoom())
				good++;
		}

		Assert.assertEquals(100, good);

		//ensures that room is always selected if it isn't last visited
		//choose spot on board where 2 rooms are a target
		board.calcTargets(board.calcIndex(19,5), 5);
		//set this last visited room to something different from these rooms
		compPlayer.setLastRoomVisited('C');
		//run test 100 times
		int yes=0;
		for(int i=0; i<100; i++) {
			BoardCell choice = compPlayer.pickLocation(board.getTargets());
			if (choice.isRoom() && !choice.isWalkway())
				yes++;
		}

		Assert.assertEquals(100, yes);
	}

	@Test
	public void testTargetRandomSelection() {
		ComputerPlayer player = new ComputerPlayer();
		// Pick a location with no rooms in target, just three targets
		board.calcTargets(board.calcIndex(13, 0), 2);
		int loc_11_0Tot = 0;
		int loc_13_2Tot = 0;
		int loc_12_1Tot = 0;
		// Run the test 100 times
		for (int i=0; i<100; i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(board.calcIndex(11, 0)))
				loc_11_0Tot++;
			else if (selected == board.getCellAt(board.calcIndex(13, 2)))
				loc_13_2Tot++;
			else if (selected == board.getCellAt(board.calcIndex(12, 1)))
				loc_12_1Tot++;
			else
				fail("Invalid target selected");
		}
		// Ensure we have 100 total selections (fail should also ensure)
		assertEquals(100, loc_11_0Tot + loc_13_2Tot + loc_12_1Tot);
		// Ensure each target was selected more than once
		assertTrue(loc_11_0Tot > 10);
		assertTrue(loc_13_2Tot > 10);
		assertTrue(loc_12_1Tot > 10);							
	}

	//test disproving a suggestions
	@Test
	public void testDisproveSuggestion(){
		///if player has suggested card, that card is returned
		//if player has multiple cards that match, card to be show is randomly chosen
		//once player shows card, no other players queried
		//querying players will happen from random starting point
		//if no players have relevant cards, null is returned

		//create player with 6 cards
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(mustardCard);
		cards.add(scarlettCard);
		cards.add(knifeCard);
		cards.add(wrenchCard);
		cards.add(greenCard);
		cards.add(aldersonCard);
		Player testPlayer = new Player();
		testPlayer.setCards(cards);

		//Test for one player, one correct match
		//Case 1: correct person returned
		Card test = testPlayer.disproveSuggestion("Colonel Mustard", "Dagger", "Marquez");
		Assert.assertEquals("Colonel Mustard", test.getName());
		//Case 2: correct weapon returned
		test = testPlayer.disproveSuggestion("Professor Plum", "Knife", "Marquez");
		Assert.assertEquals("Knife", test.getName());
		//Case 3: correct room returned
		test = testPlayer.disproveSuggestion("Professor Plum", "Dagger", "Alderson");
		Assert.assertEquals("Alderson", test.getName());
		//Case 4: null returned
		test = testPlayer.disproveSuggestion("Professor Plum", "Dagger", "Marquez");
		Assert.assertNull(test);

		//Test for one player, multiple possible matches
		int colonel = 0;
		int wrench = 0;
		int alderson = 0;
		int green = 0;
		for (int i=0; i<25; i++) {
			test = testPlayer.disproveSuggestion("Colonel Mustard", "Wrench", "Alderson");
			if (test.getName().equals("Colonel Mustard"))
				colonel++;
			if (test.getName().equals("Wrench"))
				wrench++;
			if (test.getName().equals("Alderson"))
				alderson++;
			if (test.getName().equals("Green Center"))
				green++;
		}
		assertTrue(colonel > 0);
		assertTrue(wrench > 0);
		assertTrue(alderson > 0);
		assertTrue(green == 0);

		//Test that all players are queried
		//create 3 players
		ArrayList<Card> pcards1 = new ArrayList<Card>();
		pcards1.add(scarlettCard);
		pcards1.add(knifeCard);
		pcards1.add(wrenchCard);
		pcards1.add(greenCard);
		Player p1 = new Player();
		p1.setCards(pcards1);

		ArrayList<Card> pcards2 = new ArrayList<Card>();
		pcards2.add(mustardCard);
		pcards2.add(revolverCard);
		pcards2.add(candlestickCard);
		pcards2.add(aldersonCard);
		Player p2 = new Player();
		p2.setCards(pcards2);

		ArrayList<Card> pcards3 = new ArrayList<Card>();
		pcards3.add(strattonCard);
		pcards3.add(pipeCard);
		pcards3.add(chauvenetCard);
		Player p3 = new HumanPlayer();
		p3.setCards(pcards3);

		Player currentPlayer = new Player();

		ArrayList<Player> players = new ArrayList<Player>();
		players.add(p1);
		players.add(p2);
		players.add(p3);

		//suggestion that no players can disprove (null returned)
		int bad = 0;
		for (int i=0; i<players.size(); i++) {
			Card test1 = players.get(i).disproveSuggestion("Mrs. Peacock", "Rope", "Marquez");
			if (test1 != null)
				bad++;
		}
		Assert.assertEquals(0, bad);

		//suggestion only the human could disprove (correct Card returned)
		int humangift = 0;
		for( int i = 0; i < players.size(); i++) {
			Card test2  = players.get(i).disproveSuggestion("Mrs.Peacock", "Pipe", "Marquez");
			if(test2 != null && test2.getName().equals("Pipe")) 
				humangift++;
			else 
				bad++;
		}
		Assert.assertEquals(1, humangift);
		Assert.assertEquals(2, bad);

		//Setting the players
		board.setPlayers(players);

		//multiple people have possible cards, one person returns one card only
		int scarlett = 0;
		int revolver = 0;
		int stratton = 0;
		for(int i=0; i < 100; i++){
			Card test3 = board.handleSuggestion("Miss Scarlett", "Revolver", "Stratton", currentPlayer);
			if(test3 != null && test3.getName().equals("Miss Scarlett"))
				scarlett++;
			if(test3 != null && test3.getName().equals("Revolver"))
				revolver++;
			if(test3 != null && test3.getName().equals("Stratton"))
				stratton++;
		}
		Assert.assertTrue(scarlett > 1);
		Assert.assertTrue(revolver > 1);
		Assert.assertTrue(stratton > 1);

		Assert.assertEquals(100, scarlett+revolver+stratton);

		//making sure that if it a players a turn, they shouldn't disprove their own accusation
		//computer player
		int personal1 = 0;
		for(int i=0; i < players.size(); i++) {
			Card test4 = board.handleSuggestion("Reverend Green", "Knife", "Library", p1);
			if(test4 != null)
				personal1++;
		}		
		Assert.assertEquals(0, personal1);

		//human player
		int personal2 = 0;
		for(int i=0; i < players.size(); i++) {
			Card test5 = board.handleSuggestion("Reverend Green", "Pipe", "Library", p3);
			if(test5 != null)
				personal2++;
		}		
		Assert.assertEquals(0, personal2);
	}

	//test computer player making a suggestion
	@Test
	public void testComputerSuggestion(){
		//creating computer player
		ComputerPlayer compPlayer = new ComputerPlayer("Mrs. White", "White", board.calcIndex(0,15));

		//need something for current room player is in
		//making sure that the location Mrs.White picks is a room
		board.calcTargets(board.calcIndex(0,15), 4);
		Set<BoardCell> targets = board.getTargets();

		BoardCell room = compPlayer.pickLocation(targets);

		compPlayer.updateSeen("Colonel Mustard");
		compPlayer.updateSeen("Stratton");
		compPlayer.updateSeen("Miss Scarlett");

		int mustard=0;
		int peacock=0;
		int rope=0;
		int white=0;
		int guggenheim=0;

		Card card = null;
		//have computer randomly choose from cards it hasn't seen to make suggestion
		for (int i=0; i<50; i++) {
			ArrayList<Card> suggestion = compPlayer.createSuggestion(room);
			for (int j=0; j<suggestion.size(); j++) {
				card = suggestion.get(j);
				if (card.getName().equals("Colonel Mustard"))
					mustard++;
				if (card.getName().equals("Mrs. Peacock"))
					peacock++;
				if (card.getName().equals("Rope"))
					rope++;
				if (card.getName().equals("Mrs. White"))
					white++;
				if (!card.getName().equals("Guggenheim"))
					guggenheim++;
			}
		}

		Assert.assertTrue(mustard == 0);
		Assert.assertTrue(peacock > 1);
		Assert.assertTrue(rope > 1);
		Assert.assertTrue(white == 0);
		Assert.assertTrue(guggenheim > 1);

		//try to disprove suggestion, make sure it returns null if it is correct answer
		//deal the cards
		board.deal();

		//array list of all the players
		ArrayList<Player> players =  board.getPlayers();

		//get the solution that was made in deal method
		String person = board.getSolution().getPerson();
		String weapon = board.getSolution().getWeapon();
		String room1 = board.getSolution().getRoom();
		
		int counter = 0;
		//if no one can disprove it you did great
		for (int j=0; j<100; j++) {
			for(int i=0; i < players.size(); i++) {
				Card test6 = players.get(i).disproveSuggestion(person, weapon, room1);
				if(test6 != null)
					counter++;
			}
		}

		Assert.assertEquals(0,counter);

	}

}
