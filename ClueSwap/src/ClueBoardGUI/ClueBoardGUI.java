package ClueBoardGUI;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import main.Board;

public class ClueBoardGUI extends JFrame {
	private DrawBoard drawBoard;
	private int dx, dy;
	private int xSize = 700;
	private int ySize = 770;
	public ClueBoardGUI(){
		Board board = new Board("roomLegend.txt", "craigAndLarsConfig.txt", "players.csv", "cards.csv");
		setSize(new Dimension(xSize,ySize));
		setTitle("Clue Game");
		drawBoard = new DrawBoard(board, xSize, ySize-70);
		
		JPanel a = new JPanel();
		JPanel b = new JPanel();
		
		//a.setPreferredSize(new Dimension(10,10));
		//b.setPreferredSize(new Dimension(10,10));
		//add(a, BorderLayout.NORTH);
		//add(b, BorderLayout.EAST);
		// paintComponent will automatically be 
		// called 1 time
		
		add(drawBoard, BorderLayout.CENTER);
		//menu
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
	}
	//will update when player moves
	public void updatePlayer(int dx, int dy)
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
		gui.setResizable(false);
		// This will draw the new location of player
		gui.updatePlayer(100, 100);

	}

}
