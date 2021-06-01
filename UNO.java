import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import javax.swing.*;

public class UNO implements MouseListener {

	private Game game;
	private JFrame frame;
	private JPanel homeScreen;
	private JPanel optionScreen;
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
		twoPlayer.setBackground(Color.CYAN);
		twoPlayer.setPreferredSize(new Dimension(200, 100));
		Dimension size1 = twoPlayer.getPreferredSize();
		twoPlayer.setBounds(324, 550, size1.width, size1.height);
		optionScreen.add(twoPlayer);

		threePlayer = new JButton("Three Players");
		threePlayer.setFont(new Font("Tacoma", Font.PLAIN, 18));
		threePlayer.setPreferredSize(new Dimension(200, 100));
		Dimension size2 = threePlayer.getPreferredSize();
		threePlayer.setBounds(800, 550, size2.width, size2.height);
		optionScreen.add(threePlayer);

		JLabel optionTitle = new JLabel("Choose a game type");
		optionTitle.setFont(new Font("Tacoma", Font.PLAIN, 30));

		// just me testing how this works
		// optionTitle.setOpaque(true);
		// optionTitle.setBackground(Color.BLACK);

		optionTitle.setForeground(Color.WHITE);
		Dimension size3 = optionTitle.getPreferredSize();
		optionTitle.setBounds(600, 100, size3.width, size3.height);

		optionScreen.add(optionTitle);

		System.out.println(frame.getPreferredSize());
		frame.setContentPane(optionScreen);
		frame.revalidate();

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

	public static void main(String[] args) {
		UNO uno = new UNO();
		uno.setOptionScreen();
	}

}