import java.awt.*;
import java.awt.geom.AffineTransform;

import javax.swing.*;

// graphics class

public class UNO {
	
	private Game game;
	private JFrame frame;
	private JPanel panel;
	private JButton twoPLayer;
	private JButton threePlayer;
	
	UNO() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ImageIcon background = new ImageIcon("Pictures/homescreen.jpeg");
		
		panel = new JPanel();
		
		JLabel contentPane = new JLabel();
		contentPane.setIcon(background);
		contentPane.setLayout(new BorderLayout());
		frame.setContentPane(contentPane);
		frame.pack();
		frame.setVisible(true);
	}
	
	
	public static void main(String[] args) {
		UNO uno = new UNO();
	}
}