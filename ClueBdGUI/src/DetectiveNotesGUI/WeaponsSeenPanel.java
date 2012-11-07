package DetectiveNotesGUI;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import Board.Board;
import Board.Card.CardType;

public class WeaponsSeenPanel extends JPanel {
	private Board board;
	
	public WeaponsSeenPanel(Board board)
	{
		this.board = board;
		setLayout(new GridLayout(3,3));
		setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		for (int i = 0; i < board.getDeck().size(); ++i) {
			if (board.getDeck().get(i).getCardtype() == CardType.WEAPON) {
				add(new JCheckBox(board.getDeck().get(i).getName()));
			}
		}
	}
}