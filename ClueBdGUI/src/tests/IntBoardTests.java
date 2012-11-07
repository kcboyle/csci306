package tests;

import java.util.LinkedList;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import GamePaths.IntBoard;

public class IntBoardTests {
	
	private IntBoard board;
	private LinkedList<Integer> adjList;
	
	@Before
	public void setUp() {
		board = new IntBoard();
		board.calcAdjacencies();
		board.ROW = 4;
		board.COLS = 4;
		board.GRID_PIECES = board.ROW*board.COLS;
	}
	
	@Test
	public void testCalcIndex() {
		int index = board.calcIndex(0,0);
		Assert.assertEquals(index, 0);
		index = board.calcIndex(IntBoard.ROW-1,IntBoard.COLS-1);
		Assert.assertEquals(index, IntBoard.GRID_PIECES-1);
	}
	
// The following tests that the adjacency lists being created
//  are acurate.
	@Test
	public void testCalcAdjacency0() {
		adjList = board.getAdjList(0);
		Assert.assertTrue(adjList.contains(4));
		Assert.assertTrue(adjList.contains(1));
		Assert.assertEquals(2, adjList.size());
	}
	
	@Test
	public void testCalcAdjacency15() {
		adjList = board.getAdjList(15);
		Assert.assertTrue(adjList.contains(14));
		Assert.assertTrue(adjList.contains(11));
		Assert.assertEquals(2, adjList.size());
	}
	
	@Test
	public void testCalcAdjacency7() {
		adjList = board.getAdjList(7);
		Assert.assertTrue(adjList.contains(3));
		Assert.assertTrue(adjList.contains(11));
		Assert.assertTrue(adjList.contains(6));
		Assert.assertEquals(3, adjList.size());
	}
	
	@Test
	public void testCalcAdjacency8() {
		adjList = board.getAdjList(8);
		Assert.assertTrue(adjList.contains(4));
		Assert.assertTrue(adjList.contains(9));
		Assert.assertTrue(adjList.contains(12));
		Assert.assertEquals(3, adjList.size());
	}
	
	@Test
	public void testCalcAdjacency5() {
		adjList = board.getAdjList(5);
		Assert.assertTrue(adjList.contains(1));
		Assert.assertTrue(adjList.contains(4));
		Assert.assertTrue(adjList.contains(6));
		Assert.assertTrue(adjList.contains(9));
		Assert.assertEquals(4, adjList.size());
	}
	
	@Test
	public void testCalcAdjacency10() {
		adjList = board.getAdjList(10);
		Assert.assertTrue(adjList.contains(6));
		Assert.assertTrue(adjList.contains(9));
		Assert.assertTrue(adjList.contains(11));
		Assert.assertTrue(adjList.contains(14));
		Assert.assertEquals(4, adjList.size());
	}

// The following tests are for making sure the calculation of the 
//  target spots (places able to move to) are correct.
	@Test
	public void testTargets0_3()
	{
		board.calcTargets(0, 3);
		Set<Integer> targets= board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(12));
		Assert.assertTrue(targets.contains(9));
		Assert.assertTrue(targets.contains(1));
		Assert.assertTrue(targets.contains(6));
		Assert.assertTrue(targets.contains(3));
		Assert.assertTrue(targets.contains(4));
	}
	
	@Test
	public void testTargets4_3()
	{
		board.calcTargets(4, 3);
		Set<Integer> targets= board.getTargets();
		Assert.assertEquals(7, targets.size());
		Assert.assertTrue(targets.contains(0));
		Assert.assertTrue(targets.contains(2));
		Assert.assertTrue(targets.contains(5));
		Assert.assertTrue(targets.contains(7));
		Assert.assertTrue(targets.contains(8));
		Assert.assertTrue(targets.contains(10));
		Assert.assertTrue(targets.contains(13));
	}
	
	@Test
	public void testTargets6_3()
	{
		board.calcTargets(6, 3);
		Set<Integer> targets= board.getTargets();
		Assert.assertEquals(8, targets.size());
		Assert.assertTrue(targets.contains(0));
		Assert.assertTrue(targets.contains(2));
		Assert.assertTrue(targets.contains(5));
		Assert.assertTrue(targets.contains(7));
		Assert.assertTrue(targets.contains(8));
		Assert.assertTrue(targets.contains(10));
		Assert.assertTrue(targets.contains(13));
		Assert.assertTrue(targets.contains(15));
	}
	
	@Test
	public void testTargets6_1()
	{
		board.calcTargets(6, 1);
		Set<Integer> targets= board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(2));
		Assert.assertTrue(targets.contains(5));
		Assert.assertTrue(targets.contains(7));
		Assert.assertTrue(targets.contains(10));
	}

	@Test
	public void testTargets6_6()
	{
		board.calcTargets(6, 6);
		Set<Integer> targets= board.getTargets();
		Assert.assertEquals(7, targets.size());
		Assert.assertTrue(targets.contains(1));
		Assert.assertTrue(targets.contains(3));
		Assert.assertTrue(targets.contains(4));
		Assert.assertTrue(targets.contains(9));
		Assert.assertTrue(targets.contains(11));
		Assert.assertTrue(targets.contains(12));
		Assert.assertTrue(targets.contains(14));
	}
	
	@Test
	public void testTargets0_5()
	{
		board.calcTargets(0, 5);
		Set<Integer> targets= board.getTargets();
		Assert.assertEquals(8, targets.size());
		Assert.assertTrue(targets.contains(1));
		Assert.assertTrue(targets.contains(3));
		Assert.assertTrue(targets.contains(4));
		Assert.assertTrue(targets.contains(6));
		Assert.assertTrue(targets.contains(9));
		Assert.assertTrue(targets.contains(11));
		Assert.assertTrue(targets.contains(12));
		Assert.assertTrue(targets.contains(14));

	}
}
