package gui;

import java.awt.Dimension;
import java.awt.Window;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import board.token.Player;
import cards.Card;
import game.Game;
import game.Suggestion;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;

public class RefutationUI extends JDialog {

	private Player currentPlayer;
	Map<String, String> playerNames;
	private Game game;
	private Player currentRefutor;
	private Suggestion action;
	private ActionUI actionUI;
	
	private JComboBox<String> comboBox;
	private JLabel titleLabel;

	/**
	 * Create the dialog.
	 */
	public RefutationUI(JFrame frame, Window window, String title, Player currentPlayer, Game game, Suggestion action, ActionUI actionUI, Map<String, String> playerNames) {
		super(window, title);
		
		this.action = action;
		this.currentPlayer = currentPlayer;
		this.game = game;
		this.actionUI = actionUI;
		this.playerNames = playerNames;
		
		// set inital refutor
		List<Player> refutors = setRefutors();
		for(int i = 0; i < refutors.size(); i++) {
			if(refutors.get(i).canRefute(action)) {
				currentRefutor = refutors.get(i);
				break;
			}
		}
		
		
		setBounds(100, 100, 450, 300);
		setMinimumSize(new Dimension(500, 275));
		this.setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(frame);
        getContentPane().setLayout(null);
        
        titleLabel = new JLabel("");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        titleLabel.setBounds(197, 11, 184, 28);
        titleLabel.setText(playerNames.get(currentRefutor.getName()));
        getContentPane().add(titleLabel);
        
        JPanel cardSelectPanel = new JPanel();
        cardSelectPanel.setBounds(140, 78, 195, 109);
        getContentPane().add(cardSelectPanel);
        cardSelectPanel.setLayout(null);
        
        comboBox = new JComboBox<>();
        comboBox.setBounds(40, 50, 114, 20);
        cardSelectPanel.add(comboBox);
        
        JLabel lblSelectCard = new JLabel("Select Card");
        lblSelectCard.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblSelectCard.setBounds(56, 11, 114, 28);
        cardSelectPanel.add(lblSelectCard);
        
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(l -> {
        	this.setVisible(false);
        	actionUI.addRefutationCards(comboBox.getSelectedItem().toString());
        	Player next = getNextRefutor(refutors);
        	if(next != null && next.canRefute(action)) {
        		new RefutationUI(frame, SwingUtilities.windowForComponent(this), next.getName(), currentPlayer, game, action, actionUI, playerNames);
        	}
        });
        submitButton.setBounds(188, 198, 89, 37);
        getContentPane().add(submitButton);
        this.setTitle(currentRefutor.getName());
        
        setupComboBox();
        
        this.setVisible(true);
	}
	

	/**
	 * Get the next refutor from a list of refutors
	 * 
	 * @param refutors - the list of refutors
	 * @return - the next refutor
	 */
	private Player getNextRefutor(List<Player> refutors) {
		refutors.remove(currentRefutor);
		if(refutors.size() == 0) {
			return null;
		}
		
		return refutors.get(0);
		
	}
	
	
	/**
	 * sets up the combo box
	 */
	private void setupComboBox() {
		List<Card> hand = currentRefutor.getHand();
		
		for(Card card : hand) {
			if(isRefutation(card)) {
				comboBox.addItem(card.getName());
			}
			
		}
	}
	
	
	/**
	 * Checks if the card matches the refutation cards
	 * 
	 * @param card - the card to check
	 * @return - boolean
	 */
	private boolean isRefutation(Card card) {
		return (card.equals(action.getCharacterCard())
				|| card.equals(action.getRoomCard())
				|| card.equals(action.getWeaponCard()));
			
	}

	
	
	/**
	 * removes the player who made the suggestion 
	 * 
	 * @return - the list of refutors
	 */
	private List<Player> setRefutors(){
		List<Player> refutors = actionUI.getRefutors();
		System.out.println("PlayersB: " + game.getPlayers().size());
		System.out.println("refutorsB: " + refutors.size());
		refutors.remove(currentPlayer);
		System.out.println("PlayersA: " + game.getPlayers().size());
		System.out.println("refutorsA: " + refutors.size());
		return refutors;
	}
	
	

}
