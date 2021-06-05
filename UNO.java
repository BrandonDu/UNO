import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import javax.swing.*;

public class UNO implements ActionListener, MouseListener {

	private Game game;
	private JFrame frame;
	private JPanel homeScreen;
	private JPanel optionScreen;
	private JPanel twoPlayerGameScreen;
	private JButton twoPlayer;
	private JButton threePlayer;

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
		twoPlayerGameScreen = new JPanel();
		twoPlayerGameScreen.setBackground(new Color(157, 41, 51));
		twoPlayerGameScreen.setLayout(null);

		// ImageIcon card1 = new ImageIcon("Pictures/B+2.png");
		// card1 = scaleImage(card1, 0.3, 0.3);
		// JLabel imageLabel = new JLabel(card1);
		//
		// // Dimension dim = imageLabel.getPreferredSize();
		// System.out.println(dim);
		// imageLabel.setBounds(200, 650, dim.width, dim.height);
		// optionScreen.add(imageLabel);
		//
		// ImageIcon card2 = new ImageIcon("Pictures/B+2.png");
		// card2 = scaleImage(card2, 0.3, 0.3);
		// JLabel card2image = new JLabel(card2);
		// Dimension dim2 = card2image.getPreferredSize();
		// card2image.setBounds(973, 650, dim2.width, dim2.height);
		// optionScreen.add(card2image);

//		for (int i = 0; i < 15; i++) {
//			Card card = new Card(2, 2);
//			ImageIcon image = card.getImage();
//			image = scaleImage(image, 0.3, 0.3);
//			JLabel imageLabel = new JLabel(image);
//			Dimension dim = imageLabel.getPreferredSize();
//			imageLabel.setBounds(971 - 52 * i, 650, dim.width, dim.height);
//			imageLabel.addMouseListener(this);
//			twoPlayerGameScreen.add(imageLabel);
//		}

		game = new Game(false);
//display player cards
		for (int i = 0; i < 7; i++) {
			Card card = game.getPlayerHand().nthCard(i);
			System.out.println(card);
			ImageIcon image = card.getImage();
			image = scaleImage(image, 0.3, 0.3);
			JLabel imageLabel = new JLabel(image);
			Dimension dim = imageLabel.getPreferredSize();
			imageLabel.setBounds(412 - 52 * i, 670, dim.width, dim.height);
			twoPlayerGameScreen.add(imageLabel);
		}
//display computer cards
		for(int i=0; i<7; i++) {
			Card card = game.getCp1().getHand().nthCard(i);
			ImageIcon image = card.getImage();
			image = scaleImage(image, 0.3, 0.3);
			JLabel imageLabel = new JLabel(image);
			Dimension dim = imageLabel.getPreferredSize();
			imageLabel.setBounds(1073 - 52 * i, 107, dim.width, dim.height);
			twoPlayerGameScreen.add(imageLabel);
		}
		
		
		ImageIcon startingCard = game.getStartingCard().getImage();
		startingCard = scaleImage(startingCard, 0.3, 0.3);
		JLabel imageLabel = new JLabel(startingCard);
		System.out.println(imageLabel.getPreferredSize());

		Dimension dim = imageLabel.getPreferredSize();
		imageLabel.setBounds(750, 400, dim.width, dim.height);
		twoPlayerGameScreen.add(imageLabel);
		
		frame.setContentPane(twoPlayerGameScreen);
		frame.setVisible(true);
		frame.pack();
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
		System.out.println(e.getSource());
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
		}

	}

	public static void main(String[] args) {
		UNO uno = new UNO();
		uno.setTwoPlayerGameScreen();
		System.out.println(uno.frame.getPreferredSize());
	}

}