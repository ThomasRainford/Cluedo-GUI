package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;


public class GUI {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}
	
	
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Cluedo");
		frame.setMinimumSize(new Dimension(700, 450));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		
		JLabel lblCluedo = new JLabel("CLUEDO");
		lblCluedo.setFont(new Font("Arial Black", Font.PLAIN, 54));
		lblCluedo.setBounds(224, 127, 254, 121);
		frame.getContentPane().add(lblCluedo);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Menu");
		menuBar.add(mnNewMenu);
		
		// opens the character setup menu
		JMenuItem mntmNewGame = new JMenuItem("New Game");
		mntmNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setupNumPlayers();
				
			}
		});
		
		mnNewMenu.add(mntmNewGame);
		
		// exits the window
		JMenuItem mntmNewMenuItem = new JMenuItem("Exit");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int input = JOptionPane.showConfirmDialog(frame,
						"Are you sure?", "Exit", JOptionPane.YES_NO_OPTION);
				if(input == 0) {
					System.exit(0);
				}
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		frame.pack();

	}
	
	public void setupNumPlayers() {
        new PlayerSetup(frame, SwingUtilities.windowForComponent(frame),
                "Character Selection");
    }

}
