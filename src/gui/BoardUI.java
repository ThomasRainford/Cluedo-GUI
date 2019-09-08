package gui;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import board.Board;
import board.Hallway;
import board.Location;
import board.Room;
import board.Wall;
import board.token.Player;
import board.token.Token;
import cards.Card;
import game.Game;

import java.awt.Dimension;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.JButton;

import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.Font;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class BoardUI extends JFrame {
	
	/* Game logic fields */
	private Game game;
	private Board board;
	private Location[][] locations;
	private int dice_1;
	private int dice_2;
	private List<Player> players;
	private Player currentPlayer;
	private Map<String, String> playerNames;
	private List<Card> currentHand;
	private int playerIndex;
	private int movesRemaining;
	private Map<JLabel, Token> boardTokens;
	
	/* UI fields */
	private ActionUI action;
	private JPanel boardPanel;
	private JPanel contentPane;
	private JPanel playerTokenPanel;
	private JPanel diceOnePanel;
	private JPanel diceTwoPanel;
	private JLabel playerTurnLabel;
	private JButton nextPlayerButton;
	private JPanel dicePanel;
	private JPanel movementPanel;
	private JButton upButton;
	private JButton leftButton;
	private JButton downButton;
	private JButton rightButton;
	private JLabel lblMoveRemaining;
	private JLabel movesRemainingLabel;
	private final ButtonGroup movementButtonGroup = new ButtonGroup();
	private JLabel lblLocationLabel;
	private JLabel locationLabel;
	private JPanel handPanel;
	private JLabel handLabel;
	private JPanel actionPanel;
	private JButton btnSuggestion;
	private JButton btnAccusation;
	
	
	/** Images **/
	private final Image HALL_TILE = loadImage("hall.png");
	private final Image WALL_TILE = loadImage("border.png");
	private final Image ROOM_TILE = loadImage("room.png");
	
	// weapon tokens
	private final Image LEAD_PIPE_TILE = loadImage("lead-pipe.png");
	private final Image ROPE_TILE = loadImage("rope.png");
	private final Image SPANNER_TILE = loadImage("wrench.png");
	private final Image CANDLESTICK_TILE = loadImage("candlestick.png");
	private final Image REVOLVER_TILE = loadImage("gun.png");
	private final Image DAGGER_TILE = loadImage("knife.png");
	
	// character tokens
	private final Image MISS_SCARLETT = loadImage("miss-scarlett.png");
	private final Image MRS_WHITE = loadImage("mrs-white.png");
	private final Image MRS_PEACOCK = loadImage("mrs-peacock.png");
	private final Image COL_MUSTARD = loadImage("col-mustard.png");
	private final Image MR_GREEN = loadImage("mr-green.png");
	private final Image PROF_PLUM = loadImage("prof-plum.png");
	
	// Cards
	
	// Character
	private final Image MISS_SCARLETT_CARD = loadImage("miss-scarlett-card.png");
	private final Image MRS_WHITE_CARD = loadImage("mrs-white-card.png");
	private final Image MRS_PEACOCK_CARD = loadImage("mrs-peacock-card.png");
	private final Image COL_MUSTARD_CARD = loadImage("col-mustard-card.png");
	private final Image MR_GREEN_CARD = loadImage("mr-green-card.png");
	private final Image PROF_PLUM_CARD = loadImage("prof-plum-card.png");
	
	// Weapon
	private final Image DAGGER_CARD = loadImage("knife-card.png");
	private final Image REVOLVER_CARD = loadImage("gun-card.png");
	private final Image CANDLESTICK_CARD = loadImage("candlestick-card.png");
	private final Image SPANNER_CARD = loadImage("wrench-card.png");
	private final Image LEAD_PIPE_CARD = loadImage("lead-pipe-card.png");
	private final Image ROPE_CARD = loadImage("rope-card.png");
	
	// Room
	private final Image STUDY_CARD = loadImage("study-card.png");
	private final Image HALL_CARD = loadImage("hall-card.png");
	private final Image KITCHEN_CARD = loadImage("kitchen-card.png");
	private final Image BALLROOM_CARD = loadImage("ballroom-card.png");
	private final Image DINING_ROOM_CARD = loadImage("diningroom-card.png");
	private final Image LOUNGE_CARD = loadImage("lounge-card.png");
	private final Image BILLIARD_ROOM_CARD = loadImage("billiardroom-card.png");
	private final Image LIBRARY_CARD = loadImage("library-card.png");
	private final Image CONSERVATORY_CARD = loadImage("conservatory-card.png");

	// Dice
	private final Image DICE_1 = loadImage("dice1.png");
	private final Image DICE_2 = loadImage("dice2.png");
	private final Image DICE_3 = loadImage("dice3.png");
	private final Image DICE_4 = loadImage("dice4.png");
	private final Image DICE_5 = loadImage("dice5.png");
	private final Image DICE_6 = loadImage("dice6.png");
	
	
	/**
	 * Create the frame.
	 */
	public BoardUI(Game game, Map<String, String> playerNames) {
		this.setFocusable(true);
		this.game = game;
		this.board = game.getBoard();
		this.playerNames = playerNames;
		
		locations = game.getBoard().getBoard();
		
		setTitle("Cluedo");
		
		setMinimumSize(new Dimension(1100, 825));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1020, 815);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/* board panel */
		boardPanel = new JPanel();
		boardPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JLabel label = (JLabel) boardPanel.getComponentAt(e.getPoint());
				
				if(label == null || boardTokens.get(label) == null) {
					return;
				
				}
			}
		});
		boardPanel.setName("Cluedo");
		boardPanel.setBounds(0, 21, 784, 764);
		contentPane.add(boardPanel);
		boardPanel.setLayout(new GridLayout(25, 24, 0, 0));
		
				
		// setup board
		setupBoard();
		
		/* dice panel */
		dicePanel = new JPanel();
		dicePanel.setBounds(828, 11, 224, 196);
		contentPane.add(dicePanel);
		dicePanel.setLayout(null);
		
		/* label indicating the player who's turn it is */
		playerTurnLabel = new JLabel("");
		playerTurnLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		playerTurnLabel.setBounds(78, 11, 146, 27);
		dicePanel.add(playerTurnLabel);
		diceOnePanel = new JPanel();
		diceOnePanel.setBounds(36, 52, 32, 31);
		dicePanel.add(diceOnePanel);
		diceOnePanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		diceTwoPanel = new JPanel();
		diceTwoPanel.setBounds(122, 52, 32, 31);
		dicePanel.add(diceTwoPanel);
		diceTwoPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		/* moves remaining label */
		movesRemainingLabel = new JLabel("");
		movesRemainingLabel.setBounds(122, 128, 40, 28);
		dicePanel.add(movesRemainingLabel);
		
		/* roll dice button */
		JButton btnRoll = new JButton("Roll");
		btnRoll.setBounds(36, 94, 118, 23);
		btnRoll.addActionListener(l -> {
			rollDice();
			movesRemainingLabel.setText(Integer.toString(movesRemaining));
			nextPlayerButton.setEnabled(true);	
			btnRoll.setEnabled(false);
			if(currentPlayer.getLocation().isRoom()) {
				changeActionEnable(true);
			}
			locationLabel.setText(currentPlayer.getLocation().getName());
			Enumeration<AbstractButton> enumeration = movementButtonGroup.getElements();
			while (enumeration.hasMoreElements()) {
			    enumeration.nextElement().setEnabled(true);
			}
		});
		dicePanel.add(btnRoll);
		
		//Moves Remaining Label
		lblMoveRemaining = new JLabel("Moves Remaining: ");
		lblMoveRemaining.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblMoveRemaining.setBounds(10, 128, 102, 28);
		dicePanel.add(lblMoveRemaining);
		
		//Location Label
		lblLocationLabel = new JLabel("Location:");
		lblLocationLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblLocationLabel.setBounds(10, 167, 57, 17);
		dicePanel.add(lblLocationLabel);
		
		locationLabel = new JLabel("");
		locationLabel.setBounds(77, 167, 93, 17);
		dicePanel.add(locationLabel);
		
		playerTokenPanel = new JPanel();
		playerTokenPanel.setBounds(36, 11, 32, 30);
		playerTokenPanel.setLayout(new GridLayout(1, 0, 0, 0));
		dicePanel.add(playerTokenPanel);
		
		/* player movement panel */
		movementPanel = new JPanel();
		movementPanel.setBounds(828, 613, 200, 138);
		contentPane.add(movementPanel);
		movementPanel.setLayout(null);
		
		// w button
		upButton = new JButton("W");
		
		upButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				movePlayer(0,-1);
				updateBoard();
			}		
		});
		upButton.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				//System.out.println(e);
				if(e.getKeyCode() == KeyEvent.VK_UP) {				
					movePlayer(0,-1);
					updateBoard();
				}
			}
		});
		
		movementButtonGroup.add(upButton);
		upButton.setBounds(73, 11, 52, 48);
		upButton.setEnabled(false);
		movementPanel.add(upButton);
		
		// a button
		leftButton = new JButton("A");
		movementButtonGroup.add(leftButton);
		leftButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				movePlayer(-1,0);
				updateBoard();
			}
		});
		leftButton.setBounds(10, 70, 52, 48);
		leftButton.setEnabled(false);
		movementPanel.add(leftButton);
		
		// s button
		downButton = new JButton("S");
		movementButtonGroup.add(downButton);
		downButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				movePlayer(0,1);
				updateBoard();
			}
		});
		downButton.setBounds(73, 70, 52, 48);
		downButton.setEnabled(false);
		movementPanel.add(downButton);
		
		// d button
		rightButton = new JButton("D");
		movementButtonGroup.add(rightButton);
		rightButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				movePlayer(1,0);
				updateBoard();
			}
		});
		rightButton.setBounds(135, 70, 52, 48);
		rightButton.setEnabled(false);
		movementPanel.add(rightButton);
		
		/* player hand panel */
		JPanel cardPanel = new JPanel();
		cardPanel.setBounds(794, 218, 280, 314);
		contentPane.add(cardPanel);
		cardPanel.setLayout(null);
		
		handPanel = new JPanel();
		handPanel.setBounds(0, 23, 280, 291);
		cardPanel.add(handPanel);
		handPanel.setLayout(new GridLayout(3, 3, 0, 0));
		
		handLabel = new JLabel("");
		handLabel.setBounds(86, 0, 194, 23);
		cardPanel.add(handLabel);
		handLabel.setFont(new Font("Tahoma", Font.BOLD, 14));	
		
		/* action panel for suggestions and accusations */
		actionPanel = new JPanel();
		actionPanel.setBounds(828, 543, 201, 59);
		contentPane.add(actionPanel);
		actionPanel.setLayout(null);
		
		btnSuggestion = new JButton("Suggestion");
		btnSuggestion.addActionListener(l -> {
			doAction(btnSuggestion.getText(), true);
			changeMovementEnable(false);
			changeActionEnable(false);
			updateBoard();
		});
		btnSuggestion.setBounds(0, 11, 102, 29);
		actionPanel.add(btnSuggestion);
		btnSuggestion.setEnabled(false);
		
		btnAccusation = new JButton("Accusation");
		btnAccusation.addActionListener(l -> {
			doAction(btnAccusation.getText(), false);
			updateBoard();
			changeMovementEnable(false);
			changeActionEnable(false);
			playerIndex--;
			if(action.getCorrect()) {
				nextPlayerButton.setEnabled(false);
			}
		});
		btnAccusation.setBounds(99, 11, 102, 29);
		actionPanel.add(btnAccusation);
		btnAccusation.setEnabled(false);
		
		/* next player button */
		nextPlayerButton = new JButton("Next Player");
		nextPlayerButton.addActionListener(l -> {
			doTurn();
			nextPlayerButton.setEnabled(false);
			btnRoll.setEnabled(true);
			locationLabel.setText("");
			changeActionEnable(false);
			Enumeration<AbstractButton> enumeration = movementButtonGroup.getElements();
			while (enumeration.hasMoreElements()) {
			    enumeration.nextElement().setEnabled(false);
			}
		});
		nextPlayerButton.setBounds(878, 752, 109, 23);
		contentPane.add(nextPlayerButton);
		nextPlayerButton.setEnabled(false);
		
		/* Menu bar */
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 45, 21);
		contentPane.add(menuBar);
		
		JMenu mnNewMenu = new JMenu("Menu");
		menuBar.add(mnNewMenu);
		
		JMenuItem newGameMenuItem = new JMenuItem("New Game");
		newGameMenuItem.addActionListener(l -> {
			dispose();
			new Game();
			setupPlayers();
			
		});
		mnNewMenu.add(newGameMenuItem);
		
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(l -> {
			int input = JOptionPane.showConfirmDialog(this,
					"Are you sure?", "Exit", JOptionPane.YES_NO_OPTION);
			if(input == 0) {
				System.exit(0);
			}
		});
		mnNewMenu.add(exitMenuItem);
		
		
		
		playGame();
		
	}
	
	
	


	/**
	 * Starts the game by running the first turn
	 */
	private void playGame() {		
		doTurn();		
	}
	
	
	/**
	 * Carrys out the currentPlayers turn
	 */
	private void doTurn() {
		this.players = game.getPlayers();
		currentPlayer = getNextPlayer(players);
		currentPlayer.setTurnLocations(new ArrayList<>());
		currentHand = currentPlayer.getHand();
		playerTurnLabel.setText(playerNames.get(currentPlayer.getName()) + "'s turn");
		playerTokenPanel.removeAll();
		playerTokenPanel.add(new JLabel(getPlayerToken(currentPlayer.getName())));
		handLabel.setText(playerNames.get(currentPlayer.getName()) + "'s hand");
		setupPlayersHand();
	}
	
	
	/**
	 * move a player by the x or y co-ordinate
	 * 
	 * @param x - the x amount
	 * @param y - the y amount
	 */
	private void movePlayer(int x, int y) {
		Location previousLocation = currentPlayer.getLocation();
		if(movesRemaining > 0) {
			boolean didMove = board.movePlayer(currentPlayer, currentPlayer.getLocation().getX()+x, currentPlayer.getLocation().getY()+y);
			if(didMove && !currentPlayer.getLocation().isRoom()) {
				movesRemaining--;
			} 
			movesRemainingLabel.setText(Integer.toString(movesRemaining));
			locationLabel.setText(currentPlayer.getLocation().getName());
		}
		
		if(currentPlayer.getLocation().isRoom()) {
			changeActionEnable(true);
		} else {
			currentPlayer.getTurnLocations().add(previousLocation);
			changeActionEnable(false);
		}
	}
	
	
	/**
	 * update the board to draw changes
	 */
	private void updateBoard() {
		boardPanel.removeAll();
		boardPanel.updateUI();
		setupBoard();
	}
	
	
	
	/**
	 * Gets the next players turn from a list of players
	 * 
	 * @param players - the list of players in the game
	 * @return - the next player
	 */
	private Player getNextPlayer(List<Player> players) {
		if(playerIndex == players.size()) {
			playerIndex = 0;
			return players.get(playerIndex++);
			
			
		} else {
			return players.get(playerIndex++);
		}
	}
	
	
	/**
	 * Gets the players image token from their name
	 * 
	 * @param name - name of the player
	 * @return - 
	 */
	private ImageIcon getPlayerToken(String name) {
		if(name.equals("Mrs. White")) {
			return new ImageIcon(MRS_WHITE);
		
		} else if(name.equals("Mr. Green")) {
			return new ImageIcon(MR_GREEN);
			
		} else if(name.equals("Mrs. Peacock")) {
			return new ImageIcon(MRS_PEACOCK);
			
		} else if(name.equals("Prof. Plum")) {
			return new ImageIcon(PROF_PLUM);
			
		} else if(name.equals("Miss Scarlett")) {
			return new ImageIcon(MISS_SCARLETT);
			
		} else if(name.equals("Col. Mustard")) {
			return new ImageIcon(COL_MUSTARD);
		
		} else {
			
			return null;
		}
	}
	
	
	/**
	 * rolls the dice
	 */
	private void rollDice() {
		dice_1 = game.dice_1();
		dice_2 = game.dice_2();
		movesRemaining = dice_1 + dice_2;
		//movesRemaining = 12;
		getDiceImage(dice_1, diceOnePanel);
		getDiceImage(dice_2, diceTwoPanel);
	}
	
	
	/**
	 * Adds the dice image to the panel corresponding to the number
	 * 
	 * @param number - the dice number
	 * @param panel - the panel to add the image too
	 */
	private void getDiceImage(int number, JPanel panel) {
		panel.removeAll(); // clear the panel
		
		if(number == 1) {
			panel.add(new JLabel(new ImageIcon(DICE_1)));
		
		} else if (number == 2) {
			panel.add(new JLabel(new ImageIcon(DICE_2)));
		
		} else if (number == 3) {
			panel.add(new JLabel(new ImageIcon(DICE_3)));
		
		} else if (number == 4) {
			panel.add(new JLabel(new ImageIcon(DICE_4)));
			
		} else if (number == 5) {
			panel.add(new JLabel(new ImageIcon(DICE_5)));
			
		} else if (number == 6) {
			panel.add(new JLabel(new ImageIcon(DICE_6)));
			
		}
		
	}
	
	
	/**
	 * changes the enable state of the action buttons
	 * 
	 * @param bool - the state to change the action buttons too
	 */
	private void changeActionEnable(boolean bool) {
		btnSuggestion.setEnabled(bool);
		btnAccusation.setEnabled(bool);
	}
	
	
	/**
	 * Changes the enable state of the movement controls
	 * 
	 * @para bool -  the state to change the movement buttons too
	 */
	private void changeMovementEnable(boolean bool) {
		Enumeration<AbstractButton> enumeration = movementButtonGroup.getElements();
		while (enumeration.hasMoreElements()) {
		    enumeration.nextElement().setEnabled(bool);
		}
	}

	
	/**
	 * sets up the players hand
	 */
	private void setupPlayersHand() {
		handPanel.removeAll();
		handPanel.updateUI();
		
		for(Card card : currentHand) {
			getCardImage(handPanel, card);
		}
	}
	
	
	/**
	 * Gets the images corresponing to the card then
	 * adds it to the panel
	 * 
	 * @param panel - the panel to add the image too
	 * @param card - the card
	 */
	public void getCardImage(JPanel panel, Card card) {
		// character cards
		if(card.getName().equals("Mr. Green")) {
			panel.add(new JLabel(new ImageIcon(MR_GREEN_CARD)));
		
		} else if(card.getName().equals("Mrs. White")) {
			panel.add(new JLabel(new ImageIcon(MRS_WHITE_CARD)));
			
		} else if(card.getName().equals("Mrs. Peacock")) {
			panel.add(new JLabel(new ImageIcon(MRS_PEACOCK_CARD)));
			
		} else if(card.getName().equals("Prof. Plum")) {
			panel.add(new JLabel(new ImageIcon(PROF_PLUM_CARD)));
			
		} else if(card.getName().equals("Miss Scarlett")) {
			panel.add(new JLabel(new ImageIcon(MISS_SCARLETT_CARD)));
			
		} else if(card.getName().equals("Col. Mustard")) {
			panel.add(new JLabel(new ImageIcon(COL_MUSTARD_CARD)));
			
		// weapons cards
		} else if(card.getName().equals("Dagger")) {
			panel.add(new JLabel(new ImageIcon(DAGGER_CARD)));
			
		} else if(card.getName().equals("Revolver")) {
			panel.add(new JLabel(new ImageIcon(REVOLVER_CARD)));
			
		} else if(card.getName().equals("Candlestick")) {
			panel.add(new JLabel(new ImageIcon(CANDLESTICK_CARD)));
			
		} else if(card.getName().equals("Spanner")) {
			panel.add(new JLabel(new ImageIcon(SPANNER_CARD)));
			
		} else if(card.getName().equals("Lead Pipe")) {
			panel.add(new JLabel(new ImageIcon(LEAD_PIPE_CARD)));
			
		} else if(card.getName().equals("Rope")) {
			panel.add(new JLabel(new ImageIcon(ROPE_CARD)));
			
		// room cards
		} else if(card.getName().equals("Study")) {
			panel.add(new JLabel(new ImageIcon(STUDY_CARD)));	
			
		} else if(card.getName().equals("Hall")) {
			panel.add(new JLabel(new ImageIcon(HALL_CARD)));	
			
		} else if(card.getName().equals("Kitchen")) {
			panel.add(new JLabel(new ImageIcon(KITCHEN_CARD)));	
			
		} else if(card.getName().equals("Ball Room")) {
			panel.add(new JLabel(new ImageIcon(BALLROOM_CARD)));	
			
		} else if(card.getName().equals("Dining Room")) {
			panel.add(new JLabel(new ImageIcon(DINING_ROOM_CARD)));	
			
		} else if(card.getName().equals("Lounge")) {
			panel.add(new JLabel(new ImageIcon(LOUNGE_CARD)));	
			
		} else if(card.getName().equals("Billiard Room")) {
			panel.add(new JLabel(new ImageIcon(BILLIARD_ROOM_CARD)));	
			
		} else if(card.getName().equals("Library")) {
			panel.add(new JLabel(new ImageIcon(LIBRARY_CARD)));	
		
		} else if(card.getName().equals("Conservatory")) {
			panel.add(new JLabel(new ImageIcon(CONSERVATORY_CARD)));	
			
		} else {
			panel.add(new JLabel(card.getName()));
		}
		
	}
	
	
	/**
	 * Sets up the board
	 */
	private void setupBoard() {
		boardTokens = new HashMap<>();
		
		//create gridlayout of board from layout strings
		for(int y = 0; y < 25; y++) {
			for(int x = 0; x < 24; x++) {
				Location location = locations[x][y];
                if (location instanceof Wall) {              
                	boardPanel.add(new JLabel(new ImageIcon(WALL_TILE)));

                	
                } else if (location instanceof Hallway) {
                    // check for a player. Print player token or hallway token.
                    if (location.getToken() != null) {
                    	addTokenToBoard(location);
                    	
                    } else {
                    	boardPanel.add(new JLabel(new ImageIcon(HALL_TILE)));
                    }

                    // check for a player in a Room
                } else if (location instanceof Room && location.getToken() != null) {
                	addTokenToBoard(location);
                
                } else if(!location.getName().equals(null)) {
                	boardPanel.add(new JLabel(new ImageIcon(ROOM_TILE)));
                }
			}
		}
	}
	
	
	/* Key bindings */
	
	
  
	
	
	/**
	 * Adds a token to the board with a mouse listener
	 * and set the tool tip text to the players name
	 * 
	 * @param location - location of the token being added
	 */
	private void addTokenToBoard(Location location) {
		
		// add weapon tile images to the board
		JLabel label = getTokenTile(location);
		
    	label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				JLabel player = (JLabel) label.getComponentAt(e.getPoint());
				
				
				if(player == null || boardTokens.get(player) == null) {
					return;
				
				} else {
					String tokenName = boardTokens.get(player).getName();
					if(boardTokens.get(player) instanceof Player) {						
						label.setToolTipText(tokenName + ": " + playerNames.get(tokenName));
					
					} else {
						label.setToolTipText(tokenName);
					}
				}
			}
    	});
    	boardPanel.add(label);
    	boardTokens.put(label, location.getToken());
	}
	
	
	/**
	 * Creates a label containing a tokens image
	 * 
	 * @param location - the location of the token
	 * @return
	 */
	private JLabel getTokenTile(Location location) {
		JLabel label;
		if(location.getToken().getName().equals("Lead Pipe")) {
			label = new JLabel(new ImageIcon(LEAD_PIPE_TILE));
			
		} else if(location.getToken().getName().equals("Rope")) {
			label = new JLabel(new ImageIcon(ROPE_TILE));
			
		} else if(location.getToken().getName().equals("Spanner")) {
			label = new JLabel(new ImageIcon(SPANNER_TILE));
			
		} else if(location.getToken().getName().equals("Candlestick")) {
			label = new JLabel(new ImageIcon(CANDLESTICK_TILE));
			
		} else if(location.getToken().getName().equals("Revolver")) {
			label = new JLabel(new ImageIcon(REVOLVER_TILE));
			
		} else if(location.getToken().getName().equals("Dagger")) {
			label = new JLabel(new ImageIcon(DAGGER_TILE));
			
		} else if(location.getToken().getName().equals("Miss Scarlett")) {
			label = new JLabel(new ImageIcon(MISS_SCARLETT));
			
		} else if(location.getToken().getName().equals("Mrs. White")) {
			label = new JLabel(new ImageIcon(MRS_WHITE));
			
		} else if(location.getToken().getName().equals("Mrs. Peacock")) {
			label = new JLabel(new ImageIcon(MRS_PEACOCK));
			
		} else if(location.getToken().getName().equals("Col. Mustard")) {
			label = new JLabel(new ImageIcon(COL_MUSTARD));
			
		} else if(location.getToken().getName().equals("Mr. Green")) {
			label = new JLabel(new ImageIcon(MR_GREEN));
			
		} else if(location.getToken().getName().equals("Prof. Plum")) {
			label = new JLabel(new ImageIcon(PROF_PLUM));
		
		} else {
			label = new JLabel(location.getToken().getToken());
		}
		return label;
	}

	
	/**
	 * Loads in an image with the name 'filename'
	 * 
	 * @param filename - the name of the file
	 * @return - the Image loaded in
	 */
	public Image loadImage(String filename) {
		URL image = getClass().getClassLoader().getResource(filename);
		
		try {
			return ImageIO.read(image);
		
		} catch(IOException e) {
			throw new Error("Unable to load Image: " + e);
		}
	}
	
	
	/* Getters */
	
	public Game getGame() {
		return game;
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	
	/**
	 * Carrys out an action
	 * 
	 * @param title - title of the window
	 * @param isSuggestion - if the action is a suggestion or not
	 */
	public void doAction(String title, boolean isSuggestion) {
        action = new ActionUI(this, SwingUtilities.windowForComponent(this), title, currentPlayer, game, playerNames, isSuggestion, this);
    }
	
	
	/**
	 * Sets up the players for a new game
	 */
	public void setupPlayers() {
        new PlayerSetupUI(this, SwingUtilities.windowForComponent(this),
                "Character Selection");
    }
}


