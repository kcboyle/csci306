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

public class WeaponsSeenPanel extends JPanel {
	private Board board;
	
	public WeaponsSeenPanel(Board board)
	{
		this.board = board;
		setLayout(new GridLayout(3,3));
		setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		for (int i = 0; i < board.getAllCards().size(); ++i) {
			if (board.getAllCards().get(i).getCardType() == CardType.WEAPON) {
				add(new JCheckBox(board.getAllCards().get(i).getName()));
			}
		}
	}
}