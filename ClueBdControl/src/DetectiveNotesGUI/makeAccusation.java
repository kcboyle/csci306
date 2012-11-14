/**
 * Kira Combs
 * Maria Deslis
 */
package DetectiveNotesGUI;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.*;


import main.Board;

public class makeAccusation extends JDialog {
	//Basic GUI Setup
	Board board;
	String room, weapon, person;
	public makeAccusation(Board board) {
		this.board = board;
		setSize(new Dimension(300,200));
		setTitle(board.getCurrentPlayer().getName() + " is making an accusation!");
		
		//create accusation drop boxes
		setLayout(new GridLayout(5,2));
		YourRoomPanel yrPanel = new YourRoomPanel(board);
		add(yrPanel);
		PersonGuessPanel pgPanel = new PersonGuessPanel(board);
		add(pgPanel);
		WeaponGuessPanel wgPanel = new WeaponGuessPanel(board);
		add(wgPanel);
		
		//submit and cancel buttons
		
		JButton submit = new JButton("Submit");
		add(submit);
		submit.addActionListener(new SubmitListener());
		JButton cancel = new JButton("Cancel");
		add(cancel);
		cancel.addActionListener(new CancelListener());

		//menu
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
	}
	
	//Creating File > Exit Menu for the GUI
	private JMenu createFileMenu(){
		JMenu menu = new JMenu("File");
		menu.add(createFileExitItem());
		return menu;
	}
	
	private JMenuItem createFileExitItem(){
		JMenuItem item = new JMenuItem("NeverMind, I don't want to make an accusation");
		
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);							//hides the detective notes
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}
	private class CancelListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
		}
	}
	private class SubmitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (!(room.equals("")) && !(weapon.equals("")) && !(person.equals(""))) {
				ArrayList<String> a = new ArrayList<String>();
				a.add(room);
				a.add(person);
				a.add(weapon);
				board.setAccusations(a);
				board.setWon();
				if (board.isWon() == false) {
					JOptionPane.showMessageDialog(null, "Sorry, That is incorrect.");
					setVisible(false);
				}
			} else {
				JOptionPane.showMessageDialog(null, "Incomplete Accusation.");
			}
		}
	}
	
}