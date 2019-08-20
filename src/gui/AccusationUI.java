package gui;

import java.awt.Dimension;
import java.awt.Window;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import board.token.Player;
import game.Game;
import javax.swing.JLabel;
import java.awt.Font;

public class AccusationUI extends JDialog {

	private Player currentPlayer;
	private Map<String, String> playerNames;
	private Game game;
	
	private JLabel accusationResultLabel ;
	private JLabel playerOutcomeLabel;
	private JButton newGameButton;
	
	
	/**
	 * Create the dialog.
	 */
	public AccusationUI(ActionUI actionUI, Window window, String title, boolean correct, Player currentPlayer, Game game, Map<String, String> playerNames) {
		super(window, title);
		
		this.currentPlayer = currentPlayer;
		this.playerNames = playerNames;
		this.game = game;
		
		setResizable(false);
		setModal(true);
		setMinimumSize(new Dimension(350, 250));
		getContentPane().setMinimumSize(new Dimension(200, 300));
		setBounds(100, 100, 300, 218);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(actionUI);
        getContentPane().setLayout(null);
        
        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(l -> {
        	this.dispose();
        	actionUI.dispose();
        });
        btnClose.setBounds(118, 179, 102, 31);
        getContentPane().add(btnClose);
        
        accusationResultLabel = new JLabel("New label");
        accusationResultLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        accusationResultLabel.setBounds(90, 11, 225, 24);
        getContentPane().add(accusationResultLabel);
        
        playerOutcomeLabel = new JLabel("New label");
        playerOutcomeLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
        playerOutcomeLabel.setBounds(118, 74, 216, 24);
        getContentPane().add(playerOutcomeLabel);
        
        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(l -> {
        	dispose();
        	actionUI.dispose();
        	actionUI.getFrame().dispose();
			new Game();
			setupPlayers(actionUI);
        });
        newGameButton.setBounds(118, 137, 102, 31);
        getContentPane().add(newGameButton);
        
        if(!correct) {
        	newGameButton.setEnabled(false);
        }
        
        
        showAccusationResult(correct);
        
        
        
        this.setVisible(true);
	}
	
	
	/**
	 * Sets the labels depending of the player won by getting the 
	 * correct accusation or by being the last remaining player, or
	 * if the player lost by getting the incorrect accusation
	 * 
	 * @param correct - if the accusation was correct
	 */
	private void showAccusationResult(boolean correct) {
		if(!correct) {
			accusationResultLabel.setText("Incorrect Accusation!");
			playerOutcomeLabel.setText(playerNames.get(currentPlayer.getName()) + " is out");
		
		} else {
			if(game.getPlayers().size() == 1) {
				accusationResultLabel.setText("Only " + game.getPlayers().get(0).getName() + " remains!");
				playerOutcomeLabel.setText(playerNames.get(game.getPlayers().get(0).getName()) + " has won");
				
			} else {
				accusationResultLabel.setText("Correct Accusation!");
				playerOutcomeLabel.setText(playerNames.get(currentPlayer.getName()) + " has won");
			}
		}
	}
	
	
	public void setupPlayers(ActionUI actionUI) {
        new PlayerSetupUI(actionUI.getFrame(), SwingUtilities.windowForComponent(actionUI.getFrame()),
                "Character Selection");
    }
}
