import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class UNO implements ActionListener, MouseListener {

	private Game game;
	private JFrame frame;
	private JPanel homeScreen;
	private JPanel optionScreen;
	private JPanel twoPlayerGameScreen;
	private JButton twoPlayer;
	private JButton threePlayer;
	private ArrayList<JLabel> playerCardLabels;
	private ArrayList<JLabel> showAllCardLabels;
	private JLabel clickedLabel;
	private JLabel deckCard;
	private JLabel topCard;
	private JButton showAllCards;
	private Hand playerHand;

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
		frame.setVisible(true);

		playerCardLabels = new ArrayList<JLabel>();
		showAllCards = new JButton("Show All Cards");
		showAllCards.setVisible(false);
		showAllCards.addActionListener(this);
		showAllCards.setActionCommand("SHOW_ALL_CARDS");
	}

	private ImageIcon scaleImage(ImageIcon image, double xFactor, double yFactor) {
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
		Card card = new Card(0, 15);
		ImageIcon image = card.getImage();
		image = scaleImage(image, 123d / 318, 175d / 434);
		for (int i = 0; i < game.getCp1().getHand().numberOfCards(); i++) {
			JLabel imageLabel = new JLabel(image);
			Dimension dim = imageLabel.getPreferredSize();
			imageLabel.setBounds(1073 - 52 * i, 77, dim.width, dim.height);
			twoPlayerGameScreen.add(imageLabel);
		}

		// display deck
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
		frame.setVisible(true);
		frame.pack();
	}

	private void setThreePlayerGameScreen() {
		optionScreen = new JPanel();
		optionScreen.setBackground(new Color(157, 41, 51));
		optionScreen.setLayout(null);
		frame.setContentPane(optionScreen);
		frame.setVisible(true);
		frame.pack();
	}

	private void showPlayerCards() {
		for (JLabel cardLabel : playerCardLabels) {
			if (cardLabel != null)
				twoPlayerGameScreen.remove(cardLabel);
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
				twoPlayerGameScreen.add(imageLabel);
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
				twoPlayerGameScreen.add(imageLabel);
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
				twoPlayerGameScreen.add(imageLabel);
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
				twoPlayerGameScreen.add(imageLabel);
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
				twoPlayerGameScreen.add(imageLabel);
				playerCardLabels.set(i, imageLabel);
			}

			Dimension dim = showAllCards.getPreferredSize();
			showAllCards.setBounds(35, 825, dim.width, dim.height);
			twoPlayerGameScreen.add(showAllCards);
			showAllCards.setVisible(true);
		}
		twoPlayerGameScreen.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String event = e.getActionCommand();
		switch (event) {
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
							int response = JOptionPane.showConfirmDialog(panel,
									"Are you sure you want to play this " + card + "?", "Confirm",
									JOptionPane.YES_NO_OPTION);
							if (response == JOptionPane.YES_OPTION) {
								if (index <= 31) {
									Timer timer = new Timer(1, new ActionListener() {
										int destinationLocX = 750;
										int destinationLocY = 400;

										@Override
										public void actionPerformed(ActionEvent e) {
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
												((Timer) e.getSource()).stop();
												topCard.setVisible(false);
												topCard = movingLabel;
												playerCardLabels.remove(movingLabel);
												showPlayerCards();
												topCard.setVisible(true);
											}
										}
									});
									timer.setInitialDelay(0);
									timer.start();
								}
							} else {

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
			frame.setVisible(true);
			break;
		}

	}

	private int findRightmostPosition() {
		return 200 + (playerHand.numberOfCards() % 16 - 1) * 52;
	}

	private int abs(int num) {
		if (num != 0) {
			return Math.abs(num);
		} else
			return 1;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == homeScreen) {
			setOptionScreen();
		} else if (e.getSource() == deckCard) {
			ImageIcon movingCard = new Card(0, 15).getImage();
			movingCard = scaleImage(movingCard, 123d / 318, 175d / 434);
			JLabel movingCardLabel = new JLabel(movingCard);
			Rectangle deckCardLoc = deckCard.getBounds();
			movingCardLabel.setBounds(deckCardLoc);
			twoPlayerGameScreen.add(movingCardLabel);
			int destinationLocY;
			if (playerHand.numberOfCards() <= 16) {
				destinationLocY = 700;
			} else {
				destinationLocY = 750;
			}
			Timer timer = new Timer(1, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Rectangle deckCardLoc = movingCardLabel.getBounds();
					int destinationLocX = findRightmostPosition() + 52;
					if (deckCardLoc.x != destinationLocX || deckCardLoc.y != destinationLocY) {
						deckCardLoc.translate((destinationLocX - deckCardLoc.x) / abs(destinationLocX - deckCardLoc.x),
								(destinationLocY - deckCardLoc.y) / abs(destinationLocY - deckCardLoc.y));
						movingCardLabel.setBounds(deckCardLoc);
						twoPlayerGameScreen.repaint();
					} else {
						game.dealCard(playerHand);
						movingCardLabel.setVisible(false);
						showPlayerCards();
						((Timer) e.getSource()).stop();
					}

				}
			});
			timer.setInitialDelay(0);
			timer.start();
		} else {
			for (JLabel label : playerCardLabels) {
				if (e.getSource() == label) {
					if (clickedLabel == label) {
						int index = playerCardLabels.indexOf(label);
						Card card = playerHand.nthCard(index);
						if(game.playCard(playerHand, card)) {
							if(card.getColor()==Card.WILD) {
								JFrame selectCard = new JFrame();
								
								JPanel pane = new JPanel();
								
								pane.setLayout(new BorderLayout(0,10));
								
								JPanel titlePanel = new JPanel();
								JLabel title = new JLabel("Choose a Color");
								title.setFont(new Font("Tacoma", Font.BOLD, 30));
								titlePanel.add(title);
								titlePanel.setPreferredSize(title.getPreferredSize());
								titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
								pane.add(titlePanel, BorderLayout.NORTH);
								
								GridLayout layout = new GridLayout(2,2);
								layout.setHgap(5);
								layout.setVgap(5);

								JPanel cardPanel = new JPanel(layout);
								ImageIcon redCardImage = new Card(1,0).getImage();
								redCardImage = scaleImage(redCardImage, 0.3, 0.3);
								JLabel redCard = new JLabel(redCardImage);
								cardPanel.add(redCard);
								
								ImageIcon yellowCardImage = new Card(2,0).getImage();
								yellowCardImage = scaleImage(yellowCardImage, 0.3, 0.3);
								JLabel yellowCard = new JLabel(yellowCardImage);

								cardPanel.add(yellowCard);
								
								ImageIcon greenCardImage = new Card(3,0).getImage();
								greenCardImage = scaleImage(greenCardImage, 0.3, 0.3);
								JLabel greenCard = new JLabel(greenCardImage);

								cardPanel.add(greenCard);
								
								ImageIcon blueCardImage = new Card(4,0).getImage();
								blueCardImage = scaleImage(blueCardImage, 0.3, 0.3);
								JLabel blueCard = new JLabel(blueCardImage);
								
								cardPanel.add(blueCard);
//								
								pane.add(cardPanel, BorderLayout.CENTER);
								selectCard.setContentPane(pane);
								selectCard.pack();
								selectCard.setVisible(true);
								
							}
							Timer timer = new Timer(1, new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									Rectangle deckCardLoc = label.getBounds();
									if (deckCardLoc.x != 750 || deckCardLoc.y != 400) {
										deckCardLoc.translate((750 - deckCardLoc.x) / abs(750 - deckCardLoc.x),
												(400 - deckCardLoc.y) / abs(400 - deckCardLoc.y));
										label.setBounds(deckCardLoc);
										twoPlayerGameScreen.repaint();
									} else {
										((Timer) e.getSource()).stop();
										topCard.setVisible(false);
										topCard = label;
										playerCardLabels.remove(label);
										topCard.setVisible(true);		
										showPlayerCards();
										clickedLabel= null;
									}

								}
							});
							timer.setInitialDelay(0);
							timer.start();
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

						twoPlayerGameScreen.repaint();
					}
					break;
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
		uno.setTwoPlayerGameScreen();
	}

}