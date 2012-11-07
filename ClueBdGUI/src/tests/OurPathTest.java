package tests;

import java.util.LinkedList;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import Board.Board;
import Board.BoardCell;

public class OurPathTest {
	private static Board board;
	@BeforeClass
	public static void setUp() {
		board = new Board();
		board.loadConfigFiles();
	}

	// Ensure that player does not move around within room
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// Test a corner
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(0, 0));
		Assert.assertEquals(0, testList.size());
		// Test one that has walkway underneath
		testList = board.getAdjList(board.calcIndex(3, 0));
		Assert.assertEquals(0, testList.size());
		// Test one that has walkway above
		testList = board.getAdjList(board.calcIndex(11, 11));
		Assert.assertEquals(0, testList.size());
		// Test one that is in middle of room
		testList = board.getAdjList(board.calcIndex(3, 18));
		Assert.assertEquals(0, testList.size());
		// Test one beside a door
		testList = board.getAdjList(board.calcIndex(14, 10));
		Assert.assertEquals(0, testList.size());
		// Test one in a corner of room
		testList = board.getAdjList(board.calcIndex(15, 18));
		Assert.assertEquals(0, testList.size());
	}

	// Ensure that the adjacency list from a doorway is only the
	// walkway. NOTE: This test could be merged with door 
	// direction test. 
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY RIGHT 
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(5, 1));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(5, 2)));
		// TEST DOORWAY LEFT
		testList = board.getAdjList(board.calcIndex(7, 11));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(7, 10)));
		//TEST DOORWAY DOWN
		testList = board.getAdjList(board.calcIndex(4, 9));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(5, 9)));
		//TEST DOORWAY UP
		testList = board.getAdjList(board.calcIndex(18, 17));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(17, 17)));
		
	}

	// Test a variety of walkway scenarios
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on top edge of board, one walkway piece and one door
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(0, 7));
		Assert.assertTrue(testList.contains(6));
		Assert.assertTrue(testList.contains(8));
		Assert.assertEquals(2, testList.size());
		
		// Test on left edge of board, three walkway pieces
		testList = board.getAdjList(board.calcIndex(12, 0));
		Assert.assertTrue(testList.contains(board.calcIndex(11, 0)));
		Assert.assertTrue(testList.contains(board.calcIndex(12, 1)));
		Assert.assertTrue(testList.contains(board.calcIndex(13, 0)));
		Assert.assertEquals(3, testList.size());

		// Test between two rooms, walkways right and left
		testList = board.getAdjList(board.calcIndex(4, 1));
		Assert.assertTrue(testList.contains(board.calcIndex(4, 0)));
		Assert.assertTrue(testList.contains(board.calcIndex(4, 2)));
		Assert.assertEquals(2, testList.size());

		// Test surrounded by 4 walkways
		testList = board.getAdjList(board.calcIndex(11,5));
		Assert.assertTrue(testList.contains(board.calcIndex(10, 5)));
		Assert.assertTrue(testList.contains(board.calcIndex(11, 4)));
		Assert.assertTrue(testList.contains(board.calcIndex(12, 5)));
		Assert.assertTrue(testList.contains(board.calcIndex(11 ,6)));
		Assert.assertEquals(4, testList.size());
		
		// Test on bottom edge of board, next to 1 room piece
		testList = board.getAdjList(board.calcIndex(20, 14));
		Assert.assertTrue(testList.contains(board.calcIndex(20, 13)));
		Assert.assertTrue(testList.contains(board.calcIndex(19, 14)));
		Assert.assertEquals(2, testList.size());
		
		// Test on ridge edge of board, next to 1 room piece
		testList = board.getAdjList(board.calcIndex(16, 19));
		Assert.assertTrue(testList.contains(board.calcIndex(16, 18)));
		Assert.assertTrue(testList.contains(board.calcIndex(15, 19)));
		Assert.assertEquals(2, testList.size());

	}

	// Test adjacency at entrance to rooms
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(0, 12));
		Assert.assertTrue(testList.contains(board.calcIndex(0, 11)));
		Assert.assertTrue(testList.contains(board.calcIndex(0, 13)));
		Assert.assertEquals(2, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(board.calcIndex(20, 8));
		Assert.assertTrue(testList.contains(board.calcIndex(19, 8)));
		Assert.assertTrue(testList.contains(board.calcIndex(20, 7)));
		Assert.assertTrue(testList.contains(board.calcIndex(20, 9)));
		Assert.assertEquals(3, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(board.calcIndex(7, 10));
		Assert.assertTrue(testList.contains(board.calcIndex(7, 11)));
		Assert.assertTrue(testList.contains(board.calcIndex(8, 10)));
		Assert.assertTrue(testList.contains(board.calcIndex(6, 10)));
		Assert.assertEquals(3, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(board.calcIndex(12, 3));
		Assert.assertTrue(testList.contains(board.calcIndex(13, 3)));
		Assert.assertTrue(testList.contains(board.calcIndex(12, 2)));
		Assert.assertTrue(testList.contains(board.calcIndex(12, 4)));
		Assert.assertTrue(testList.contains(board.calcIndex(11, 3)));
		Assert.assertEquals(4, testList.size());
	}

	
	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room
	// Have already tested adjacency lists on all four edges, will
	// only test two edges here
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(board.calcIndex(20, 4), 1);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(20, 3))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(19, 4))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(20, 5))));	
		
		board.calcTargets(board.calcIndex(0, 15), 1);
		targets= board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(0, 14))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(1, 15))));			
	}
	// Tests of just walkways, 2 steps
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(board.calcIndex(4, 4), 2);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(4, 2))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(4, 6))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(5, 5))));	
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(5, 3))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(3, 5))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(2, 4))));	

		board.calcTargets(board.calcIndex(12, 19), 2);
		targets= board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 19))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(10, 19))));			
	}
	// Tests of just walkways, 4 steps
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(board.calcIndex(0, 15), 4);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(4, 15))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(0, 11))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(3, 14))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(0, 13))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(2, 15))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(1, 14))));
		
		// Includes a path that doesn't have enough length
		board.calcTargets(board.calcIndex(20, 4), 4);
		targets= board.getTargets();
		Assert.assertEquals(8, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(19, 3))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(19, 5))));	
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(18, 4))));	
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(20, 8))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(17, 5))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(17, 4))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(17, 3))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(20, 6))));	
	}	
	// Tests of just walkways plus one door, 6 steps
	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(board.calcIndex(0,15), 6);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(10, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(0, 11))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(0, 13))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 15))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(2, 15))));	
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(4, 13))));	
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(3, 12))));	
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(4, 13))));	
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(3, 14))));	
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(4, 15))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(5, 16))));	
	}	
	
	// Test getting into a room
	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly 2 away
		board.calcTargets(board.calcIndex(7, 19), 2);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(5, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 18))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(5, 19))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(9, 19))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(8, 18))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 17))));
	}
	
	// Test getting into room, doesn't require all steps
	@Test
	public void testTargetsIntoRoomShortcut() 
	{
		board.calcTargets(board.calcIndex(7, 19), 3);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(8, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 18))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 17))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 16))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(10, 19))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(9, 18))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(8, 17))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(8, 19))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 18))));
	}

	// Test getting out of a room
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(board.calcIndex(2, 3), 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		Assert.assertEquals(1, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(2, 4))));
		// Take two steps
		board.calcTargets(board.calcIndex(2, 3), 2);
		targets= board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(1, 4))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(3, 4))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(2, 5))));
	}

}