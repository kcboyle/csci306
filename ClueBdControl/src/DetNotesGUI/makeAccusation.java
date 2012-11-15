/**
 * Kira Combs
 * Maria Deslis
 */
package DetNotesGUI;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import main.Board;
import main.Card.CardType;



public class makeAccusation extends JFrame {
	//Basic GUI Setup
	Board board;
	private String room, weapon, person;

	private JComboBox roomBox, personBox, weaponBox;
	public makeAccusation(Board board) {
		this.board = board;
		setSize(new Dimension(200,500));
		setTitle(board.getCurrentPlayer().getName() + " is making an accusation!");
		setLayout(new GridLayout(5, 1)); 		
		JPanel roomPanel = new JPanel();
		roomPanel.setBorder(new TitledBorder (new EtchedBorder(), "Room Guess"));
		roomBox = createRoomCombo();
		roomPanel.add(roomBox);
		add(roomPanel);

		JPanel personPanel = new JPanel();
		personPanel.setBorder(new TitledBorder (new EtchedBorder(), "Person Guess"));
		personBox = createPersonCombo();
		personPanel.add(personBox);
		add(personPanel);

		JPanel weaponPanel = new JPanel();
		weaponPanel.setBorder(new TitledBorder (new EtchedBorder(), "Weapon Guess"));
		weaponBox = createWeaponCombo();
		weaponPanel.add(weaponBox);
		add(weaponPanel);

		JButton submit = new JButton("Submit");
		JButton cancel = new JButton("Cancel");
		add(submit);
		add(cancel);

		cancel.addActionListener(new CancelListener());
		submit.addActionListener(new SubmitListener());
	}

	private JComboBox createWeaponCombo()
	{
		JComboBox combo = new JComboBox();
		combo.addItem("Pick a weapon");
		for (int i = 0; i < board.getAllCards().size(); ++i) {
			if (board.getAllCards().get(i).getCardType() == CardType.WEAPON) {
				combo.addItem(board.getAllCards().get(i).getName());
			} 
		}
		return combo;
	}

	private JComboBox createPersonCombo()
	{
		JComboBox combo = new JComboBox();
		combo.addItem("Pick a person");
		for (int i = 0; i < board.getAllCards().size(); ++i) {
			if (board.getAllCards().get(i).getCardType() == CardType.PERSON) {
				combo.addItem(board.getAllCards().get(i).getName());
			} 
		}
		return combo;
	}
	private JComboBox createRoomCombo()
	{
		JComboBox combo = new JComboBox();
		combo.addItem("Pick a room");
		for (int i = 0; i < board.getAllCards().size(); ++i) {
			if (board.getAllCards().get(i).getCardType() == CardType.ROOM) {
				combo.addItem(board.getAllCards().get(i).getName());
			} 
		}
		return combo;
	}

	private class CancelListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
		}
	}
	private class SubmitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			room = roomBox.getSelectedItem().toString();
			weapon = weaponBox.getSelectedItem().toString();
			person = personBox.getSelectedItem().toString();
			if (!(room.equals("Pick a room")) && !(weapon.equals("Pick a weapon")) && !(person.equals("Pick a person"))) {
				ArrayList<String> a = new ArrayList<String>();
				a.add(weapon);
				a.add(person);
				a.add(room);
				board.setAccusations(a);
				board.setWon();
				setVisible(false);
			} else {
				JOptionPane.showMessageDialog(null, "FAILED ACCUSATION. Ensure 3 choices made");
				board.setSubmissionComplete(false);
			}
		}
	}
};