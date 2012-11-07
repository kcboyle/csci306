package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import Board.Board;
import Board.Card.CardType;
import Board.ComputerPlayer;
import Board.HumanPlayer;
import Board.Player;
import Board.Card;
import Board.Solution;

public class GameSetupTests {
	//declaring objects
	public static Board board;
	public static ComputerPlayer mustard;
	public static ComputerPlayer scarlett;
	public static HumanPlayer human;
	public static Card card;
	public static ArrayList<Player> players;
	public static Solution solution;
	
	//do this before anything else!
	@BeforeClass
	public static void setUp(){
		//creating new  Board object
		board = new Board();
		//declaring new ComputerPlayers
		mustard = new ComputerPlayer("Colonel Mustard", "Yellow", board.calcIndex(0,4));
		scarlett = new ComputerPlayer("Miss Scarlett", "Red", board.calcIndex(20, 3));
		//declaring new HumanPlayer object
		human = new HumanPlayer("Mrs. Peacock", "Blue", board.calcIndex(16, 19));
		players = board.getPlayers();
	}
	
	//test loading people from file
	@Test
	public void testLoadFromFile() {
		//Colonel Mustard - computer
		Player mustardTest = board.getPlayers().get(0);
		assertEquals(mustard.getName(), mustardTest.getName());
		assertEquals(mustard.getColor(), mustardTest.getColor());
		assertEquals(board.calcIndex(0,4), mustardTest.getPosition());
		
		//Miss Scarlett - computer
		Player scarlettTest = board.getPlayers().get(5);
		assertEquals(scarlett.getName(), scarlettTest.getName());
		assertEquals(scarlett.getColor(), scarlettTest.getColor());
		assertEquals(board.calcIndex(20,3), scarlettTest.getPosition());
		
		//Mrs. Peacock - testing human player
		Player peacockTest = board.getPlayers().get(3);
		assertEquals(human.getName(), peacockTest.getName());
		assertEquals(human.getColor(), peacockTest.getColor());
		assertEquals(board.calcIndex(16,19), peacockTest.getPosition());
	}

	//test loading the cards from files
	@Test
	public void testLoadCardsFromFile() {
		
		//our deck of cards
		ArrayList<Card> deck = board.getDeck();
		
		int numRooms = 0;
		int numWeapons = 0;
		int numSuspects = 0;
		int totalCards = deck.size();
		
		//correct number of total cards
		Assert.assertEquals(21, totalCards);
		
		//checking for each type of card
		for (int i=0; i<totalCards; i++) {
			CardType cardtype = deck.get(i).getCardtype();
			if (cardtype == CardType.ROOM)
				numRooms++;
			else if (cardtype == CardType.WEAPON)
				numWeapons++;
			else
				numSuspects++;
		}
		
		//correct number of each type of card
		Assert.assertEquals(6, numWeapons);
		Assert.assertEquals(6, numSuspects);
		Assert.assertEquals(9, numRooms);
		
		//testing one of each card
		int daggerCount=0;
		int revGreenCount=0;
		int pipeCount=0;
		int marquezCount=0;
		
		for (int i=0; i<deck.size(); i++) {
			if (deck.get(i).getName().equals("Dagger") && deck.get(i).getCardtype().equals(CardType.WEAPON))
				daggerCount++;
			if (deck.get(i).getName().equals("Reverend Green") && deck.get(i).getCardtype().equals(CardType.PERSON))
				revGreenCount++;
			if (deck.get(i).getName().equals("Pipe") && deck.get(i).getCardtype().equals(CardType.WEAPON))
				pipeCount++;
			if (deck.get(i).getName().equals("Marquez") && deck.get(i).getCardtype().equals(CardType.ROOM))
				marquezCount++;
		}
		Assert.assertEquals(1, daggerCount);
		Assert.assertEquals(1, revGreenCount);
		Assert.assertEquals(1, pipeCount);
		Assert.assertEquals(1, marquezCount);
	}
	
	//testing the deal
	@Test
	public void testDeal(){
		
		board.deal();
		solution = board.getSolution();
		
		int totalCards=0;
		for (int i=0; i<players.size(); i++) {
			totalCards += players.get(i).getCards().size();
		}
			
		//18 cards dealt among players, 3 are the solution
		Assert.assertEquals(18, totalCards);
		
		//making sure that each person has three cards
		for (int i=0; i<players.size(); i++) {
			Assert.assertEquals(3, players.get(i).getCards().size());
		}
		
		//make sure that each card is unique to the player
		int revolver = 0;
		int alderson = 0;
		int mustard = 0;
		int plum = 0;
		
		//making sure that each above card is accounted for
		
		//run test 100 times
		for (int t=0; t<100; t++) {
			for (int i=0; i<players.size(); i++) {
				//for each player's cards
				for (int j=0; j<players.get(i).getCards().size(); j++) {
					String card = players.get(i).getCards().get(j).getName();
					CardType type = players.get(i).getCards().get(j).getCardtype();
					//check how many times the following 4 cards show up
					if (card.equals("Revolver") && type == CardType.WEAPON)
						revolver++;
					if (card.equals("Alderson") && type == CardType.ROOM)
						alderson++;
					if (card.equals("Colonel Mustard") && type == CardType.PERSON)
						mustard++;
					if (card.equals("Professor Plum") && type == CardType.PERSON)
						plum++;
				}
			}
		}
		
		//assuming the solution is not any of the 4 cards
		if (!solution.getPerson().equals("Professor Plum")) 
			assertEquals(100, plum);
		if (!solution.getRoom().equals("Alderson"))
			assertEquals(100, alderson);
		if (!solution.getWeapon().equals("Revolver"))
			assertEquals(100, revolver);
		if (!solution.getPerson().equals("Colonel Mustard"))
			assertEquals(100, mustard);
	
	}
}

