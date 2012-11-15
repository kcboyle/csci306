package DetNotesGUI;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import main.Board;
import main.Card.CardType;


public class YourRoomPanelAccusation extends JPanel{
	private Board board;
	private JComboBox room;
	YourRoomPanelAccusation(Board board) {
		this.board = board;
		setBorder(new TitledBorder (new EtchedBorder(), "Room Guess"));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS)); 		//makes big button
		room = createPersonCombo();
		add(room);
	}
	private JComboBox createPersonCombo()
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
