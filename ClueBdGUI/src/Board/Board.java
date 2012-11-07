package Board;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import junit.framework.Assert;

import Board.Card.CardType;
//import tests.Arraylist;
import Board.Player;
import Board.RoomCell.DoorDirection;

public class Board {

	private ArrayList<BoardCell> cells = new ArrayList<BoardCell>();
	private static ArrayList<Player> players = new ArrayList<Player>();
	private static ArrayList<Card> deck = new ArrayList<Card>();
	private Map<Character, String> rooms = null;
	private Map<BoardCell, LinkedList<Integer>> adjMtx;
	private Boolean[] visited;
	private Set<BoardCell> targets;
	private static Solution solution;
	private int numRows = 21;
	private int numColumns = 20;
	//rader test from previous group
	private String configOne = "Config1.txt";
	private String configTwo = "Config2.txt";
	//maria and anastasia's stuff
	private String ourlegend = "legend.txt";
	private String ourboard = "clueboard.csv";

	public Board() {
		super();
		adjMtx = new HashMap<BoardCell, LinkedList<Integer>>();
		targets = new HashSet<BoardCell>();
		loadConfigFiles();
		calcAdjacencies();
	}

	public RoomCell getRoomCellAt(int row, int column){
		return (RoomCell) cells.get(calcIndex(row, column));	
	}

	public BoardCell getCellAt(int start) {
		return cells.get(start);
	}

	public Set<BoardCell> getTargets(){
		return targets;
	}

	public void setNumRows(int numRows) {
		this.numRows = numRows;
	}

	public void setNumColumns(int numColumns) {
		this.numColumns = numColumns;
	}

	public ArrayList<BoardCell> getCells() {
		return cells;
	}

	public Map<Character, String> getRooms() {
		return rooms;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}


	public int calcIndex(int row, int column){
		return (row*numColumns+column);
	}
	//returns the coordinate of the index
	public int[] calcCoords(int index) {
		int[] coords = { index/numColumns, index%numColumns };
		return coords;
	}

	public boolean doorwayWorks(int index, RoomCell.DoorDirection dd) {
		RoomCell rc = (RoomCell) cells.get(index);
		if(rc.isDoorway() && rc.getDoorDirection() == dd)
			return true;
		return false;
	}

	public void calcAdjacencies(){
		LinkedList<Integer> local;

		for(int i=0; i<numRows; i++) {
			for(int j=0; j<numColumns; j++) {
				local = new LinkedList<Integer>();
				if(cells.get(calcIndex(i,j)).isRoom() && !cells.get(calcIndex(i,j)).isDoorway()) {
					adjMtx.put(cells.get(calcIndex(i,j)), local);
					continue;
				}
				//Make sure you exit room correctly
				if(cells.get(calcIndex(i,j)).isRoom() && cells.get(calcIndex(i,j)).isDoorway()) {
					RoomCell tempRoom = (RoomCell)cells.get(calcIndex(i,j));
					switch(tempRoom.getDoorDirection()){

					case UP : local.add(numColumns*(i-1)+j);
					adjMtx.put(cells.get(calcIndex(i, j)), local);
					continue;
					case DOWN : local.add(numColumns*(i+1)+j);
					adjMtx.put(cells.get(calcIndex(i, j)), local);
					continue;
					case LEFT : local.add(numColumns*(i)+(j-1));
					adjMtx.put(cells.get(calcIndex(i, j)), local);
					continue;
					case RIGHT : local.add(numColumns*(i)+(j+1));
					adjMtx.put(cells.get(calcIndex(i, j)), local);
					continue;
					case NONE:
					}
				}
				// Check above the cell
				if(i!=0) {
					if(cells.get(calcIndex(i-1,j)).isWalkway())
						local.add(numColumns*(i-1)+j);
					else if(doorwayWorks(calcIndex(i-1,j), RoomCell.DoorDirection.DOWN))
						local.add(numColumns*(i-1)+j);
				}
				// Check below the cell
				if (i!=numRows-1) {
					if(cells.get(calcIndex(i+1,j)).isWalkway()) 
						local.add(numColumns*(i+1) + j);
					else if(doorwayWorks(calcIndex(i+1,j), RoomCell.DoorDirection.UP))
						local.add(numColumns*(i+1) + j);
				}
				// Check left of the cell
				if(j!=0) {
					if(cells.get(calcIndex(i,j-1)).isWalkway())
						local.add(numColumns*i + j - 1);
					else if(doorwayWorks(calcIndex(i,j-1), RoomCell.DoorDirection.RIGHT))
						local.add(numColumns*i+j-1);
				}
				// Check right of the cell
				if (j!=numColumns-1) {
					if(cells.get(calcIndex(i,j+1)).isWalkway())
						local.add(numColumns*i + j + 1);
					else if(doorwayWorks(calcIndex(i,j+1), RoomCell.DoorDirection.LEFT))
						local.add(numColumns*i+j+1);
				}
				adjMtx.put(cells.get(calcIndex(i,j)), local);
			}
		}
	}

	public void recursiveTargets(int start, int steps) {
		int place;
		BoardCell cell = cells.get(start);
		visited[start] = true;
		if(steps == 0) {
			targets.add(cell);
			visited[start] = false;
			return;
		} else {
			LinkedList<Integer> adjacent = getAdjList(start);
			for(int i=0; i<adjacent.size(); i++) {
				place = adjacent.get(i);
				if(visited[place] == false) {
					if(cells.get(place).isDoorway())
						targets.add(cells.get(place));
					else
						recursiveTargets(place , steps-1);
				}
			}
		}
		visited[start] = false;
	}

	public void calcTargets(int start, int steps){
		targets = new HashSet<BoardCell>();
		recursiveTargets(start, steps);
	}
	public LinkedList<Integer> getAdjList(int cell){
		return adjMtx.get(cells.get(cell));
	}

	public void loadPeople(String filepeople){

		Player player = null;

		try{
			FileReader reader = new FileReader(filepeople);
			Scanner in  = new Scanner(reader);

			int position = 0;
			String row;
			String column;

			//reading from the file
			//Splitting Person, Color, Starting Point
			String line;
			while (in.hasNext()) {
				line = in.nextLine();
				String slash = "/";
				String[] tokens = line.split(slash);

				String comma = ",";
				String[] tokens2 = tokens[2].split(comma);
				tokens2[0] = tokens2[0].substring(1);

				//getting the position value from character to integer
				row = tokens2[0];
				column = tokens2[1];
				position = calcIndex(Integer.parseInt(row), Integer.parseInt(column));

				//Mrs.Peacock is always the human player
				if(tokens[0].equals("Mrs. Peacock")) {
					player = new HumanPlayer(tokens[0], tokens[1].substring(1), position);
				} else {
					player = new ComputerPlayer(tokens[0], tokens[1].substring(1), position);

				}
				//adding all players to the array list
				players.add(player);
				//adds player cards to deck
				deck.add(new Card(player.getName(), CardType.PERSON));
			}

		} catch (FileNotFoundException e){
			System.out.println("ERROR: " + e + " was not found");
		}
	}

	public void loadWeapons(String weaponFile) {
		try {
			FileReader reader = new FileReader(weaponFile);
			Scanner in = new Scanner(reader);
			while (in.hasNext()) {
				deck.add(new Card(in.nextLine(), CardType.WEAPON));
			}
		} catch (FileNotFoundException e){
			System.out.println("ERROR: " + e + " was not found");
		}
	}

	public void loadConfigFiles(){

		//load players
		loadPeople("players.txt");
		loadWeapons("weapons.txt");

		try{
			//FileReader reader = new FileReader(configOne);
			FileReader reader = new FileReader(ourlegend);
			Scanner in = new Scanner(reader);
			RoomCell r;
			WalkwayCell w;
			rooms = new HashMap<Character, String>();
			String input;
			int rowCount = 0;
			int totalCount = 0;
			String name;
			while(in.hasNext()){
				name = in.nextLine();
				char key = name.charAt(0);
				name = name.substring(3);
				rooms.put(key, name);
				//adds room cards to deck
				if (!name.equals("Walkway") && !name.equals("Closet") && !name.equals("Door"))
					deck.add(new Card(name, CardType.ROOM));
			}

			in.close();
			//reader = new FileReader(configTwo);
			reader = new FileReader(ourboard);
			in = new Scanner (reader);
			while(in.hasNextLine())
			{
				String line = in.nextLine();
				for(int i=0; i<line.length(); i++) {
					if(line.charAt(i) == ',')
						i++;
					r = new RoomCell();
					w = new WalkwayCell();
					int end;
					if(i >= line.length()-1) {
						end = i+1;
						input = line.substring(i, end);
					}
					else {
						if(line.charAt(i+1)!= ',') {
							end = i+2;
						}
						else
							end = i+1;
						input = line.substring(i, end);
					}
					r.setInitial(input.charAt(0));
					if(input.length() > 1)
					{
						if (input.charAt(1) == 'U')
							r.setDoorDirection(DoorDirection.UP);

						if (input.charAt(1) == 'D')
							r.setDoorDirection(DoorDirection.DOWN);

						if (input.charAt(1) == 'L')
							r.setDoorDirection(DoorDirection.LEFT);

						if (input.charAt(1) == 'R')
							r.setDoorDirection(DoorDirection.RIGHT);	
					}
					if (input.charAt(0) == 'W')
					{
						cells.add(w);
					}
					else
					{
						r.setInitial(input.charAt(0));
						cells.add(r);
					}
					totalCount++;
					if(end == i+2)
						i++;
				}
				rowCount++;
			}
			in.close();

			numRows = rowCount;
			numColumns = totalCount / rowCount; 
			visited = new Boolean [totalCount];
			for (int i=0; i<totalCount; i++)
				visited[i] = false;

		} catch(FileNotFoundException e) {
			System.out.println(e);
		}
	}

	public boolean checkAccusation(String person, String weapon, String room) {
		if (solution.getPerson().equals(person) && solution.getWeapon().equals(weapon) && solution.getRoom().equals(room))
			return true;
		else
			return false;
	}

	//for createSuggestion in ComputerPlayer
	public String findMapValue(char initial){
		for (Map.Entry<Character, String> entry: rooms.entrySet()) {
			if (entry.getKey().equals(initial)) {
				return entry.getValue();
			}
		}
		return null;		
	}

	public void deal() {
		String person = null;
		String weapon = null;
		String room = null;

		//shuffle the cards
		Collections.shuffle(deck);

		//pick solution
		for (int i=0; i<deck.size(); i++) {
			if (deck.get(i).getCardtype() == CardType.PERSON) {
				person = deck.get(i).getName();
				continue;
			}
			if (deck.get(i).getCardtype() == CardType.WEAPON) {
				weapon = deck.get(i).getName();
				continue;
			}
			if (deck.get(i).getCardtype() == CardType.ROOM) {
				room = deck.get(i).getName();
				continue;
			}
		}
		solution = new Solution(person, weapon, room);

		//put solution into an ArrayList
		ArrayList<String> answer = new ArrayList<String>();
		answer.add(person);
		answer.add(weapon);
		answer.add(room);

		//find out how many cards each player gets
		int cardsPer = deck.size()/players.size();

		//each person's cards
		ArrayList<Card> setofCards;
		int loc = 0;

		//for each player, create new set of cards
		for (int j=0; j<players.size(); j++) {
			setofCards = new ArrayList<Card>();
			//check if next card is not solution and player still needs more cards
			for (int i=loc; i<deck.size(); i++) {		
				if (!answer.contains(deck.get(i).getName()) && setofCards.size() < cardsPer) {
					setofCards.add(deck.get(i));
					loc++;
				}
			}
			players.get(j).setCards(setofCards);
		}	
	}

	public ArrayList<Card> getDeck() {
		return deck;
	}


	public Card handleSuggestion(String person, String weapon, String room, Player currentPlayer) {
		ArrayList<Card> discards = new ArrayList<Card>();

		for (int i=0; i < players.size(); i++) {
			if (players.get(i) != currentPlayer) {
				Card card = players.get(i).disproveSuggestion(person, weapon, room);
				discards.add(card);
			}
		}
		//if someone has the card shuffle them and return the first card and only the one
		if(discards.size() != 0){
			Collections.shuffle(discards);
			return discards.get(0);
		} else {
			//no one has the card
			return null;
		}
	}

	public static void setPlayers(ArrayList<Player> players) {
		Board.players = players;
	}

	public static void setSolution(Solution conclusion){
		solution = conclusion;
	}

	public Solution getSolution() {
		return solution;
	}

}

