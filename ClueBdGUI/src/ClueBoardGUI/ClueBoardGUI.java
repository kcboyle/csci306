package ClueBoardGUI;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import java.awt.BorderLayout;
import javax.swing.*;

import Board.Board;

public class ClueBoardGUI extends JFrame {
	private DrawBoard drawBoard;
	private int dx, dy;
	public ClueBoardGUI(){
		Board board = new Board();
		setSize(new Dimension(700,700));
		setTitle("Clue Game");
		drawBoard = new DrawBoard();
		// paintComponent will automatically be 
		// called 1 time
		add(drawBoard, BorderLayout.CENTER);
		//menu
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
	}
	public void updateDrawing(int dx, int dy)
	{
		this.dx = dx; // need to add instance variables
		this.dy = dy;
	}
	
	//Creating File > Exit Menu for the GUI
	private JMenu createFileMenu(){
		JMenu menu = new JMenu("File");
		menu.add(createFileExitItem());
		return menu;
	}
	private JMenuItem createFileExitItem(){
		JMenuItem item = new JMenuItem("Exit");
		
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Setting up basic items in JFrame
		ClueBoardGUI gui = new ClueBoardGUI();
		
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setVisible(true);
		 // This will cause rectangle to display in new location
		gui.updateDrawing(100, 100);

	}

}
