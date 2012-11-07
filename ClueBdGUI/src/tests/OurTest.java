package tests;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import Board.Board;
import Board.BoardCell;
import Board.RoomCell;

public class OurTest {
	private static Board board;
	public static final int NUM_ROOMS = 12;
	public static final int NUM_ROWS = 21;
	public static final int NUM_COLUMNS = 20;
	
	@BeforeClass
	public static void setUp() {
		board = new Board();
		board.loadConfigFiles();
	}
	
	//Ensure rooms map has correct number of rooms
	@Test
	public void testRooms() {
		Map<Character, String> rooms = board.getRooms();
		assertEquals(NUM_ROOMS, rooms.size());
		assertEquals("Marquez", rooms.get('M'));
		assertEquals("Chauvenet", rooms.get('C'));
		assertEquals("Stratton", rooms.get('S'));
		assertEquals("Closet", rooms.get('X'));
		assertEquals("Walkway", rooms.get('W'));
	}
	
	//Ensure mapping from character to Room is working
	@Test
	public void testBoardDimensions() {
		assertEquals(NUM_COLUMNS, board.getNumColumns());
		assertEquals(NUM_ROWS, board.getNumRows());		
	}
	
	//Test Doors
	@Test
	public void FourDoorDirections() {
		// Test one each RIGHT/LEFT/UP/DOWN
		RoomCell room = board.getRoomCellAt(2, 3);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getRoomCellAt(3, 2);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.DOWN, room.getDoorDirection());
		room = board.getRoomCellAt(13, 4);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.UP, room.getDoorDirection());
		room = board.getRoomCellAt(19, 15);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.LEFT, room.getDoorDirection());
		//Test that room pieces that aren't doors
		room = board.getRoomCellAt(14, 3);
		assertFalse(room.isDoorway());	
		//Test that walkways are not doors
		BoardCell cell = board.getCellAt(board.calcIndex(9, 14));
		assertFalse(room.isDoorway());		

	}
	
	//Test that we have the correct number of doors
	@Test
	public void testNumberOfDoorways() 
	{
		int numDoors = 0;
		int totalCells = board.getNumColumns() * board.getNumRows();
		Assert.assertEquals(420, totalCells);
		for (int i=0; i<totalCells; i++)
		{
			BoardCell cell = board.getCellAt(i);
			if (cell.isDoorway())
				numDoors++;
		}
		Assert.assertEquals(27, numDoors);
	}

	//Test that the calcIndex function works properly
	@Test
	public void testCalcIndex() {
		// Test each corner of the board
		assertEquals(0, board.calcIndex(0, 0));
		assertEquals(NUM_COLUMNS-1, board.calcIndex(0, NUM_COLUMNS-1));
		assertEquals(400, board.calcIndex(NUM_ROWS-1, 0));
		assertEquals(419, board.calcIndex(NUM_ROWS-1, NUM_COLUMNS-1));
		// Test a couple others
		assertEquals(21, board.calcIndex(1, 1));
		assertEquals(6, board.calcIndex(0, 6));		
	}
	
	//Test that rooms have correct room initial
	@Test
	public void testRoomInitials() {
		assertEquals('M', board.getRoomCellAt(20, 0).getInitial());
		assertEquals('A', board.getRoomCellAt(0, 0).getInitial());
		assertEquals('B', board.getRoomCellAt(6, 0).getInitial());
		assertEquals('H', board.getRoomCellAt(20, 19).getInitial());
		assertEquals('E', board.getRoomCellAt(17, 9).getInitial());
	}
}