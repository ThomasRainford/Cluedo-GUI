package gui;

import java.awt.EventQueue;
import java.awt.Window;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import board.token.Player;
import game.Game;

import javax.swing.JButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PlayerSetup extends JDialog {
	
	private Game game = new Game();
	private Map<String, String> playerNames;
	private List<String> names = new ArrayList<>(); // list of players
	
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private List<JRadioButton> rbList = new ArrayList<>();
	private JTextField textField;
	private JButton nextButton;
	private JButton continueButton;
	
	
	

	/**
	 * Create the dialog.
	 */
	public PlayerSetup(JFrame frame, Window window, String title) {
		super(window, title);
		setMinimumSize(new Dimension(350, 450));
		
		playerNames = new HashMap<>();
		
		
		setBounds(100, 100, 350, 473);
		
		//this.add(playerSetupPane);
        this.setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(frame);
        getContentPane().setLayout(null);
        
        JLabel lblSelectACharacter = new JLabel("Select Character");
        lblSelectACharacter.setFont(new Font("Arial", Font.BOLD, 20));
        lblSelectACharacter.setBounds(85, 11, 174, 56);
        getContentPane().add(lblSelectACharacter);
        
        
        JRadioButton rdbtnNewRadioButton = new JRadioButton("Mr. Green");
        buttonGroup.add(rdbtnNewRadioButton);
        rbList.add(rdbtnNewRadioButton);
        rdbtnNewRadioButton.setBounds(118, 74, 109, 23);
        getContentPane().add(rdbtnNewRadioButton);
        
        JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Mrs. White");
        buttonGroup.add(rdbtnNewRadioButton_1);
        rbList.add(rdbtnNewRadioButton_1);
        rdbtnNewRadioButton_1.setBounds(118, 100, 109, 23);
        getContentPane().add(rdbtnNewRadioButton_1);
        
        JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("Prof. Plum");
        buttonGroup.add(rdbtnNewRadioButton_2);
        rbList.add(rdbtnNewRadioButton_2);
        rdbtnNewRadioButton_2.setBounds(118, 126, 109, 23);
        getContentPane().add(rdbtnNewRadioButton_2);
        
        JRadioButton rdbtnNewRadioButton_3 = new JRadioButton("Mrs. Peacock");
        buttonGroup.add(rdbtnNewRadioButton_3);
        rbList.add(rdbtnNewRadioButton_3);
        rdbtnNewRadioButton_3.setBounds(118, 152, 109, 23);
        getContentPane().add(rdbtnNewRadioButton_3);
        
        JRadioButton rdbtnNewRadioButton_4 = new JRadioButton("Col. Mustard");
        buttonGroup.add(rdbtnNewRadioButton_4);
        rbList.add(rdbtnNewRadioButton_4);
        rdbtnNewRadioButton_4.setBounds(118, 178, 109, 23);
        getContentPane().add(rdbtnNewRadioButton_4);
        
        JRadioButton rdbtnNewRadioButton_5 = new JRadioButton("Miss Scarlett");
        buttonGroup.add(rdbtnNewRadioButton_5);
        rbList.add(rdbtnNewRadioButton_5);
        rdbtnNewRadioButton_5.setBounds(118, 205, 109, 23);
        getContentPane().add(rdbtnNewRadioButton_5);
        
        
        // name label
        JLabel lblNewLabel = new JLabel("Enter your name");
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 17));
        lblNewLabel.setBounds(106, 235, 131, 56);
        getContentPane().add(lblNewLabel);
              
        // text field for entering name
        textField = new JTextField();
        textField.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyReleased(KeyEvent e) {
        		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
        			pressNextButton();
        		}
        	}
        });
        //ensure can't go next when no name is entered
        Document textFieldDoc = textField.getDocument();
        textFieldDoc.addDocumentListener(new DocumentListener() {
           public void changedUpdate(DocumentEvent e) {
              updated(e);
           }
           public void insertUpdate(DocumentEvent e) {
              updated(e);
           }
           public void removeUpdate(DocumentEvent e) {
              updated(e);
           }
           private void updated(DocumentEvent e) {
              boolean enable = e.getDocument().getLength() > 0;
              nextButton.setEnabled(enable);
           }
        });
        textField.setBounds(106, 286, 131, 20);
        getContentPane().add(textField);
        textField.setColumns(10);
        
        // next button
        nextButton = new JButton("Next");
        
        // 'Next' button clicked
        nextButton.addActionListener(l -> {	
        	pressNextButton();
        });

        nextButton.setBounds(118, 337, 109, 23);
        getContentPane().add(nextButton);
        nextButton.setEnabled(false);
        
        // continue button
        continueButton = new JButton("Continue");
        // 'Continue' button action
        continueButton.addActionListener(l -> {
        	game.setupGame(names);
        	this.setVisible(false);
        	frame.setVisible(false);
        	
        	// create frame for game logic
        	BoardUI bui = new BoardUI(game, playerNames);
        	bui.setVisible(true);
        	
        });       
        continueButton.setBounds(118, 371, 109, 23);
        getContentPane().add(continueButton);
        continueButton.setEnabled(false);
        
        
        
        this.setVisible(true);
        
	}
	
	/**
	 * Stores the new player who was inputted
	 */
	private void pressNextButton() {
		
    	for(JRadioButton rb : rbList) {
        	if(rb.isSelected()) {
        		names.add(rb.getText());
        		playerNames.put(rb.getText(), textField.getText());
        		
        		
        		rb.setSelected(false);
                rb.setEnabled(false);
        	}
        }
    	textField.setText("");
    	
    	// enable continue button
    	if(names.size() >= 1) { // TODO: Change to 3
    		continueButton.setEnabled(true);
    	}
        
	}
}
