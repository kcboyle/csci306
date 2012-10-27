package main;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.HashSet;

import javax.xml.stream.events.StartDocument;

import main.Card.CardType;
/**
 * @author: Craig Carlson
 * @author: Lars Walen
 */

public class Board {

	private ArrayList<BoardCell> cells = new ArrayList<BoardCell>();
	private Map<Character, String> rooms = new HashMap<Character, String>();
	private Map<String, Character> iRooms = new HashMap<String, Character>();		//stores the room initial
	private ArrayList<Card> allCards = new ArrayList<Card>();
	private ArrayList<ComputerPlayer> compPlayers = new ArrayList<ComputerPlayer>();
	private ArrayList<String> answers = new ArrayList<String>();					//stores 3 strings that will be the game answer
	private ArrayList<String> accusations = new ArrayList<String>();				//stores 3 strings that will be a person's accusation or suggestion
		
	private HumanPlayer self = new HumanPlayer();
	private int numRows;
	
	private int numColumns;
	// The following will be used to check card configurations
	private int numPlayers;
	private int numRooms;
	private int numWeapons;
	//The following will be used to check cards dealt
	private int numDealt;				//will track number of total cards dealt
	//the following will be used to track winning status
	private boolean won;

	// Adjacencies and targets related members
	private Map<Integer, LinkedList<Integer>> adjMatrix;
	public HashSet<Integer> targets;
	private boolean[] visited;

	//Who's Turn is it?? hmmmmm
	private Player currentPlayer;
	
	/**
	 * Creates board given filenames of legend file and board config file
	 * @param legendFilename
	 * @param boardFilename
	 */
	public Board(String legendFilename, String boardFilename, String playersFilename, String cardsFilename) {
		try {
			loadConfigFiles(legendFilename, boardFilename, playersFilename, cardsFilename);
		} catch (BadConfigFormatException e) {
			System.out.println(e);
		}
		visited = new boolean[numRows * numColumns];
		adjMatrix = new HashMap<Integer, LinkedList<Integer>>();
		targets = new HashSet<Integer>();
		calcAdjacencies();
	}

	/**
	 * Calls helper functions to load data from legend and and board config files
	 */
	public void loadConfigFiles(String legendFilename, String boardFilename, String playersFilename, String cardsFilename) throws BadConfigFormatException {

		try {
			readLegend(legendFilename);
		} catch (FileNotFoundException e) {
			System.out.println("could not read legend file");
		}

		try {
			readBoard(boardFilename);
		} catch (FileNotFoundException e) {
			System.out.println("could not read board file");
		}
		try {
			readBoard(playersFilename);
		} catch (FileNotFoundException e) {
			System.out.println("could not read players file");
		}
		try {
			readBoard(cardsFilename);
		} catch (FileNotFoundException e) {
			System.out.println("could not read cards file");
		}
	}

	/**
	 * Iterates through lines in legend file, splits each line at ", ", and adds initial and name to rooms map.
	 * Also adds to inverted map, to allow getting initials from names
	 */
	public void readLegend(String legendFilename) throws FileNotFoundException, BadConfigFormatException {
		String legendLine; 
		FileReader legendFile = new FileReader(legendFilename);
		Scanner scan = new Scanner(legendFile);

		while( scan.hasNextLine() ) {
			legendLine = scan.nextLine();
			String[] line;
			line = legendLine.split(", ");
			if( line.length > 2 ) throw new BadConfigFormatException("Legend file has more than two items per line");
			rooms.put(line[0].charAt(0), line[1]);
			iRooms.put( line[1], line[0].charAt(0));
		}
		scan.close();
	} 

	/**
	 * Iterates through lines in board config file, splitting by "," and adding each symbol to cells as the appropriate cell type
	 */
	public void readBoard(String boardFilename) throws FileNotFoundException, BadConfigFormatException {
		FileReader boardFile = new FileReader(boardFilename);
		Scanner scan = new Scanner(boardFile);
		char walkwayKey = getInitial("Walkway");

		LinkedList<Integer> rowSize = new LinkedList<Integer>();		//stores number of "cells" per line for EVERY LINE
		numColumns = 0;
		numRows = 0;
		while( scan.hasNext() ) {
			String line = scan.next();
			String cellInits[] = line.split(",");

			numColumns = cellInits.length;

			for( int i = 0; i < cellInits.length; i++ ) {
				String cellInit = cellInits[i];

				BoardCell cell;
				if( cellInit.charAt(0) == walkwayKey ) {
					cell = new WalkwayCell();
				} else {
					cell = new RoomCell(cellInit);
				}
				cells.add(cell);
				//System.out.print(cellInit + "\t");
			}
			//System.out.print("\n");

			rowSize.add(numColumns);
			numRows++;
		}
		scan.close();
		for( int i = 1; i < rowSize.size(); i++ ) {
			if( rowSize.get(i) != rowSize.get(i-1) ) throw new BadConfigFormatException("Not all rows the same length");
		}

	}

	/**
	 * Calculates adjacencies list for all points on board
	 */
	public void calcAdjacencies() {

		//System.out.println("calculating adjacencies");
		for( int row = 0; row < numRows; row++ ) {
			for( int col = 0; col < numColumns; col++ ) {

				LinkedList<Integer> list = new LinkedList<Integer>();
				//System.out.println("adj list for " + calcIndex(row, col));
//				if( getCellAt(calcIndex(row, col)).isRoom() ) {
//					System.out.println(" with door dir " + getRoomCellAt(row, col).getDoorDirection());
//				}

				if( getCellAt(calcIndex(row, col)).isWalkway() || getCellAt(calcIndex(row, col)).isDoorway() ) {

					// Up
					if( row-1 >= 0 && ( getCellAt(calcIndex(row-1, col)).isDoorway() || getCellAt(calcIndex(row-1, col)).isWalkway() ) ) {
						if( getCellAt(calcIndex(row-1, col)).isWalkway() || getRoomCellAt(row-1, col).getDoorDirection() == RoomCell.DoorDirection.DOWN) {
							list.add(calcIndex(row-1, col));
							//System.out.println("\tadding " + calcIndex(row-1, col));
						}
					}
					// Down
					if( row+1 < numRows && ( getCellAt(calcIndex(row+1, col)).isDoorway() || getCellAt(calcIndex(row+1, col)).isWalkway() ) ) {
						if( getCellAt(calcIndex(row+1, col)).isWalkway() || getRoomCellAt(row+1, col).getDoorDirection() == RoomCell.DoorDirection.UP ) {
							list.add(calcIndex(row+1, col));
							//System.out.println("\tadding " + calcIndex(row+1, col));
						}
					}
					// Left
					if( col-1 >= 0 && ( getCellAt(calcIndex(row, col-1)).isDoorway() || getCellAt(calcIndex(row, col-1)).isWalkway() ) ) {
						if( getCellAt(calcIndex(row, col-1)).isWalkway() || getRoomCellAt(row, col-1).getDoorDirection() == RoomCell.DoorDirection.RIGHT ) {
							list.add(calcIndex(row, col-1));
							//System.out.println("\tadding " + calcIndex(row, col-1));
						}
					}
					// Right
					if( col+1 < numColumns && ( getCellAt(calcIndex(row, col+1)).isDoorway() || getCellAt(calcIndex(row, col+1)).isWalkway() ) ) {
						if( getCellAt(calcIndex(row, col+1)).isWalkway() || getRoomCellAt(row, col+1).getDoorDirection() == RoomCell.DoorDirection.LEFT ) {
							list.add(calcIndex(row, col+1));
							//System.out.println("\tadding " + calcIndex(row, col+1));
						}
					}
				}
				adjMatrix.put(calcIndex(row, col), list);
			}
		}
	}

	/**
	 * 
	 * @return HashSet<BoardCell>
	 */
	public HashSet<BoardCell> getTargets() {

		// maybe put this in calcTargets
		for( int cell = 0; cell < numRows*numColumns; cell++ ) {
			visited[cell] = false;
		}

		HashSet<BoardCell> targetCells = new HashSet<BoardCell>();
		for( int i : targets ) {
			targetCells.add(getCellAt(i));
		}
		return targetCells;	
	}

	public void calcTargets(int startLocation, int numberOfSteps) {
		targets.clear();
		if( getCellAt(startLocation).isDoorway() ) {
			visited[startLocation] = true;
			targets = calcTargetsRecursively(getAdjList(startLocation).getLast(), numberOfSteps-1);
		} else {
			targets = calcTargetsRecursively(startLocation, numberOfSteps);
		}
	}

	public HashSet<Integer> calcTargetsRecursively(int startLocation, int numberOfSteps) {
		visited[startLocation] = true;
		HashSet<Integer> set = new HashSet<Integer>();
		if( numberOfSteps == 0 || getCellAt(startLocation).isDoorway()) {
			set.add(startLocation);
			visited[startLocation] = false;
			return set;
		}

		for( int i : getAdjList(startLocation) ) {
			if( !visited[i] ) {
				visited[i] = true;		
				set.addAll(calcTargetsRecursively(i, numberOfSteps-1));
				visited[i] = false;
			}
		}
		return set;
	}
	
	public String disproveSuggestion(ArrayList<String> accusation) {
		String c = accusation.get(0);
		return c;
	}

	/**
	 * Convert row and column coordinates to cell index
	 */
	public int calcIndex(int row, int col) {
		return col + (row * numColumns);
	}

	public int[] calcCoords(int index) {
		int[] coords = { index/numColumns, index%numColumns };
		return coords;
	}

	/**
	 * Get RoomCell at row and column coordinates 
	 */
	public RoomCell getRoomCellAt(int row, int col) {
		return (RoomCell)cells.get(calcIndex(row, col));
	}

	/*
	 *  Getters
	 */
	public LinkedList<Integer> getAdjList(int index) {
		return adjMatrix.get(index);
	}
	public char getInitial(String roomName) {
		return iRooms.get(roomName);
	}
	public Map<Character, String> getRooms() {
		return rooms;
	}
	public ArrayList<BoardCell> getCells() {
		return cells;
	}
	public int getNumRows() {
		return numRows;
	}
	public int getNumColumns() {
		return numColumns;
	}
	public ArrayList<ComputerPlayer> getCompPlayers() {
		return compPlayers;
	}
	public void setCompPlayers(ArrayList<ComputerPlayer> compPlayers) {
		this.compPlayers = compPlayers;
	}
	public HumanPlayer getSelf() {
		return self;
	}
	public ArrayList<Card> getAllCards() {
		return allCards;
	}
	public void setAllCards(ArrayList<Card> allCards) {
		this.allCards = allCards;
	}
	public int getNumPlayers() {
		return numPlayers;
	}
	public int getNumRooms() {
		return numRooms;
	}
	public int getNumWeapons() {
		return numWeapons;
	}
	public int getNumDealt() {
		return numDealt;
	}
	public boolean isWon() {
		return won;
	}
	public ArrayList<String> getAnswers() {
		return answers;
	}
	public void setAnswers(ArrayList<String> answers) {
		this.answers = answers;
	}
	public ArrayList<String> getAccusations() {
		return accusations;
	}
	public void setAccusations(ArrayList<String> accusations) {
		//GOING TO NEED TO CLEAR BEFORE SETTING!!!!!!!!!!!!!!!!!
		this.accusations = accusations;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public BoardCell getCellAt(int i) {
		return cells.get(i);
	}
}
