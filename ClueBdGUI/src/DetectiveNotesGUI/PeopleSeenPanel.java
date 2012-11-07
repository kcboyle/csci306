package DetectiveNotesGUI;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import Board.Board;
import Board.Card.CardType;

public class PeopleSeenPanel extends JPanel {
	private Board board;
	
	public PeopleSeenPanel(Board board)
	{
		this.board = board;
		setLayout(new GridLayout(3, 2));
		setBorder(new TitledBorder (new EtchedBorder(), "People"));
		for (int i = 0; i < board.getDeck().size(); ++i) {
			if (board.getDeck().get(i).getCardtype() == CardType.PERSON) {
				add(new JCheckBox(board.getDeck().get(i).getName()));
			}
		}
	}
}