/**
 * This is the extended class of MemoryFrame
 * @author JaiTorres13 
 */

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class MyMemoryFrame extends MemoryFrame {

	private static final long serialVersionUID = -1779776750581169562L;
	private static final boolean DEBUG = true;
	private JPanel contentPane;
	private TurnsTakenCounterLabel turnCounterLabel;
	private GameLevel difficulty;
	private JPanel centerGrid;
	private JLabel levelDescriptionLabel;
	private JLabel scoreLabel;


	/**
	 * Create the frame.
	 */
	public MyMemoryFrame() {
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800,700);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("Memory");
		menuBar.add(mnFile);

		ActionListener menuHandler = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dprintln("actionPerformed " + e.getActionCommand());
				try {
					if(e.getActionCommand().equals("Easy Level")) newGame("easy");
					else if(e.getActionCommand().equals("Equal Pair Level")) newGame("equalpair");
					else if(e.getActionCommand().equals("Same Rank Trio Level")) newGame("ranktrio");
					else if(e.getActionCommand().equals("Flush Level")) newGame("flush");
					else if(e.getActionCommand().equals("Straight Level")) newGame("straight");
					else if(e.getActionCommand().equals("Combo Level")) newGame("combo");
					else if(e.getActionCommand().equals("How To Play")) showInstructions();
					else if(e.getActionCommand().equals("About")) showAbout();
					else if(e.getActionCommand().equals("Exit")) System.exit(0);
				} catch (IOException e2) {
					e2.printStackTrace(); throw new RuntimeException("IO ERROR");
				}
			}
		};

		JMenuItem easyLevelMenuItem = new JMenuItem("Easy Level");
		easyLevelMenuItem.addActionListener(menuHandler);
		mnFile.add(easyLevelMenuItem);

		JMenuItem equalPairMenuItem = new JMenuItem("Equal Pair Level");
		equalPairMenuItem.addActionListener(menuHandler);
		mnFile.add(equalPairMenuItem);

		JMenuItem sameRankTrioMenuItem = new JMenuItem("Same Rank Trio Level");
		sameRankTrioMenuItem.addActionListener(menuHandler);		
		mnFile.add(sameRankTrioMenuItem);

		JMenuItem flushMenuItem = new JMenuItem("Flush Level");
		flushMenuItem.addActionListener(menuHandler);		
		mnFile.add(flushMenuItem);

		JMenuItem straightMenuItem = new JMenuItem("Straight Level");
		straightMenuItem.addActionListener(menuHandler);		
		mnFile.add(straightMenuItem);

		JMenuItem comboMenuItem = new JMenuItem("Combo Level");
		comboMenuItem.addActionListener(menuHandler);		
		mnFile.add(comboMenuItem);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmHowToPlay = new JMenuItem("How To Play");
		mntmHowToPlay.addActionListener(menuHandler);
		mnHelp.add(mntmHowToPlay);

		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(menuHandler);
		mnHelp.add(mntmAbout);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(5, 5));

		JLabel lblPokerMemory = new JLabel("PoKer Memory");
		lblPokerMemory.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblPokerMemory, BorderLayout.NORTH);

		centerGrid = new JPanel();
		centerGrid.setBorder(new LineBorder(new Color(0, 0, 0), 4));
		contentPane.add(centerGrid, BorderLayout.CENTER);
		centerGrid.setLayout(new GridLayout(4, 4, 5, 5));

		Component horizontalStrut = Box.createHorizontalStrut(10);
		contentPane.add(horizontalStrut, BorderLayout.WEST);

		Component horizontalStrut_1 = Box.createHorizontalStrut(10);
		contentPane.add(horizontalStrut_1, BorderLayout.EAST);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));

		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		panel_1.add(horizontalStrut_2);

		JLabel lblNewLabel = new JLabel("Turns:");
		panel_1.add(lblNewLabel);

		turnCounterLabel = new TurnsTakenCounterLabel();
		turnCounterLabel.setText("");
		panel_1.add(turnCounterLabel);

		Component horizontalGlue = Box.createHorizontalGlue();
		panel_1.add(horizontalGlue);

		levelDescriptionLabel = new JLabel("New label");
		panel_1.add(levelDescriptionLabel);

		Component horizontalGlue_1 = Box.createHorizontalGlue();
		panel_1.add(horizontalGlue_1);

		JLabel pointsLabel = new JLabel("Points:");
		panel_1.add(pointsLabel);

		scoreLabel = new JLabel("New label");
		scoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(scoreLabel);

		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		panel_1.add(horizontalStrut_3);
	}

	public TurnsTakenCounterLabel getTurnCounterLabel() {
		return turnCounterLabel;
	}

	public JPanel getCenterGrid() {
		return centerGrid;
	}

	public JLabel getLevelDescriptionLabel() {
		return levelDescriptionLabel;
	}

	public void setTurnCounterLabel(TurnsTakenCounterLabel turnCounterLabel) {
		this.turnCounterLabel = turnCounterLabel;
	}

	public void setCenterGrid(JPanel centerGrid) {
		this.centerGrid = centerGrid;
	}

	public void setLevelDescriptionLabel(JLabel levelDescriptionLabel) {
		this.levelDescriptionLabel = levelDescriptionLabel;
	}

	public void setGameLevel(GameLevel l) {
		this.difficulty = l;
	}

	public void setScore(long score) {
		this.scoreLabel.setText("" + score);
	}

	/**
	 * Prepares a new game (first game or non-first game)
	 * @throws IOException 
	 */
	public void newGame(String difficultyMode) throws IOException
	{
		// Reset the turn counter label
		this.getTurnCounterLabel().reset();

		// make a new card field with cards, and add it to the window

		if(difficultyMode.equalsIgnoreCase("easy")) {
			this.setGameLevel(new EasyLevel(this.getTurnCounterLabel(), this));
			this.getLevelDescriptionLabel().setText("Easy Level");
		}
		else if(difficultyMode.equalsIgnoreCase("equalpair")){
			this.setGameLevel(new MyEqualPair(this.getTurnCounterLabel(), this));
			this.getLevelDescriptionLabel().setText("Equal Pair Level");
		}

		else if(difficultyMode.equalsIgnoreCase("ranktrio")){
			this.setGameLevel(new MyRankTrio(this.getTurnCounterLabel(), this));
			this.getLevelDescriptionLabel().setText("Same Rank Trio Level");
		}

		else if(difficultyMode.equalsIgnoreCase("flush")){
			this.setGameLevel(new FlushLevel(this.getTurnCounterLabel(), this));
			this.getLevelDescriptionLabel().setText("Flush Level");
		}

		else if(difficultyMode.equalsIgnoreCase("straight")){
			this.setGameLevel(new StraightLevel(this.getTurnCounterLabel(), this));
			this.getLevelDescriptionLabel().setText("Sraight Level");
		}

		else if(difficultyMode.equalsIgnoreCase("combo")){
			this.setGameLevel(new ComboLevel(this.getTurnCounterLabel(), this));
			this.getLevelDescriptionLabel().setText("Combo Level");
		}

		else {
			throw new RuntimeException("Illegal Game Level Detected");
		}

		this.turnCounterLabel.reset();

		// clear out the content pane (removes turn counter label and card field)
		BorderLayout bl  = (BorderLayout) this.getContentPane().getLayout();
		this.getContentPane().remove(bl.getLayoutComponent(BorderLayout.CENTER));
		this.getContentPane().add(showCardDeck(), BorderLayout.CENTER);

		// show the window (in case this is the first game)
		this.setVisible(true);
	}

	public JPanel showCardDeck()
	{
		// make the panel to hold all of the cards
		JPanel panel = new JPanel(new GridLayout(difficulty.getRowsPerGrid(),difficulty.getCardsPerRow()));

		// this set of cards must have their own manager
		this.difficulty.makeDeck();

		for(int i= 0; i<difficulty.getGrid().size();i++){
			panel.add(difficulty.getGrid().get(i));
		}
		return panel;
	}

	public boolean gameOver() throws FileNotFoundException, InterruptedException{
		return difficulty.isGameOver();
	}

	/**
	 * Shows an instructional dialog box to the user
	 */
	private void showInstructions()
	{
		dprintln("MemoryGame.showInstructions()");
		final String HOWTOPLAYTEXT = 
				"How To Play\r\n" +
						"\r\n" +
						"EQUAL PAIR Level\r\n"+
						"The objective of this game is to uncover all the pairs.\r\n"+
						"Find all the 8 pairs to win the game!\r\n." +
						"\r\n"+
						"SAME RANK TRIO Level\r\n"+
						"The objective of this game is to uncover all the trios with the same rank.\r\n"+
						"Find all the possible trios to win the game!\r\n." +
						"\r\n"+
						"FLUSH LEVEL\r\n"+
						"The objective of this game is to uncover all the quintuplets with the same suit.\r\n"+
						"Find all the possible quintuplets to win the game!\r\n." +
						"\r\n"+
						"STRAIGHT LEVEL\r\n"+
						"The objective of this game is to uncover 5 cards with consecutive ranks.\r\n"+
						"Find all the possible combinations to win the game!\r\n." +
						"\r\n" +
						"COMBO LEVEL\r\n"+
						"The objective of this game is to uncover Poker Hands like Flush, Straight and Four a Kind. \r\n"+
						"Find 8 possible combinations to win the game!" +
						"\r\n"; 

		JOptionPane.showMessageDialog(this, HOWTOPLAYTEXT
				, "How To Play", JOptionPane.PLAIN_MESSAGE);
	}

	/**
	 * Shows an dialog box with information about the program
	 */
	private void showAbout()
	{
		dprintln("MemoryGame.showAbout()");
		final String ABOUTTEXT = "Game Customized at UPRM. Originally written by Mike Leonhard";

		JOptionPane.showMessageDialog(this, ABOUTTEXT
				, "About Memory Game", JOptionPane.PLAIN_MESSAGE);
	}
	/**
	 * Prints debugging messages to the console
	 *
	 * @param message the string to print to the console
	 */
	static public void dprintln( String message )
	{
		if (DEBUG) System.out.println( message );
	}
}
