package DetectiveNotesGUI;

import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import Board.Board;
import Board.Card.CardType;

public class PersonGuessPanel extends JPanel{
	private Board board;
	private JComboBox person;
	PersonGuessPanel(Board board) {
		this.board = board;
		setBorder(new TitledBorder (new EtchedBorder(), "Person Guess"));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS)); 		//makes big button
		person = createPersonCombo();
		add(person);
	}
	private JComboBox createPersonCombo()
	{
		JComboBox combo = new JComboBox();
		for (int i = 0; i < board.getDeck().size(); ++i) {
			if (board.getDeck().get(i).getCardtype() == CardType.PERSON) {
				combo.addItem(board.getDeck().get(i).getName());
			} 
		}
		return combo;
	}
}
