/**
 * Kira Combs
 * Maria Deslis
 */
package DetectiveNotesGUI;
import javax.swing.*;
import javax.swing.border.*;

import main.Board;
import main.Card.CardType;

public class RoomGuessPanel extends JPanel{
		private Board board;
		private JComboBox room;
		RoomGuessPanel(Board board) {
			this.board = board;
			setBorder(new TitledBorder (new EtchedBorder(), "Room Guess"));
			setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS)); 		//makes big button
			room = createWeaponCombo();
			add(room);
		}
		private JComboBox createWeaponCombo()
		{
			JComboBox combo = new JComboBox();
			for (int i = 0; i < board.getAllCards().size(); ++i) {
				if (board.getAllCards().get(i).getCardType() == CardType.ROOM) {
					combo.addItem(board.getAllCards().get(i).getName());
				} 
			}
			return combo;
		}
		

}
