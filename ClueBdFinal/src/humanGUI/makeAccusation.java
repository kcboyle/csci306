/**
 * Kira Combs
 * Maria Deslis
 */

import javax.swing.*;

package humanGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;

import main.Board;


class makeAccusation extends JFrame {
	//Basic GUI Setup
	public makeAccusation() {
		
		//creates a Board
			Board board = new Board ("roomLegend.txt", "config.txt", "playsers.csv", "cards.csv");
			
			setSize(new Dimension(300,200));
			setTitle("Gandalf is making an accusation!");
			
	}
	
	
	//Creating File > Exit Menu for the GUI
		private JMenu createFileMenu() {
			JMENU menu = new JMenu("File");
			menu.add(createFileExitItem());
			return menu;
		}
		
	//Implementing createFileExitItem
		private JMenuItem exit = new JMenuItem("Exit");
		
		class MenuItemListener implements ActionListener {
			public void actionPerformed(Action e) {
				setVisible(false); //hides makeAccusation until it is called
			}
		}
		
		item.addActionListener(new MenuItemListener());
		return item;
		
	}
	
	/**
	 * @param args
	 public static void main(String[] args) {
	 	//Setting up basic items in JFrame		
	 		makeAccusation gui = new makeAccusation();
	 		
	 		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 		gui.setVisible(true);
	 	}*/