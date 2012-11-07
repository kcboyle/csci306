package DetectiveNotesGUI;
import javax.swing.*;
import javax.swing.border.*;

import Board.Board;
import Board.Card.CardType;

public class RoomGuessPanel extends JPanel{
		private Board board;
		private JComboBox room;
		RoomGuessPanel(Board board) {
			this.board = board;
			setBorder(new TitledBorder (new EtchedBorder(), "Weapon Guess"));
			setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS)); 		//makes big button
			room = createWeaponCombo();
			add(room);
		}
		private JComboBox createWeaponCombo()
		{
			JComboBox combo = new JComboBox();
			for (int i = 0; i < board.getDeck().size(); ++i) {
				if (board.getDeck().get(i).getCardtype() == CardType.ROOM) {
					combo.addItem(board.getDeck().get(i).getName());
				} 
			}
			return combo;
		}
		

}
