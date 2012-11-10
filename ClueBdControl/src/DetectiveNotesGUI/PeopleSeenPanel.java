/**
 * Kira Combs
 * Maria Deslis
 */
package DetectiveNotesGUI;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import main.Board;
import main.Card.CardType;

public class PeopleSeenPanel extends JPanel {
	private Board board;
	
	public PeopleSeenPanel(Board board)
	{
		this.board = board;
		setLayout(new GridLayout(3, 2));
		setBorder(new TitledBorder (new EtchedBorder(), "People"));
		for (int i = 0; i < board.getAllCards().size(); ++i) {
			if (board.getAllCards().get(i).getCardType() == CardType.PERSON) {
				add(new JCheckBox(board.getAllCards().get(i).getName()));
			}
		}
	}
}