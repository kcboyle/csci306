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
	Board board;
	JPanel diceRollPanel = new JPanel();
	JLabel diceRoll = new JLabel();
	JLabel currentPlayer = new JLabel();
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
			JLabel guess = new JLabel(board.getSuggestions().get(i) + ", ");
			guess.setFont(new Font("Chiller", Font.BOLD, 20));
			lastGuessPanel.add(guess);
		}
		add(lastGuessPanel);
		
		//displays disprovement of last guess
		JPanel guessResultPanel = new JPanel();
		guessResultPanel.setBorder(new TitledBorder (new EtchedBorder(), "Previous Disproval "));
		//disprove suggestion returns null if a bad guess is made, so this box will be empty at startup
		//GRADER: if you want to test this, uncomment return "null" line in disproveSuggestion in board
		JLabel dis = new JLabel(board.disproveSuggestion(board.getSuggestions(), board.getCurrentPlayer()));
		dis.setFont(new Font("Chiller", Font.BOLD, 25));		
		guessResultPanel.add(dis);		
		add(guessResultPanel);
		
		
	}
	public void resetDice() {
		diceRoll.setText("Current Roll: " + board.getDiceRoll());
	}
	public void resetCurrentPlayer() {
		currentPlayer.setText(board.getCurrentPlayer().getName());
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
			if (board.getSubmissionComplete()) {
				if (board.isPlayerSelTarget()) {
					board.nextMove();
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
