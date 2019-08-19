package gui;

import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.awt.Dimension;


public class TitleUI {

	private JFrame frame;
	
	private final Image TITLE = loadImage("title.png");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TitleUI window = new TitleUI();
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
	public TitleUI() {
		initialize();
	}
	
	
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Cluedo");
		frame.setMinimumSize(new Dimension(700, 450));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		
		JLabel lblCluedo = new JLabel(new ImageIcon(TITLE));
		lblCluedo.setFont(new Font("Arial Black", Font.PLAIN, 54));
		lblCluedo.setBounds(0, 0, 684, 390);
		frame.getContentPane().add(lblCluedo);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Menu");
		menuBar.add(mnNewMenu);
		
		// opens the character setup menu
		JMenuItem mntmNewGame = new JMenuItem("New Game");
		mntmNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setupPlayers();
				
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
	
	
	public void setupPlayers() {
        new PlayerSetupUI(frame, SwingUtilities.windowForComponent(frame),
                "Character Selection");
    }

}
