/**
 * Kira Combs
 * Maria Deslis
 */
package DetNotesGUI;
import javax.swing.*;
import javax.swing.border.*;

import main.Board;
import main.Card.CardType;

public class WeaponGuessPanel extends JPanel{
		private Board board;
		private JComboBox weapon;
		WeaponGuessPanel(Board board) {
			this.board = board;
			setBorder(new TitledBorder (new EtchedBorder(), "Weapon Guess"));
			setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS)); 		//makes big button
			weapon = createWeaponCombo();
			add(weapon);
		}
		private JComboBox createWeaponCombo()
		{
			JComboBox combo = new JComboBox();
			for (int i = 0; i < board.getAllCards().size(); ++i) {
				if (board.getAllCards().get(i).getCardType() == CardType.WEAPON) {
					combo.addItem(board.getAllCards().get(i).getName());
				} 
			}
			return combo;
		}
		

}
