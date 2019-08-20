package gui;


import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import cards.Card;
import game.Game;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import java.awt.Dimension;

public class ViewRefutationCardsUI extends JDialog {
	
	private BoardUI boardUI;
	private Game game;
	
	private JPanel card1;
	private JPanel card2;
	private JPanel card3;
	
	
	/**
	 * Create the dialog.
	 */
	public ViewRefutationCardsUI(JFrame frame, Window window, String title, ActionUI actionUI, RefutationUI refutationUI) {
		super(window, title);
		
		this.boardUI = actionUI.getBoardUI();
		this.game = boardUI.getGame();
		
		List<String> refutationCards = actionUI.getRefutationCards();
		
		setMinimumSize(new Dimension(450, 300));
		setBounds(100, 100, 450, 304);
		getContentPane().setLayout(null);
		this.setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(frame);
		
		
		JLabel lblRefutationCards = new JLabel("Refutation Cards");
		lblRefutationCards.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblRefutationCards.setBounds(154, 11, 169, 28);
		getContentPane().add(lblRefutationCards);
		
		JPanel cardsPanel = new JPanel();
		cardsPanel.setBounds(10, 59, 414, 118);
		getContentPane().add(cardsPanel);
		cardsPanel.setLayout(new GridLayout(1, 3, 0, 0));
		
		card1 = new JPanel();
		cardsPanel.add(card1);
		
		card2 = new JPanel();
		cardsPanel.add(card2);
		
		card3 = new JPanel();
		cardsPanel.add(card3);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(l -> {
			dispose();
			refutationUI.dispose();
			actionUI.dispose();
		});
		btnClose.setBounds(167, 203, 89, 28);
		getContentPane().add(btnClose);
		
		addCards(refutationCards);
		
		
		this.setVisible(true);
	}
	
	
	/**
	 * Adds the refutation cards to the card panels
	 * 
	 * @param refutationCards - the lost of refutation cards
	 */
	private void addCards(List<String> refutationCards) {
		if(refutationCards.size() > 0) {
			getCardImage(card1, boardUI.getCurrentPlayer().getCardByName(refutationCards.get(0), game.getAllCards()));
		
		}
		
		if(refutationCards.size() > 1) {
			getCardImage(card2, boardUI.getCurrentPlayer().getCardByName(refutationCards.get(1), game.getAllCards()));
		}
		
		if(refutationCards.size() > 2) {
			getCardImage(card3, boardUI.getCurrentPlayer().getCardByName(refutationCards.get(2), game.getAllCards()));
		}
	}
	
	
	/**
	 * Gets the image for the card
	 * 
	 * @param panel - the panel to add the image to
	 * @param card - the card
	 */
	private void getCardImage(JPanel panel, Card card) {
		boardUI.getCardImage(panel, card);
	}

}
