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

	public ImageIcon scaleImage(ImageIcon image, double xFactor, double yFactor) {
		AffineTransform scaleTransform = new AffineTransform();
		scaleTransform.scale(xFactor, yFactor);
		Image img = image.getImage();
		Image newImg = img.getScaledInstance((int) (image.getIconWidth() * xFactor),
				(int) (image.getIconHeight() * yFactor), java.awt.Image.SCALE_SMOOTH);
		image = new ImageIcon(newImg);
		return image;
	}

	public void setOptionScreen() {
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

	public void setTwoPlayerGameScreen() {
		game = new Game(false);
		twoPlayerGameScreen = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				// display number of remaining cards
				g.setColor(new Color(240, 240, 240, 175));
				g.fillOval(543, 350, 40, 40);
				g.setColor(Color.BLACK);
				g.drawString(Integer.toString(Game.getDeck().numberOfCards()), 555, 375);
			}
		};
		twoPlayerGameScreen.setBackground(new Color(157, 41, 51));
		twoPlayerGameScreen.setLayout(null);

		//display player cards
		for (int i = 0; i < game.getPlayerHand().numberOfCards(); i++) {
			Card card = game.getPlayerHand().nthCard(i);
			ImageIcon image = card.getImage();
			image = scaleImage(image, 0.3, 0.3);
			JLabel imageLabel = new JLabel(image);
			Dimension dim = imageLabel.getPreferredSize();
			imageLabel.setBounds(512 - 52 * i, 700, dim.width, dim.height);
			twoPlayerGameScreen.add(imageLabel);
			playerCardLabels.add(imageLabel);
		}

		//display computer cards
		for (int i = 0; i < game.getCp1().getHand().numberOfCards(); i++) {
			Card card = game.getCp1().getHand().nthCard(i);
			ImageIcon image = card.getImage();
			image = scaleImage(image, 0.3, 0.3);
			JLabel imageLabel = new JLabel(image);
			Dimension dim = imageLabel.getPreferredSize();
			imageLabel.setBounds(1073 - 52 * i, 77, dim.width, dim.height);
			twoPlayerGameScreen.add(imageLabel);
		}

		//display deck
		for(int i=0; i<Game.getDeck().numberOfCards(); i++) {
			if(i%4==0) {
				Card card = Game.getDeck().nthCard(i);
				ImageIcon cardImage = card.getImage();
				cardImage = scaleImage(cardImage, 0.3, 0.3);
				JLabel cardLabel = new JLabel(cardImage);
				Dimension cardDim = cardLabel.getPreferredSize();
				cardLabel.setBounds(500, 400 +  i/4, cardDim.width, cardDim.height);
				twoPlayerGameScreen.add(cardLabel); 
			}
		}
		Card card = new Card(1, 1);
		ImageIcon cardImage = card.getImage();
		cardImage = scaleImage(cardImage, 0.3, 0.3);
		JLabel cardLabel = new JLabel(cardImage);
		Dimension cardDim = cardLabel.getPreferredSize();
		cardLabel.setBounds(500, 400, cardDim.width, cardDim.height);
		twoPlayerGameScreen.add(cardLabel);

		//display startingCard
		ImageIcon startingCard = game.getStartingCard().getImage();
		startingCard = scaleImage(startingCard, 0.3, 0.3);
		JLabel imageLabel = new JLabel(startingCard);
		Dimension dim = imageLabel.getPreferredSize();
		imageLabel.setBounds(750, 400, dim.width, dim.height);
		twoPlayerGameScreen.add(imageLabel);

		//reorder button
		JButton reorder = new JButton("Reorder Cards");
		Dimension REORDERDim = reorder.getPreferredSize();
		reorder.setBounds(50, 650, REORDERDim.width, REORDERDim.height);
		twoPlayerGameScreen.add(reorder);
		reorder.addActionListener(this);
		reorder.setActionCommand("REORDER");

		frame.setContentPane(twoPlayerGameScreen);
		frame.setVisible(true);
		frame.pack();
	}

	public void updateTwoPlayerGameScreen() {
		twoPlayerGameScreen.repaint();
	}

	public void setThreePlayerGameScreen() {
		optionScreen = new JPanel();
		optionScreen.setBackground(new Color(157, 41, 51));
		optionScreen.setLayout(null);
		frame.setContentPane(optionScreen);
		frame.setVisible(true);
		frame.pack();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == homeScreen) {
			setOptionScreen();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

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
			for(JLabel cardLabel : playerCardLabels) {
				twoPlayerGameScreen.remove(cardLabel);
			}
			ArrayList<Card> cards = Hand.sortCards(game.getPlayerHand().getCards());
			game.getPlayerHand().setCards(cards);
			for (int i = 0; i < game.getPlayerHand().numberOfCards(); i++) {
				Card card = game.getPlayerHand().nthCard(i);
				ImageIcon image = card.getImage();
				image = scaleImage(image, 0.3, 0.3);
				JLabel imageLabel = new JLabel(image);
				Dimension dim = imageLabel.getPreferredSize();
				int maxX = 200+(game.getPlayerHand().numberOfCards()-1)*52;
				imageLabel.setBounds(maxX - 52 * i, 700, dim.width, dim.height);
				twoPlayerGameScreen.add(imageLabel);
			}
			twoPlayerGameScreen.repaint();
		}

	}

	public static void main(String[] args) {
		UNO uno = new UNO();
		uno.setTwoPlayerGameScreen();

	}

}