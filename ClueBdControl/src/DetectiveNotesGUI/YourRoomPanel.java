package DetectiveNotesGUI;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import main.Board;
import main.Card.CardType;

public class YourRoomPanel extends JPanel{
	private Board board;
	private char room;
	private String roomStr;
	YourRoomPanel(Board board) {
		this.board = board;
		setBorder(new TitledBorder (new EtchedBorder(), "Your Current Room"));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS)); 		//makes big button
		room = board.getCurrentPlayer().getLastRoomVisited();
		roomStr = Character.toString(room);
		JLabel roomLabel = new JLabel("Closet");
		add(roomLabel);
	}

}
