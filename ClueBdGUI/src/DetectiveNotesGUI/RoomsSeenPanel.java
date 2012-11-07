package DetectiveNotesGUI;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import Board.Board;
import Board.Card.CardType;

public class RoomsSeenPanel extends JPanel {
	private Board board;
	
	public RoomsSeenPanel(Board board)
	{
		this.board = board;
		setLayout(new GridLayout(3,3));
		setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		for (int i = 0; i < board.getDeck().size(); ++i) {
			if (board.getDeck().get(i).getCardtype() == CardType.ROOM) {
				add(new JCheckBox(board.getDeck().get(i).getName()));
			}
		}
	}
}