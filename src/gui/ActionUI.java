package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Window;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import board.Room;
import board.token.Player;
import cards.Card;
import cards.CharacterCard;
import cards.RoomCard;
import cards.WeaponCard;
import game.Game;
import game.Suggestion;

import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ActionUI extends JDialog {
	
	private Player currentPlayer;
	private Map<String, String> playerNames;
	private Game game;
	private Suggestion action;
	private List<String> refutationCards;
	private List<Player> refutors;
	private RefutationUI refutationUI;
	private boolean correct;
	private BoardUI boardUI;
	
	private JFrame frame;
	private JComboBox<String> characterComboBox;
	private JComboBox<String> roomComboBox;
	private	JComboBox<String> weaponComboBox;
	private JButton viewRefutationButton;
	
	
	
	/**
	 * Create the dialog.
	 */
	public ActionUI(JFrame frame, Window window, String title, Player currentPlayer, Game game, Map<String, String> playerNames, boolean isSuggestion, BoardUI boardUI) {
		super(window, title);
		
		this.frame = frame;
		this.currentPlayer = currentPlayer;
		this.game = game;
		this.refutationCards = new ArrayList<>();
		this.playerNames = playerNames;
		this.boardUI = boardUI;
		
		refutors = new ArrayList<>();
		refutors.addAll(game.getPlayers());
		
		setMinimumSize(new Dimension(500, 300));
		setBounds(100, 100, 500, 282);
		this.setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(frame);
        getContentPane().setLayout(null);
        
        JLabel lblNewLabel = new JLabel(title);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
        lblNewLabel.setBounds(188, 11, 209, 38);
        getContentPane().add(lblNewLabel);
        
        // panel for character card selection
        JPanel characterPanel = new JPanel();
        characterPanel.setBounds(10, 52, 148, 104);
        getContentPane().add(characterPanel);
        characterPanel.setLayout(null);
        
        JLabel lblCharacterCard = new JLabel("Character Card");
        lblCharacterCard.setBounds(20, 11, 102, 14);
        characterPanel.add(lblCharacterCard);
        lblCharacterCard.setFont(new Font("Tahoma", Font.PLAIN, 14));
        
        characterComboBox = new JComboBox<>();
        characterComboBox.setBounds(10, 50, 128, 20);
        characterPanel.add(characterComboBox);
        
        // panel for room card selection
        JPanel roomPanel = new JPanel();
        roomPanel.setBounds(168, 52, 153, 104);
        getContentPane().add(roomPanel);
        roomPanel.setLayout(null);
        
        JLabel lblRoomCard = new JLabel("Room Card");
        lblRoomCard.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblRoomCard.setBounds(31, 11, 86, 14);
        roomPanel.add(lblRoomCard);
        
        roomComboBox = new JComboBox<>();
        roomComboBox.setBounds(10, 52, 133, 20);
        roomPanel.add(roomComboBox);
        
        // panel for weapon card selection
        JPanel weaponPanel = new JPanel();
        weaponPanel.setBounds(331, 52, 153, 104);
        getContentPane().add(weaponPanel);
        weaponPanel.setLayout(null);
        
        JLabel lblWeaponCard = new JLabel("Weapon Card");
        lblWeaponCard.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblWeaponCard.setBounds(36, 11, 90, 17);
        weaponPanel.add(lblWeaponCard);
        
        weaponComboBox = new JComboBox<>();
        weaponComboBox.setBounds(10, 51, 133, 20);
        weaponPanel.add(weaponComboBox);
        
        JButton actionSubmitButton = new JButton("Submit");
        actionSubmitButton.addActionListener(l -> {
        	if(isSuggestion()) {
	        	createAction(true);
	        	doRefutation("Refutation");
	        	game.getBoard().movePlayerToRoom(action.getCharacterCard().getToken(game.getPlayers()), (Room) currentPlayer.getLocation(), game.getPlayers());
	            game.getBoard().moveWeaponToRoom(action.getWeaponCard().getToken(game.getWeapons()), (Room) currentPlayer.getLocation());
	            
	        	actionSubmitButton.setEnabled(false);
	        	viewRefutationButton.setEnabled(true);
        	
        	} else {
        		createAction(false);
        		correct = game.doAccusation(action, currentPlayer);

        		if(!correct) {
        			currentPlayer.getLocation().setBoardToken(null);
        			currentPlayer.getLocation().setAccessible(true);
        			doAction("Accusation", false);
        		
        		} else {
        			doAction("Accusation", true);
        		}
        		
        	}
        });
        actionSubmitButton.setBounds(178, 167, 133, 38);
        getContentPane().add(actionSubmitButton);
        
        viewRefutationButton = new JButton("View Refutation");
        viewRefutationButton.addActionListener(l -> {
        	doViewRefutation("Refutation Cards");
        	
        });
        viewRefutationButton.setBounds(178, 222, 133, 38);
        getContentPane().add(viewRefutationButton);
        viewRefutationButton.setEnabled(false);
        
        setupComboBoxes(isSuggestion);
        
        
        this.setVisible(true);
	}
	
	
	/**
	 * sets up the combo boxes
	 */
	private void setupComboBoxes(boolean isSuggestion) {
		List<Card> hand = currentPlayer.getHand();
		
		for(Card card : game.getAllCards()) {
			if(!hand.contains(card)) {
				if(card instanceof CharacterCard) {
					characterComboBox.addItem(card.getName());
				
				} else if(card instanceof RoomCard) {
					if(!isSuggestion) {
						roomComboBox.addItem(card.getName());
					}
				
				} else if(card instanceof WeaponCard) {
					weaponComboBox.addItem(card.getName());
				}
			}
		}
		
		// if a suggestion then only add the room they are in
		if(isSuggestion) {
			roomComboBox.addItem(currentPlayer.getLocation().getName());
		}
	}
	
	
	/**
	 * creates a new action
	 * 
	 * @param isSuggestion - if action is a suggestion or accusation
	 */
	private void createAction(boolean isSuggestion) {
		String character = (String) characterComboBox.getSelectedItem();
		String room = (String) roomComboBox.getSelectedItem();
		String weapon = (String) weaponComboBox.getSelectedItem();
		
		
		CharacterCard cc = (CharacterCard) currentPlayer.getCardByName(character, game.getAllCards());
		RoomCard  rc = (RoomCard) currentPlayer.getCardByName(room, game.getAllCards());
		WeaponCard wc = (WeaponCard) currentPlayer.getCardByName(weapon, game.getAllCards());

        action = new Suggestion(cc, rc, wc, isSuggestion);
	}
	
	

	public Suggestion getSuggestion() {
		return action;
	}
	
	public void addRefutationCards(String cardName) {
		refutationCards.add(cardName);
	}
	
	
	public void doRefutation(String title) {
        refutationUI = new RefutationUI(frame, SwingUtilities.windowForComponent(this), title, currentPlayer, game, action, this, playerNames);
    } 
	
	public void doViewRefutation(String title) {
        new ViewRefutationCardsUI(frame, SwingUtilities.windowForComponent(this), title, this, refutationUI);
    } 


	public List<String> getRefutationCards() {
		return refutationCards;
	}
	
	public List<Player> getRefutors(){
		return refutors;	
	}
	
	public void setRefutors(List<Player> refutors) {
		this.refutors = refutors;
	}
	
	public boolean isSuggestion() {
		return getTitle().equals("Suggestion");
	}
	
	public boolean getCorrect() {
		return correct;
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public BoardUI getBoardUI() {
		return boardUI;
	}
	
	
	public void doAction(String title, boolean correct) {
        new AccusationUI(this, SwingUtilities.windowForComponent(this), title, correct, currentPlayer, game, playerNames);
    }
}



















