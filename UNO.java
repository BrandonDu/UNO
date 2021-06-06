import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
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
	private JLabel clickedLabel;
	private JLabel deckCard;
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
		for (int i = 0; i < game.getCp1().getHand().numberOfCards(); i++) {
			Card card = game.getCp1().getHand().nthCard(i);
			ImageIcon image = card.getImage();
			image = scaleImage(image, 0.3, 0.3);
			JLabel imageLabel = new JLabel(image);
			Dimension dim = imageLabel.getPreferredSize();
			imageLabel.setBounds(1073 - 52 * i, 77, dim.width, dim.height);
			twoPlayerGameScreen.add(imageLabel);
		}

		// display deck
		int numCards = game.getDeck().numberOfCards();
		for (int i = 0; i < numCards; i++) {
			if (i % 4 == 0) {
				Card card = game.getDeck().nthCard(i);
				ImageIcon cardImage = card.getImage();
				cardImage = scaleImage(cardImage, 0.3, 0.3);
				JLabel cardLabel = new JLabel(cardImage);
				Dimension cardDim = cardLabel.getPreferredSize();
				cardLabel.setBounds(500, 400 + i / 4, cardDim.width, cardDim.height);
				twoPlayerGameScreen.add(cardLabel);
				if(i==0) {
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

		// reorder button
		JButton reorder = new JButton("Reorder Cards");
		Dimension reorderDim = reorder.getPreferredSize();
		reorder.setBounds(50, 650, reorderDim.width, reorderDim.height);
		twoPlayerGameScreen.add(reorder);
		reorder.addActionListener(this);
		reorder.setActionCommand("REORDER");

		frame.setContentPane(twoPlayerGameScreen);
		frame.setVisible(true);
		frame.pack();
	}

	@SuppressWarnings("unused")
	private void updateTwoPlayerGameScreen() {
		// TODO will probably use when actually incorporating the game
		// Or will it
		// idrk
		twoPlayerGameScreen.repaint();
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
			twoPlayerGameScreen.remove(cardLabel);
		}
		playerCardLabels.clear();
		for (int i = playerHand.numberOfCards() - 1; i >= 0; i--) {
			Card card = playerHand.nthCard(i);
			ImageIcon image = card.getImage();
			image = scaleImage(image, 0.3, 0.3);
			JLabel imageLabel = new JLabel(image);
			Dimension dim = imageLabel.getPreferredSize();
			imageLabel.setBounds(200 + 52 * i, 700, dim.width, dim.height);
			imageLabel.addMouseListener(this);
			twoPlayerGameScreen.add(imageLabel);
			playerCardLabels.add(imageLabel);
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
		case "REORDER":
			ArrayList<Card> cards = Hand.sortCards(playerHand.getCards());
			playerHand.setCards(cards);
			showPlayerCards();

		}

	}

	private int findRightmostPosition() {
		return 200 + (playerHand.numberOfCards() - 1) * 52;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == homeScreen) {
			setOptionScreen();
		} else if (e.getSource() == deckCard) {
			ImageIcon movingCard = new Card(1,1).getImage();
			//TODO change this to back of card
			movingCard = scaleImage(movingCard, 0.3, 0.3);
			JLabel movingCardLabel = new JLabel(movingCard);
			Rectangle deckCardLoc = deckCard.getBounds();
			movingCardLabel.setBounds(deckCardLoc);
			twoPlayerGameScreen.add(movingCardLabel);

			Timer timer = new Timer(1, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Rectangle deckCardLoc = movingCardLabel.getBounds();
					int destinationLocX = findRightmostPosition() + 52;
					if (deckCardLoc.x != destinationLocX) {
						if (deckCardLoc.x < destinationLocX) {
							if (deckCardLoc.y != 700) {
								deckCardLoc.translate(1, 1);
								movingCardLabel.setBounds(deckCardLoc);
								twoPlayerGameScreen.repaint();
							} else {
								deckCardLoc.translate(1, 0);
								movingCardLabel.setBounds(deckCardLoc);
								twoPlayerGameScreen.repaint();
							}
						} else {
							if (deckCardLoc.y != 700) {
								deckCardLoc.translate(-1, 1);
								movingCardLabel.setBounds(deckCardLoc);
								twoPlayerGameScreen.repaint();
							} else {
								deckCardLoc.translate(-1, 0);
								movingCardLabel.setBounds(deckCardLoc);
								twoPlayerGameScreen.repaint();
							}
						}
					} else if (deckCardLoc.y != 700) {
						deckCardLoc.translate(0, 1);
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
						// TODO play the card
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
						break;
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
		uno.setTwoPlayerGameScreen();
	}

}