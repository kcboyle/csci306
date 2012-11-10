package ClueBoardGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import main.Board;

public class WestPanel extends JPanel{
	public WestPanel(Board board) {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		//displays the current player, lots of fluff in looks...
		JPanel currentPlayerPanel = new JPanel();
		currentPlayerPanel.setBorder(new TitledBorder (new EtchedBorder(), "Current Player"));
		JLabel currentPlayer = new JLabel(board.getCurrentPlayer().getName());
		currentPlayer.setFont(new Font("Chiller", Font.BOLD, 25));
		currentPlayerPanel.add(currentPlayer);
		add(currentPlayerPanel);
		
		//displays the current dice roll
		JPanel diceRollPanel = new JPanel();
		String die = Integer.toString(board.getDiceRoll());
		diceRollPanel.setBorder(new TitledBorder (new EtchedBorder(), "Die"));
		JLabel diceRoll = new JLabel("Current Roll: " + die);
		diceRoll.setFont(new Font("Chiller", Font.BOLD, 25));
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
	//Player button listener for use to make the buttons work!! mwaha 
	private class PlayerButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
		}
	}
	//Accusation button listener
		private class AccusationButtonListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			}
		}
}
