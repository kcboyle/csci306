package DetectiveNotesGUI;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import main.Board;
import main.Card.CardType;

public class RoomsSeenPanel extends JPanel {
	private Board board;
	
	public RoomsSeenPanel(Board board)
	{
		this.board = board;
		setLayout(new GridLayout(3,3));
		setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		for (int i = 0; i < board.getAllCards().size(); ++i) {
			if (board.getAllCards().get(i).getCardType() == CardType.ROOM) {
				add(new JCheckBox(board.getAllCards().get(i).getName()));
			}
		}
	}
}