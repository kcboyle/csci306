package ClueBoardGUI;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.*;

import main.Board;
import main.Card;
import main.Card.CardType;

public class InHandPanel extends JPanel {
	//variables for listing the player's cards, if more than 24 cards, must add more variables
	//in this implementation, each player will have 3 cards	

	public InHandPanel(Board board) {
		ArrayList<Card> playerCards = board.getSelf().getCards();
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		JPanel people = new JPanel();
		people.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		//people.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		JPanel weapons = new JPanel();
		weapons.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		//weapons.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		JPanel rooms = new JPanel();
		rooms.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		//rooms.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		for (int i = 0; i < playerCards.size(); ++i) {
			if(playerCards.get(i).getCardType() == CardType.PERSON) {
				people.add(new JLabel("( * )" + playerCards.get(i).getName()));
			} else if(playerCards.get(i).getCardType() == CardType.WEAPON) {
				weapons.add(new JLabel("( * )" + playerCards.get(i).getName()));
			} else if(playerCards.get(i).getCardType() == CardType.ROOM) {
				rooms.add(new JLabel("( * )" + playerCards.get(i).getName()));
			} else {
				people.add(new JLabel("null"));		//should never print unless error in loop
			}
		}
		add(people);
		add(weapons);
		add(rooms);
	}
}