package ClueBoardGUI;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import DetNotesGUI.makeAccusation;


import main.Board;

public class WestPanel extends JPanel{
	static Board board;
	JPanel diceRollPanel = new JPanel();
	JLabel diceRoll = new JLabel();
	JLabel currentPlayer = new JLabel();
	static JLabel guess = new JLabel();
	static JLabel dis = new JLabel();
	public WestPanel(Board board) {
		this.board = board;
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		//displays the current player, lots of fluff in looks...
		JPanel currentPlayerPanel = new JPanel();
		currentPlayerPanel.setBorder(new TitledBorder (new EtchedBorder(), "Current Player"));
		currentPlayer.setFont(new Font("Chiller", Font.BOLD, 25));
		resetCurrentPlayer();
		currentPlayerPanel.add(currentPlayer);
		add(currentPlayerPanel);
		
		//displays the current dice roll
		diceRollPanel.setBorder(new TitledBorder (new EtchedBorder(), "Die"));
		diceRoll.setFont(new Font("Chiller", Font.BOLD, 25));
		resetDice();
		diceRollPanel.add(diceRoll);
		add(diceRollPanel);
		
		//displays next player button
		JButton nextPlayerButton = new JButton("Next Player");
		add(nextPlayerButton);
		nextPlayerButton.setFont(new Font("Chiller", Font.BOLD, 25));
		nextPlayerButton.addActionListener(new PlayerButtonListener()); //should do nothing until implemented
		
		//displays make accusation button
		JButton makeAccusationButton = new JButton("Make Accusation");
		add(makeAccusationButton);
		makeAccusationButton.setFont(new Font("Chiller", Font.BOLD, 25));
		makeAccusationButton.addActionListener(new AccusationButtonListener()); //should do nothing until implemented
		
		//displays Last Guess
		JPanel lastGuessPanel = new JPanel();
		lastGuessPanel.setBorder(new TitledBorder (new EtchedBorder(), "Last Guess "));
		for(int i = 0; i < board.getSuggestions().size(); ++i) {
			//default values just say person, room, weapon. in board setup
			System.out.println(board.getSuggestions().get(i) + ", ");
			guess.setFont(new Font("Chiller", Font.BOLD, 20));
			resetLastGuess();
			lastGuessPanel.add(guess);
		}
		add(lastGuessPanel);
		
		//displays disprovement of last guess
		JPanel guessResultPanel = new JPanel();
		guessResultPanel.setBorder(new TitledBorder (new EtchedBorder(), "Previous Disproval "));
		System.out.println(board.getCardShown());
		dis.setFont(new Font("Chiller", Font.BOLD, 25));	
		resetLastDisprovement();
		guessResultPanel.add(dis);		
		add(guessResultPanel);
		
		
	}
	public void resetDice() {
		diceRoll.setText("Current Roll: " + board.getDiceRoll());
	}
	public void resetCurrentPlayer() {
		currentPlayer.setText(board.getCurrentPlayer().getName());
	}
	//reset the lastguess and lastdiprovement
	public static void resetLastGuess() {
		if (board.getSuggestions().size() > 0) {
			guess.setText(board.getSuggestions().get(0) + ", " + board.getSuggestions().get(1) + ", " + board.getSuggestions().get(2));
		}
	}
	public static void resetLastDisprovement() {
		dis.setText(board.getCardShown());
		if (board.getCardShown() == null) {
			dis.setText("No current card shown");
		}
	}
	public void changeLocation() {
		//move the player accused in the selection to the player's current room
		String playerName;
		for (int j = 0; j < board.getSuggestions().size(); ++j) {
			playerName = board.getSuggestions().get(j);
			for (int i = 0; i < board.getAllPlayers().size(); ++i) {
				if (board.getAllPlayers().get(i).getName().equals(playerName)) {
					board.getAllPlayers().get(i).setCol(board.getCurrentPlayer().getCol());
					board.getAllPlayers().get(i).setRow(board.getCurrentPlayer().getRow());
					board.getAllPlayers().get(i).setCurrentLocation(board.getCurrentPlayer().getCurrentLocation());
				}
			}
		}	
	}
	
	//creating makeAccusationButton listener
	private class AccusationButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (board.getCurrentPlayer() == board.getSelf()) {
				DetNotesGUI.makeAccusation humanAccusation = new DetNotesGUI.makeAccusation(board);
				humanAccusation.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(null, "YOU SHALL NOT MOVE! *whacks*");
			}
		}
	}
	
	
	//Player button listener for use to make the buttons work!! mwaha 
	private class PlayerButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			//displays a humanGuess on the click of nextTurn
			resetLastGuess();
			resetLastDisprovement();
			if (board.getSubmissionComplete()) {
				if (board.isPlayerSelTarget()) {
					board.nextMove();
					changeLocation();
					resetDice();
					resetCurrentPlayer();
					repaint();
				
				} else {
					JOptionPane.showMessageDialog(null, "At least move....exercise is good for you!");
				}
			} 
		
		}
	}
}
