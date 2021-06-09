
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.Timer;

import javax.swing.*;

public class UNO implements ActionListener, MouseListener {

	private Game game;
	private Hand playerHand;
	private Hand cp1Hand;
	private Hand cp2Hand;

	private ArrayList<JLabel> playerCardLabels;
	private ArrayList<JLabel> cp1CardLabels;
	private ArrayList<JLabel> cp2CardLabels;

	private ArrayList<JLabel> showAllCardLabels;

	private JFrame frame;
	private JFrame winner;

	private JPanel homeScreen;
	private JPanel optionScreen;
	private JPanel twoPlayerGameScreen;
	private JPanel threePlayerGameScreen;

	private JLabel clickedLabel;
	private JLabel deckCard;
	private JLabel topCard;
	private JLabel currentColor;

	private JButton twoPlayer;
	private JButton threePlayer;
	private JButton showAllCards;

	UNO() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ImageIcon background = new ImageIcon("Pictures/homescreen.jpeg");
		background = scaleImage(background, 1.2, 1.2);
		Image img = background.getImage();
		frame.setPreferredSize(new Dimension(background.getIconWidth(), background.getIconHeight()));

		homeScreen = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(img, 0, 0, null);
			}
		};
		frame.setContentPane(homeScreen);

		homeScreen.setLayout(null);
		homeScreen.addMouseListener(this);

		JLabel clickAnywhereToContinue = new JLabel("Click Anywhere To Continue");
		clickAnywhereToContinue.setFont(new Font("Tacoma", Font.PLAIN, 28));
		clickAnywhereToContinue.setForeground(Color.WHITE);

		Dimension size = clickAnywhereToContinue.getPreferredSize();
		clickAnywhereToContinue.setBounds(550, 400, size.width, size.height);
		frame.getContentPane().add(clickAnywhereToContinue);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		playerCardLabels = new ArrayList<JLabel>();
		cp1CardLabels = new ArrayList<JLabel>();
		showAllCards = new JButton("Show All Cards");
		showAllCards.setVisible(false);
		showAllCards.addActionListener(this);
		showAllCards.setActionCommand("SHOW_ALL_CARDS");
	}

	private static ImageIcon scaleImage(ImageIcon image, double xFactor, double yFactor) {
		AffineTransform scaleTransform = new AffineTransform();
		scaleTransform.scale(xFactor, yFactor);
		Image img = image.getImage();
		Image newImg = img.getScaledInstance((int) (image.getIconWidth() * xFactor),
				(int) (image.getIconHeight() * yFactor), java.awt.Image.SCALE_SMOOTH);
		image = new ImageIcon(newImg);
		return image;
	}

	private void setOptionScreen() {
		ImageIcon background = new ImageIcon("Pictures/optionScreen.jpg");
		background = scaleImage(background, 1.35, 1.35);
		Image img = background.getImage();
		optionScreen = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(img, 0, 0, null);
			}
		};
		optionScreen.setLayout(null);

		twoPlayer = new JButton("Two Players");
		twoPlayer.setFont(new Font("Tacoma", Font.PLAIN, 18));
		twoPlayer.setBorderPainted(false);
		twoPlayer.setOpaque(true);
		twoPlayer.setBackground(Color.BLACK);
		twoPlayer.setForeground(Color.WHITE);
		twoPlayer.setPreferredSize(new Dimension(200, 100));
		Dimension size1 = twoPlayer.getPreferredSize();
		twoPlayer.setBounds(324, 550, size1.width, size1.height);
		optionScreen.add(twoPlayer);
		twoPlayer.addActionListener(this);
		twoPlayer.setActionCommand("TWO_PLAYER_BUTTON");

		threePlayer = new JButton("Three Players");
		threePlayer.setFont(new Font("Tacoma", Font.PLAIN, 18));
		threePlayer.setFont(new Font("Tacoma", Font.PLAIN, 18));
		threePlayer.setBorderPainted(false);
		threePlayer.setOpaque(true);
		threePlayer.setBackground(Color.BLACK);
		threePlayer.setForeground(Color.WHITE);
		threePlayer.setPreferredSize(new Dimension(200, 100));
		Dimension size2 = threePlayer.getPreferredSize();
		threePlayer.setBounds(780, 550, size2.width, size2.height);
		optionScreen.add(threePlayer);
		threePlayer.addActionListener(this);
		threePlayer.setActionCommand("THREE_PLAYER_BUTTON");

		JLabel optionTitle = new JLabel("Choose a Game Type");
		optionTitle.setFont(new Font("Tacoma", Font.BOLD, 60));

		optionTitle.setForeground(Color.BLACK);
		Dimension size3 = optionTitle.getPreferredSize();
		optionTitle.setBounds(335, 350, size3.width, size3.height);

		optionScreen.add(optionTitle);

		frame.setContentPane(optionScreen);
		frame.revalidate();
	}

	private void setTwoPlayerGameScreen() {
		game = new Game(false);
		playerHand = game.getPlayerHand();
		cp1Hand = game.getCp1().getHand();
		twoPlayerGameScreen = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				// display number of remaining cards
				g.setColor(new Color(240, 240, 240, 175));
				g.fillOval(543, 350, 40, 40);
				g.setColor(Color.BLACK);
				g.drawString(Integer.toString(game.getDeck().numberOfCards()), 555, 375);
			}
		};
		twoPlayerGameScreen.setBackground(new Color(157, 41, 51));
		twoPlayerGameScreen.setLayout(null);

		// display player cards
		showPlayerCards();

		// display computer cards
		showComputerCards();

		// display deck
		Card card = new Card(0, 15);
		ImageIcon image = card.getImage();
		image = scaleImage(image, 123d / 318, 175d / 434);
		int numCards = game.getDeck().numberOfCards();
		for (int i = 0; i < numCards; i++) {
			if (i % 4 == 0) {
				JLabel cardLabel = new JLabel(image);
				Dimension cardDim = cardLabel.getPreferredSize();
				cardLabel.setBounds(500, 400 + i / 4, cardDim.width, cardDim.height);
				twoPlayerGameScreen.add(cardLabel);
				if (i == 0) {
					deckCard = cardLabel;
					cardLabel.addMouseListener(this);
				}
			}
		}

		// display startingCard
		ImageIcon startingCard = game.getStartingCard().getImage();
		startingCard = scaleImage(startingCard, 0.3, 0.3);
		JLabel imageLabel = new JLabel(startingCard);
		Dimension dim = imageLabel.getPreferredSize();
		imageLabel.setBounds(750, 400, dim.width, dim.height);
		twoPlayerGameScreen.add(imageLabel);
		topCard = imageLabel;

		// reorder button
		JButton reorder = new JButton("Reorder Cards");
		Dimension reorderDim = reorder.getPreferredSize();
		reorder.setBounds(35, 750, reorderDim.width, reorderDim.height);
		twoPlayerGameScreen.add(reorder);
		reorder.addActionListener(this);
		reorder.setActionCommand("REORDER");

		frame.setContentPane(twoPlayerGameScreen);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private void setThreePlayerGameScreen() {

		optionScreen = new JPanel();
		optionScreen.setBackground(new Color(157, 41, 51));
		optionScreen.setLayout(null);
		frame.setContentPane(optionScreen);
		frame.setVisible(true);
		frame.pack();

		game = new Game(true);
		playerHand = game.getPlayerHand();
		cp1Hand = game.getCp1().getHand();
		cp2Hand = game.getCp2().getHand();
		threePlayerGameScreen = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				// display number of remaining cards
				g.setColor(new Color(240, 240, 240, 175));
				g.fillOval(543, 350, 40, 40);
				g.setColor(Color.BLACK);
				g.drawString(Integer.toString(game.getDeck().numberOfCards()), 555, 375);
			}
		};
		threePlayerGameScreen.setBackground(new Color(157, 41, 51));
		threePlayerGameScreen.setLayout(null);

		// display player cards
		showPlayerCards();

		// display computer cards
		showComputerCards3P();

		// display deck
		Card card = new Card(0, 15);
		ImageIcon image = card.getImage();
		image = scaleImage(image, 123d / 318, 175d / 434);
		int numCards = game.getDeck().numberOfCards();
		for (int i = 0; i < numCards; i++) {
			if (i % 4 == 0) {
				JLabel cardLabel = new JLabel(image);
				Dimension cardDim = cardLabel.getPreferredSize();
				cardLabel.setBounds(500, 400 + i / 4, cardDim.width, cardDim.height);
				if (!(game.isMultiplayer())) {
					twoPlayerGameScreen.add(cardLabel);
				} else {
					threePlayerGameScreen.add(cardLabel);
				}
				if (i == 0) {
					deckCard = cardLabel;
					cardLabel.addMouseListener(this);
				}
			}
		}

		// display startingCard
		ImageIcon startingCard = game.getStartingCard().getImage();
		startingCard = scaleImage(startingCard, 0.3, 0.3);
		JLabel imageLabel = new JLabel(startingCard);
		Dimension dim = imageLabel.getPreferredSize();
		imageLabel.setBounds(750, 400, dim.width, dim.height);
		threePlayerGameScreen.add(imageLabel);
		topCard = imageLabel;

		// reorder button
		JButton reorder = new JButton("Reorder Cards");
		Dimension reorderDim = reorder.getPreferredSize();
		reorder.setBounds(35, 750, reorderDim.width, reorderDim.height);
		threePlayerGameScreen.add(reorder);
		reorder.addActionListener(this);
		reorder.setActionCommand("REORDER");

		frame.setContentPane(threePlayerGameScreen);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private void showPlayerCards() {
		for (JLabel cardLabel : playerCardLabels) {
			if (cardLabel != null)
				if (!(game.isMultiplayer())) {
					twoPlayerGameScreen.remove(cardLabel);
				} else {
					threePlayerGameScreen.remove(cardLabel);
				}
		}
		playerCardLabels = new ArrayList<JLabel>();

		int numCard = playerHand.numberOfCards();
		if (numCard <= 16) {
			for (int i = 0; i < 16; i++) {
				playerCardLabels.add(null);
			}
			for (int i = numCard - 1; i >= 0; i--) {
				Card card = playerHand.nthCard(i);
				ImageIcon image = card.getImage();
				image = scaleImage(image, 0.3, 0.3);
				JLabel imageLabel = new JLabel(image);
				Dimension dim = imageLabel.getPreferredSize();
				imageLabel.setBounds(200 + 52 * i, 700, dim.width, dim.height);
				imageLabel.addMouseListener(this);
				if (!(game.isMultiplayer())) {
					twoPlayerGameScreen.add(imageLabel);
				} else {
					threePlayerGameScreen.add(imageLabel);
				}
				playerCardLabels.set(i, imageLabel);
			}
		} else if (16 < numCard && numCard <= 32) {
			playerCardLabels = new ArrayList<JLabel>();
			for (int i = 0; i < 32; i++) {
				playerCardLabels.add(null);
			}
			for (int i = numCard - 1; i >= 16; i--) {
				Card card = playerHand.nthCard(i);
				ImageIcon image = card.getImage();
				image = scaleImage(image, 0.3, 0.3);
				JLabel imageLabel = new JLabel(image);
				Dimension dim = imageLabel.getPreferredSize();
				imageLabel.setBounds(200 + 52 * (i - 16), 750, dim.width, dim.height);
				imageLabel.addMouseListener(this);
				if (!(game.isMultiplayer())) {
					twoPlayerGameScreen.add(imageLabel);
				} else {
					threePlayerGameScreen.add(imageLabel);
				}
				playerCardLabels.set(i, imageLabel);
				showAllCards.setVisible(false);
			}
			for (int i = 15; i >= 0; i--) {
				Card card = playerHand.nthCard(i);
				ImageIcon image = card.getImage();
				image = scaleImage(image, 0.3, 0.3);
				JLabel imageLabel = new JLabel(image);
				Dimension dim = imageLabel.getPreferredSize();
				imageLabel.setBounds(200 + 52 * i, 700, dim.width, dim.height);
				imageLabel.addMouseListener(this);
				if (!(game.isMultiplayer())) {
					twoPlayerGameScreen.add(imageLabel);
				} else {
					threePlayerGameScreen.add(imageLabel);
				}
				playerCardLabels.set(i, imageLabel);
				showAllCards.setVisible(false);
			}
		} else {
			for (int i = 0; i < 32; i++) {
				playerCardLabels.add(null);
			}
			for (int i = 31; i >= 16; i--) {
				Card card = playerHand.nthCard(i);
				ImageIcon image = card.getImage();
				image = scaleImage(image, 0.3, 0.3);
				JLabel imageLabel = new JLabel(image);
				Dimension dim = imageLabel.getPreferredSize();
				imageLabel.setBounds(200 + 52 * (i % 16), 750, dim.width, dim.height);
				imageLabel.addMouseListener(this);
				if (!(game.isMultiplayer())) {
					twoPlayerGameScreen.add(imageLabel);
				} else {
					threePlayerGameScreen.add(imageLabel);
				}
				playerCardLabels.set(i, imageLabel);
			}
			for (int i = 15; i >= 0; i--) {
				Card card = playerHand.nthCard(i);
				ImageIcon image = card.getImage();
				image = scaleImage(image, 0.3, 0.3);
				JLabel imageLabel = new JLabel(image);
				Dimension dim = imageLabel.getPreferredSize();
				imageLabel.setBounds(200 + 52 * (i % 16), 700, dim.width, dim.height);
				imageLabel.addMouseListener(this);
				if (!(game.isMultiplayer())) {
					twoPlayerGameScreen.add(imageLabel);
				} else {
					threePlayerGameScreen.add(imageLabel);
				}
				playerCardLabels.set(i, imageLabel);
			}

			Dimension dim = showAllCards.getPreferredSize();
			showAllCards.setBounds(35, 825, dim.width, dim.height);
			if (!(game.isMultiplayer())) {
				twoPlayerGameScreen.add(showAllCards);
			} else {
				threePlayerGameScreen.add(showAllCards);
			}
			showAllCards.setVisible(true);
		}
		if (!(game.isMultiplayer())) {
			twoPlayerGameScreen.repaint();
		} else {
			threePlayerGameScreen.repaint();
		}
	}

	private void showComputerCards() {
		for (JLabel cardLabel : cp1CardLabels) {
			if (cardLabel != null)
				if (!(game.isMultiplayer())) {
					twoPlayerGameScreen.remove(cardLabel);
				} else {
					threePlayerGameScreen.remove(cardLabel);
				}
		}
		cp1CardLabels = new ArrayList<JLabel>();

		int numCard = cp1Hand.numberOfCards();
		if (numCard <= 16) {
			for (int i = 0; i < 16; i++) {
				cp1CardLabels.add(null);
			}
			for (int i = numCard - 1; i >= 0; i--) {
				ImageIcon image = new ImageIcon("Pictures/BC.png");
				image = scaleImage(image, 123d / 318, 175d / 434);
				JLabel imageLabel = new JLabel(image);
				Dimension dim = imageLabel.getPreferredSize();
				imageLabel.setBounds(1073 - 52 * i, 77, dim.width, dim.height);
				imageLabel.addMouseListener(this);
				twoPlayerGameScreen.add(imageLabel);
				cp1CardLabels.set(i, imageLabel);
			}
		} else {
			playerCardLabels = new ArrayList<JLabel>();
			for (int i = 0; i < 32; i++) {
				playerCardLabels.add(null);
			}
			for (int i = numCard - 1; i >= 16; i--) {
				ImageIcon image = new ImageIcon("Pictures/BC.png");
				image = scaleImage(image, 123d / 318, 175d / 434);
				JLabel imageLabel = new JLabel(image);
				Dimension dim = imageLabel.getPreferredSize();
				imageLabel.setBounds(1073 - 52 * (i - 16), 77, dim.width, dim.height);
				imageLabel.addMouseListener(this);
				twoPlayerGameScreen.add(imageLabel);
				playerCardLabels.set(i, imageLabel);
				showAllCards.setVisible(false);
			}
			for (int i = 15; i >= 0; i--) {
				ImageIcon image = new ImageIcon("Pictures/BC.png");
				image = scaleImage(image, 123d / 318, 175d / 434);
				JLabel imageLabel = new JLabel(image);
				Dimension dim = imageLabel.getPreferredSize();
				imageLabel.setBounds(1073 - 52 * i, 27, dim.width, dim.height);
				imageLabel.addMouseListener(this);
				twoPlayerGameScreen.add(imageLabel);
				playerCardLabels.set(i, imageLabel);
				showAllCards.setVisible(false);
			}
		}
		twoPlayerGameScreen.repaint();
	}

	private void showComputerCards3P() {
		for (JLabel cardLabel : cp1CardLabels) {
			if (cardLabel != null)
				threePlayerGameScreen.remove(cardLabel);
		}
		if (!(game.isMultiplayer())) {
			for (JLabel cardLabel : cp2CardLabels) {
				if (cardLabel != null) {
					threePlayerGameScreen.remove(cardLabel);
				}
			}
		}
		cp1CardLabels = new ArrayList<JLabel>();
		cp2CardLabels = new ArrayList<JLabel>();

		int numCard = cp1Hand.numberOfCards();
		if (numCard <= 16) {
			for (int i = 0; i < 16; i++) {
				cp1CardLabels.add(null);
			}
			for (int i = numCard - 1; i >= 0; i--) {
				ImageIcon image = new ImageIcon("Pictures/BCRight.png");
				image = scaleImage(image, 123d / 318, 175d / 434);
				JLabel imageLabel = new JLabel(image);
				Dimension dim = imageLabel.getPreferredSize();
				imageLabel.setBounds(77, 100 + 52 * i, dim.width, dim.height);
				imageLabel.addMouseListener(this);
				threePlayerGameScreen.add(imageLabel);
				cp1CardLabels.set(i, imageLabel);
			}
		} else {
			playerCardLabels = new ArrayList<JLabel>();
			for (int i = 0; i < 32; i++) {
				playerCardLabels.add(null);
			}
			for (int i = numCard - 1; i >= 16; i--) {
				ImageIcon image = new ImageIcon("Pictures/BCRight.png");
				image = scaleImage(image, 123d / 318, 175d / 434);
				JLabel imageLabel = new JLabel(image);
				Dimension dim = imageLabel.getPreferredSize();
				imageLabel.setBounds(77, 100 + 52 * (i - 16), dim.width, dim.height);
				imageLabel.addMouseListener(this);
				threePlayerGameScreen.add(imageLabel);
				playerCardLabels.set(i, imageLabel);
				showAllCards.setVisible(false);
				threePlayerGameScreen.repaint();
			}
			for (int i = 15; i >= 0; i--) {
				ImageIcon image = new ImageIcon("Pictures/BCRight.png");
				image = scaleImage(image, 123d / 318, 175d / 434);
				JLabel imageLabel = new JLabel(image);
				Dimension dim = imageLabel.getPreferredSize();
				imageLabel.setBounds(27, 100 + 52 * i, dim.width, dim.height);
				imageLabel.addMouseListener(this);
				threePlayerGameScreen.add(imageLabel);
				playerCardLabels.set(i, imageLabel);
				showAllCards.setVisible(false);
			}
		}

		int numCard2 = cp2Hand.numberOfCards();
		if (numCard2 <= 16) {
			for (int i = 0; i < 16; i++) {
				cp2CardLabels.add(null);
			}
			for (int i = numCard2 - 1; i >= 0; i--) {
				ImageIcon image = new ImageIcon("Pictures/BCLeft.png");
				image = scaleImage(image, 123d / 318, 175d / 434);
				JLabel imageLabel = new JLabel(image);
				Dimension dim = imageLabel.getPreferredSize();
				imageLabel.setBounds(1049, 100 + 52 * i, dim.width, dim.height);
				imageLabel.addMouseListener(this);
				threePlayerGameScreen.add(imageLabel);
				cp2CardLabels.set(i, imageLabel);
			}
		} else {
			playerCardLabels = new ArrayList<JLabel>();
			for (int i = 0; i < 32; i++) {
				playerCardLabels.add(null);
			}
			for (int i = numCard - 1; i >= 16; i--) {
				ImageIcon image = new ImageIcon("Pictures/BCLeft.png");
				image = scaleImage(image, 123d / 318, 175d / 434);
				JLabel imageLabel = new JLabel(image);
				Dimension dim = imageLabel.getPreferredSize();
				imageLabel.setBounds(1049, 100 + 52 * (i - 16), dim.width, dim.height);
				imageLabel.addMouseListener(this);
				threePlayerGameScreen.add(imageLabel);
				playerCardLabels.set(i, imageLabel);
				showAllCards.setVisible(false);
			}
			for (int i = 15; i >= 0; i--) {
				ImageIcon image = new ImageIcon("Pictures/BCLeft.png");
				image = scaleImage(image, 123d / 318, 175d / 434);
				JLabel imageLabel = new JLabel(image);
				Dimension dim = imageLabel.getPreferredSize();
				imageLabel.setBounds(1009, 100 + 52 * i, dim.width, dim.height);
				imageLabel.addMouseListener(this);
				threePlayerGameScreen.add(imageLabel);
				playerCardLabels.set(i, imageLabel);
				showAllCards.setVisible(false);
			}
		}
		threePlayerGameScreen.repaint();
	}

	private int findRightmostPosition() {
		return 200 + (playerHand.numberOfCards() % 16 - 1) * 52;
	}

	private int findLeftMostPosition() {
		return 1073 - (cp1Hand.numberOfCards() % 16 + 1) * 52;
	}

	private int abs(int num) {
		if (num != 0) {
			return Math.abs(num);
		} else
			return 1;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String event = e.getActionCommand();
		switch (event) {
		case "PLAY_AGAIN":
			winner.dispose();
			setOptionScreen();
			break;
		case "TWO_PLAYER_BUTTON":
			setTwoPlayerGameScreen();
			break;
		case "THREE_PLAYER_BUTTON":
			setThreePlayerGameScreen();
			break;
		case "REORDER":
			ArrayList<Card> cards = Hand.sortCards(playerHand.getCards());
			playerHand.setCards(cards);
			showPlayerCards();
			break;
		case "SHOW_ALL_CARDS":
			showAllCardLabels = new ArrayList<JLabel>();

			JFrame frame = new JFrame();
			frame.setMinimumSize(new Dimension(400, 400));
			frame.setAlwaysOnTop(true);
			frame.setResizable(false);

			GridLayout layout = new GridLayout(0, 3);
			layout.setHgap(5);
			layout.setVgap(5);
			JPanel panel = new JPanel(layout);
			MouseListener pickCard = new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					for (JLabel cardLabel : showAllCardLabels) {
						if (e.getSource() == cardLabel) {
							int index = showAllCardLabels.indexOf(cardLabel);
							Card card = playerHand.nthCard(index);
							if (!game.playCard(playerHand, card)) {
								JOptionPane.showMessageDialog(frame, "That card cannot be played.", "Error",
										JOptionPane.ERROR_MESSAGE);
							} else {
								int response = JOptionPane.showConfirmDialog(frame,
										"Are you sure you want to play this " + card + "?", "Confirm",
										JOptionPane.YES_NO_OPTION);
								if (response == JOptionPane.YES_OPTION) {
									frame.dispose();
									if (index <= 31) {

										Timer timer = new Timer();

										TimerTask task = new TimerTask() {

											@Override
											public void run() {
												int destinationLocX = 750;
												int destinationLocY = 400;
												JLabel movingLabel = playerCardLabels.get(index);

												Rectangle cardLoc = movingLabel.getBounds();
												if (destinationLocX != cardLoc.x || destinationLocY != cardLoc.y) {
													cardLoc.translate(
															(destinationLocX - cardLoc.x)
															/ (abs(destinationLocX - cardLoc.x)),
															(destinationLocY - cardLoc.y)
															/ (abs(destinationLocY - cardLoc.y)));
													movingLabel.setBounds(cardLoc);
													twoPlayerGameScreen.repaint();
												} else {
													topCard.setVisible(false);
													topCard = movingLabel;
													playerCardLabels.remove(movingLabel);
													showPlayerCards();
													topCard.setVisible(true);
													timer.cancel();
												}
											}

										};
										timer.scheduleAtFixedRate(task, 0, 1);
									}
								}
							}
						}
					}
				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

			};
			for (Card card : playerHand.getCards()) {
				ImageIcon image = card.getImage();
				image = scaleImage(image, 0.3, 0.3);
				JLabel imageLabel = new JLabel(image);
				Dimension dim = imageLabel.getPreferredSize();
				imageLabel.setBounds(100, 100, dim.width, dim.height);
				panel.add(imageLabel);
				showAllCardLabels.add(imageLabel);
				imageLabel.addMouseListener(pickCard);
			}

			JScrollPane scrollPane = new JScrollPane(panel);
			scrollPane.setPreferredSize(new Dimension(400, 400));
			frame.setContentPane(scrollPane);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			break;
		}

	}

	private void playComputerCard(ComputerStrategy cp) {
		Card card = cp.chooseCard(game.getTopCard());
		if (card != null) {
			int color = 4;
			if (card.getColor() == Card.WILD) {
				color = cp.chooseColor();
				String colorName;
				Color COLOR;

				if (color == 0) {
					COLOR = Color.RED;
					colorName = "Red";
				} else if (color == 1) {
					COLOR = Color.YELLOW;
					colorName = "Yellow";
				} else if (color == 2) {
					COLOR = Color.GREEN;
					colorName = "Green";
				} else {
					COLOR = new Color(66, 135, 245);
					colorName = "Blue";
				}

				currentColor = new JLabel("Current Color: " + colorName);
				currentColor.setForeground(COLOR);
				currentColor.setFont(new Font("Tacoma", Font.PLAIN, 20));
				Dimension dim = currentColor.getPreferredSize();
				currentColor.setBounds(720, 362, dim.width, dim.height);

				if (!game.isMultiplayer()) {
					twoPlayerGameScreen.add(currentColor);
					twoPlayerGameScreen.repaint();
				} else {
					threePlayerGameScreen.add(currentColor);
					threePlayerGameScreen.repaint();
				}
			} else if (currentColor != null)
				currentColor.setVisible(false);

			ImageIcon icon = card.getImage();
			icon = scaleImage(icon, 0.3, 0.3);
			JLabel cardLabel = new JLabel(icon);
			cardLabel.setBounds(topCard.getBounds());
			if (!game.isMultiplayer()) {
				game.playCard(cp1Hand, card);
			} else {
				game.playCard(cp2Hand, card);
			}

			if (color != 4) {
				game.setTopCard(new Card(color + 1, Card.WILD_CARD));
			}

			int randIndex = (int) (cp.getHand().numberOfCards() * Math.random());

			Timer timer = new Timer();

			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					int destinationLocX = 750;
					int destinationLocY = 400;
					JLabel movingLabel = cp1CardLabels.get(randIndex);
					Rectangle cardLoc = movingLabel.getBounds();
					if (destinationLocX != cardLoc.x || destinationLocY != cardLoc.y) {
						cardLoc.translate((destinationLocX - cardLoc.x) / (abs(destinationLocX - cardLoc.x)),
								(destinationLocY - cardLoc.y) / (abs(destinationLocY - cardLoc.y)));
						movingLabel.setBounds(cardLoc);
						twoPlayerGameScreen.repaint();
					} else {
						if (!game.isMultiplayer()) {
							twoPlayerGameScreen.remove(topCard);
							topCard = cardLabel;
							playerCardLabels.remove(movingLabel);
							showComputerCards();
							twoPlayerGameScreen.add(topCard);
							topCard.setVisible(true);
							twoPlayerGameScreen.repaint();
							timer.cancel();
						} else {
							twoPlayerGameScreen.remove(topCard);
							topCard = cardLabel;
							playerCardLabels.remove(movingLabel);
							showComputerCards();
							twoPlayerGameScreen.add(topCard);
							topCard.setVisible(true);
							threePlayerGameScreen.repaint();
							timer.cancel();
						}
					}
				}
			};
			timer.scheduleAtFixedRate(task, 0, 1);
			play(game.getTurn());
		} else {
			ImageIcon image = new ImageIcon("Pictures/BC.png");
			image = scaleImage(image, 123d / 318, 175d / 434);
			JLabel movingCardLabel = new JLabel(image);
			Rectangle deckCardLoc = deckCard.getBounds();
			movingCardLabel.setBounds(deckCardLoc);
			if (!game.isMultiplayer()) {
				twoPlayerGameScreen.add(movingCardLabel);
			} else
				threePlayerGameScreen.add(movingCardLabel);

			Timer timer = new Timer();

			TimerTask task = new TimerTask() {

				@Override
				public void run() {
					int destinationLocX = findLeftMostPosition() + 52;
					int destinationLocY;

					if (cp1Hand.numberOfCards() <= 16)
						destinationLocY = 77;
					else
						destinationLocY = 27;

					Rectangle cardLoc = movingCardLabel.getBounds();
					if (destinationLocX != cardLoc.x || destinationLocY != cardLoc.y) {
						cardLoc.translate((destinationLocX - cardLoc.x) / (abs(destinationLocX - cardLoc.x)),
								(destinationLocY - cardLoc.y) / (abs(destinationLocY - cardLoc.y)));
						movingCardLabel.setBounds(cardLoc);
						twoPlayerGameScreen.repaint();
					} else {
						game.dealCard(cp1Hand);
						movingCardLabel.setVisible(false);
						showComputerCards();
						timer.cancel();
						play(game.getTurn());
					}
				}

			};
			timer.scheduleAtFixedRate(task, 0, 1);
		}

	}

	private void play(int turn) {
		if (turn == 1) {
			playComputerCard(game.getCp1());
		} else if (turn == 2) {
			playComputerCard(game.getCp2());
		}
		if (playerHand.numberOfCards()==0) {
			winner = new JFrame();
			winner.setSize(600,250);
			JPanel pane = new JPanel();
			pane.setLayout(new BorderLayout(0, 10));

			JPanel titlePanel = new JPanel();
			JLabel title = new JLabel("Congrats! You won UNO!");
			title.setFont(new Font("Tacoma", Font.PLAIN, 20));
			titlePanel.add(title);
	//		titlePanel.setPreferredSize(title.getPreferredSize());
			

			GridLayout layout = new GridLayout(2, 2);
			layout.setHgap(5);
			layout.setVgap(5);
			
			titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
			pane.add(titlePanel, BorderLayout.NORTH);
			//			ArrayList<JLabel> cardImages = new ArrayList<JLabel>();
			JPanel buttonPanel = new JPanel();
			JButton playAgain = new JButton();
			playAgain.setText("Play Again");
			playAgain.setSize(200, 50);
			buttonPanel.add(playAgain);
			playAgain.addActionListener(this);
			playAgain.setActionCommand("PLAY_AGAIN");
			
			pane.add(buttonPanel);
			
			winner.setContentPane(pane);
			winner.pack();
			winner.setLocationRelativeTo(null);

			winner.setVisible(true);
			game.setTurn(3);
		} else if (cp1Hand.numberOfCards()==0) {
			winner = new JFrame();
			winner.setSize(600,250);
			
			
			JPanel pane = new JPanel();
			pane.setLayout(new BorderLayout(0, 10));

			JPanel titlePanel = new JPanel();
			JLabel title = new JLabel("");
			if (game.isMultiplayer()) {
				title = new JLabel("Sorry, you lost to Computer Player 1.");
			} else {
				title = new JLabel("Sorry, you lost to the Computer Player.");
			}
			title.setFont(new Font("Tacoma", Font.PLAIN, 20));
			titlePanel.add(title);
			GridLayout layout = new GridLayout(2, 2);
			layout.setHgap(5);
			layout.setVgap(5);

			titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));
			pane.add(titlePanel, BorderLayout.NORTH);

			JPanel buttonPanel = new JPanel();
			JButton playAgain = new JButton();
			playAgain.setText("Play Again");
			playAgain.setSize(200, 50);
			buttonPanel.add(playAgain);
			playAgain.addActionListener(this);
			playAgain.setActionCommand("PLAY_AGAIN");
			
			pane.add(buttonPanel);
			
			winner.setContentPane(pane);
			winner.pack();
			winner.setLocationRelativeTo(null);

			winner.setVisible(true);
			game.setTurn(3);
		} else if (game.isMultiplayer()&&(cp2Hand.numberOfCards()==0)) {
			winner = new JFrame();
			winner.setSize(600,250);
			JPanel pane = new JPanel();
			pane.setLayout(new BorderLayout(0, 10));

			JPanel titlePanel = new JPanel();
			JLabel title = new JLabel("Sorry, you lost to Computer Player 2.");
			title.setFont(new Font("Tacoma", Font.PLAIN, 20));
			titlePanel.add(title);
		//	titlePanel.setPreferredSize(title.getPreferredSize());
			GridLayout layout = new GridLayout(2, 2);
			layout.setHgap(5);
			layout.setVgap(5);
			
			titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));
			pane.add(titlePanel, BorderLayout.NORTH);

			
			JPanel buttonPanel = new JPanel();
			JButton playAgain = new JButton();
			playAgain.setText("Play Again");
			playAgain.setSize(200, 50);
			buttonPanel.add(playAgain);
			playAgain.addActionListener(this);
			playAgain.setActionCommand("PLAY_AGAIN");
			
			pane.add(buttonPanel);
			//			ArrayList<JLabel> cardImages = new ArrayList<JLabel>();
			winner.setContentPane(pane);
			winner.pack();
			winner.setLocationRelativeTo(null);

			winner.setVisible(true);
			game.setTurn(3);
		}
	}

	//
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == homeScreen) {
			setOptionScreen();
		} else {
			if (e.getSource() == deckCard) {
				if (game.getTurn() == 0) {
					ImageIcon movingCard = new Card(0, 15).getImage();
					movingCard = scaleImage(movingCard, 123d / 318, 175d / 434);
					JLabel movingCardLabel = new JLabel(movingCard);
					Rectangle deckCardLoc = deckCard.getBounds();
					movingCardLabel.setBounds(deckCardLoc);
					if (game.isMultiplayer()) {
						threePlayerGameScreen.add(movingCardLabel);
					} else {
						twoPlayerGameScreen.add(movingCardLabel);
					}

					Timer timer = new Timer();

					TimerTask task = new TimerTask() {

						@Override
						public void run() {
							int destinationLocX = findRightmostPosition() + 52;
							int destinationLocY;
							if (playerHand.numberOfCards() <= 16) {
								destinationLocY = 700;
							} else {
								destinationLocY = 750;
							}

							Rectangle cardLoc = movingCardLabel.getBounds();
							if (destinationLocX != cardLoc.x || destinationLocY != cardLoc.y) {
								cardLoc.translate((destinationLocX - cardLoc.x) / (abs(destinationLocX - cardLoc.x)),
										(destinationLocY - cardLoc.y) / (abs(destinationLocY - cardLoc.y)));
								movingCardLabel.setBounds(cardLoc);
								if (game.isMultiplayer()) {
									threePlayerGameScreen.repaint();
								} else {
									twoPlayerGameScreen.repaint();
								}
								twoPlayerGameScreen.repaint();
							} else {
								game.dealCard(playerHand);
								movingCardLabel.setVisible(false);
								showPlayerCards();
								play(game.getTurn());
								timer.cancel();
							}
						}

					};
					timer.scheduleAtFixedRate(task, 0, 1);
				}
			} else {
				if (game.getTurn() == 0) {
					for (JLabel label : playerCardLabels) {
						if (e.getSource() == label) {
							if (clickedLabel == label) {
								int index = playerCardLabels.indexOf(label);
								Card card = playerHand.nthCard(index);
								if (game.playCard(playerHand, card)) {
									if (card.getColor() == Card.WILD) {
										JFrame selectCard = new JFrame();

										JPanel pane = new JPanel();
										pane.setLayout(new BorderLayout(0, 10));

										JPanel titlePanel = new JPanel();
										JLabel title = new JLabel("Choose a Color");
										title.setFont(new Font("Tacoma", Font.BOLD, 30));
										titlePanel.add(title);
										titlePanel.setPreferredSize(title.getPreferredSize());
										titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
										pane.add(titlePanel, BorderLayout.NORTH);

										GridLayout layout = new GridLayout(2, 2);
										layout.setHgap(5);
										layout.setVgap(5);
										ArrayList<JLabel> cardImages = new ArrayList<JLabel>();

										MouseListener ml = new MouseListener() {

											@Override
											public void mouseClicked(MouseEvent e) {
												for (JLabel label : cardImages) {
													if (label == e.getSource()) {
														int index = cardImages.indexOf(label);
														game.setTopCard(new Card(index + 1, Card.WILD_CARD));
														selectCard.dispose();

														String color;
														Color COLOR;
														if (index == 0) {
															color = "Red";
															COLOR = Color.RED;
														} else if (index == 1) {
															color = "Yellow";
															COLOR = Color.YELLOW;
														} else if (index == 2) {
															color = "Green";
															COLOR = Color.GREEN;
														} else {
															color = "Blue";
															COLOR = new Color(66, 135, 245);
														}
														if (currentColor != null) {
															if (game.isMultiplayer()) {
																threePlayerGameScreen.remove(currentColor);
															} else {
																twoPlayerGameScreen.remove(currentColor);
															}
														}
														currentColor = new JLabel("Current Color: " + color);
														currentColor.setForeground(COLOR);
														currentColor.setFont(new Font("Tacoma", Font.PLAIN, 20));
														Dimension dim = currentColor.getPreferredSize();
														currentColor.setBounds(720, 362, dim.width, dim.height);
														if (game.isMultiplayer()) {
															threePlayerGameScreen.add(currentColor);
															threePlayerGameScreen.repaint();
														} else {
															twoPlayerGameScreen.add(currentColor);
															twoPlayerGameScreen.repaint();
														}

														play(game.getTurn());

														break;

													}
												}
											}

											@Override
											public void mousePressed(MouseEvent e) {
											}

											@Override
											public void mouseReleased(MouseEvent e) {
											}

											@Override
											public void mouseEntered(MouseEvent e) {
											}

											@Override
											public void mouseExited(MouseEvent e) {
											}

										};

										JPanel cardPanel = new JPanel(layout);

										ImageIcon redCardImage = new Card(1, 0).getImage();
										redCardImage = scaleImage(redCardImage, 0.3, 0.3);
										JLabel redCard = new JLabel(redCardImage);
										cardPanel.add(redCard);
										cardImages.add(redCard);
										redCard.addMouseListener(ml);

										ImageIcon yellowCardImage = new Card(2, 0).getImage();
										yellowCardImage = scaleImage(yellowCardImage, 0.3, 0.3);
										JLabel yellowCard = new JLabel(yellowCardImage);
										cardPanel.add(yellowCard);
										cardImages.add(yellowCard);
										yellowCard.addMouseListener(ml);

										ImageIcon greenCardImage = new Card(3, 0).getImage();
										greenCardImage = scaleImage(greenCardImage, 0.3, 0.3);
										JLabel greenCard = new JLabel(greenCardImage);
										cardPanel.add(greenCard);
										cardImages.add(greenCard);
										greenCard.addMouseListener(ml);

										ImageIcon blueCardImage = new Card(4, 0).getImage();
										blueCardImage = scaleImage(blueCardImage, 0.3, 0.3);
										JLabel blueCard = new JLabel(blueCardImage);
										cardPanel.add(blueCard);
										cardImages.add(blueCard);
										blueCard.addMouseListener(ml);

										pane.add(cardPanel, BorderLayout.CENTER);
										selectCard.setContentPane(pane);
										selectCard.pack();
										selectCard.setLocationRelativeTo(null);

										selectCard.setVisible(true);

									}
									Timer timer = new Timer();
									TimerTask task = new TimerTask() {

										@Override
										public void run() {
											int destinationLocX = 750;
											int destinationLocY = 400;

											Rectangle cardLoc = label.getBounds();
											if (destinationLocX != cardLoc.x || destinationLocY != cardLoc.y) {
												cardLoc.translate(
														(destinationLocX - cardLoc.x)
														/ (abs(destinationLocX - cardLoc.x)),
														(destinationLocY - cardLoc.y)
														/ (abs(destinationLocY - cardLoc.y)));
												label.setBounds(cardLoc);
												if (!game.isMultiplayer()) {
													twoPlayerGameScreen.repaint();
												} else
													threePlayerGameScreen.repaint();
											} else {
												topCard.setVisible(false);
												topCard = label;
												playerCardLabels.remove(label);
												topCard.setVisible(true);
												showPlayerCards();
												clickedLabel = null;
												timer.cancel();
												if (card.getColor() != Card.WILD)
													play(game.getTurn());
											}
										}

									};
									timer.scheduleAtFixedRate(task, 0, 1);
								}
							} else {
								Rectangle loc = label.getBounds();
								loc.translate(0, -20);
								label.setBounds(loc);

								if (clickedLabel != null) {
									Rectangle ogLoc = clickedLabel.getBounds();
									ogLoc.translate(0, 20);
									clickedLabel.setBounds(ogLoc);
								}

								clickedLabel = label;

								if (game.isMultiplayer()) {
									threePlayerGameScreen.repaint();
								} else {
									twoPlayerGameScreen.repaint();
								}
							}
							break;
						}
					}
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	public static void main(String[] args) {
		UNO uno = new UNO();
	}

}
