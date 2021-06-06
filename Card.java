import javax.swing.ImageIcon;

public class Card {

	public static final int RED = 1;
	public static final int YELLOW = 2;
	public static final int GREEN = 3;
	public static final int BLUE = 4;
	public static final int WILD = 5;

	public static final int REVERSE = 10;
	public static final int SKIP = 11;
	public static final int PLUS_TWO = 12;
	public static final int PLUS_FOUR = 13;
	public static final int WILD_CARD = 14;

	private int color;
	private int value;
	private ImageIcon cardImage;

	Card(int color, int value) {
		this.color = color;
		this.value = value;
		setColor();
	}

	Card(String color, String value) {
		if (color.toLowerCase().equals("red")) {
			this.color = RED;
		} else if (color.toLowerCase().equals("yellow")) {
			this.color = YELLOW;
		} else if (color.toLowerCase().equals("green")) {
			this.color = GREEN;
		} else if (color.toLowerCase().equals("blue")) {
			this.color = BLUE;
		} else if (color.toLowerCase().equals("wild")) {
			this.color = WILD;
		}

		try {
			this.value = Integer.parseInt(value);
			this.setColor();
		} catch (NumberFormatException ex) {
			if (value.toLowerCase().equals("reverse")) {
				this.value = REVERSE;
			} else if (value.toLowerCase().equals("skip")) {
				this.value = SKIP;
			} else if (value.toLowerCase().equals("plus two")) {
				this.value = PLUS_TWO;
			} else if (value.toLowerCase().equals("plus four")) {
				this.value = PLUS_FOUR;
			} else if (value.toLowerCase().equals("wild card")) {
				this.value = WILD_CARD;
			}
			this.setColor();
		}
	}

	public void setColor() {
		if (getValue()==0) {
			if (getColor() == 1) {
				cardImage = new ImageIcon("Pictures/R0.png");
			}
			else if (getColor() == 2) {
				cardImage = new ImageIcon("Pictures/Y0.png");
			}
			else if (getColor() == 3) {
				cardImage = new ImageIcon("Pictures/G0.png");
			}
			else if (getColor() == 4) {
				cardImage = new ImageIcon("Pictures/B0.png");
			}
		} else if (getValue()==1) {
			if (getColor() == 1) {
				cardImage = new ImageIcon("Pictures/R1.png");
			}
			else if (getColor() == 2) {
				cardImage = new ImageIcon("Pictures/Y1.png");
			}
			else if (getColor() == 3) {
				cardImage = new ImageIcon("Pictures/G1.png");
			}
			else if (getColor() == 4) {
				cardImage = new ImageIcon("Pictures/B1.png");
			}
		} else if (getValue()==2) {
			if (getColor() == 1) {
				cardImage = new ImageIcon("Pictures/R2.png");
			}
			else if (getColor() == 2) {
				cardImage = new ImageIcon("Pictures/Y2.png");
			}
			else if (getColor() == 3) {
				cardImage = new ImageIcon("Pictures/G2.png");
			}
			else if (getColor() == 4) {
				cardImage = new ImageIcon("Pictures/B2.png");
			}
		} else if (getValue()==3) {
			if (getColor() == 1) {
				cardImage = new ImageIcon("Pictures/R3.png");
			}
			else if (getColor() == 2) {
				cardImage = new ImageIcon("Pictures/Y3.png");
			}
			else if (getColor() == 3) {
				cardImage = new ImageIcon("Pictures/G3.png");
			}
			else if (getColor() == 4) {
				cardImage = new ImageIcon("Pictures/B3.png");
			}
		} else if (getValue()==4) {
			if (getColor() == 1) {
				cardImage = new ImageIcon("Pictures/R4.png");
			}
			else if (getColor() == 2) {
				cardImage = new ImageIcon("Pictures/Y4.png");
			}
			else if (getColor() == 3) {
				cardImage = new ImageIcon("Pictures/G4.png");
			}
			else if (getColor() == 4) {
				cardImage = new ImageIcon("Pictures/B4.png");
			}
		} else if (getValue()==5) {
			if (getColor() == 1) {
				cardImage = new ImageIcon("Pictures/R5.png");
			}
			else if (getColor() == 2) {
				cardImage = new ImageIcon("Pictures/Y5.png");
			}
			else if (getColor() == 3) {
				cardImage = new ImageIcon("Pictures/G5.png");
			}
			else if (getColor() == 4) {
				cardImage = new ImageIcon("Pictures/B5.png");
			}
		} else if (getValue()==6) {
			if (getColor() == 1) {
				cardImage = new ImageIcon("Pictures/R6.png");
			}
			else if (getColor() == 2) {
				cardImage = new ImageIcon("Pictures/Y6.png");
			}
			else if (getColor() == 3) {
				cardImage = new ImageIcon("Pictures/G6.png");
			}
			else if (getColor() == 4) {
				cardImage = new ImageIcon("Pictures/B6.png");
			}
		} else if (getValue()==7) {
			if (getColor() == 1) {
				cardImage = new ImageIcon("Pictures/R7.png");
			}
			else if (getColor() == 2) {
				cardImage = new ImageIcon("Pictures/Y7.png");
			}
			else if (getColor() == 3) {
				cardImage = new ImageIcon("Pictures/G7.png");
			}
			else if (getColor() == 4) {
				cardImage = new ImageIcon("Pictures/B7.png");
			}
		} else if (getValue()==8) {
			if (getColor() == 1) {
				cardImage = new ImageIcon("Pictures/R8.png");
			}
			else if (getColor() == 2) {
				cardImage = new ImageIcon("Pictures/Y8.png");
			}
			else if (getColor() == 3) {
				cardImage = new ImageIcon("Pictures/G8.png");
			}
			else if (getColor() == 4) {
				cardImage = new ImageIcon("Pictures/B8.png");
			}
		} else if (getValue()==9) {
			if (getColor() == 1) {
				cardImage = new ImageIcon("Pictures/R9.png");
			}
			else if (getColor() == 2) {
				cardImage = new ImageIcon("Pictures/Y9.png");
			}
			else if (getColor() == 3) {
				cardImage = new ImageIcon("Pictures/G9.png");
			}
			else if (getColor() == 4) {
				cardImage = new ImageIcon("Pictures/B9.png");
			}
		} else if (getValue()==10) {
			if (getColor() == 1) {
				cardImage = new ImageIcon("Pictures/RR.png");
			}
			else if (getColor() == 2) {
				cardImage = new ImageIcon("Pictures/YR.png");
			}
			else if (getColor() == 3) {
				cardImage = new ImageIcon("Pictures/GR.png");
			}
			else if (getColor() == 4) {
				cardImage = new ImageIcon("Pictures/BR.png");
			}
		} else if (getValue()==11) {
			if (getColor() == 1) {
				cardImage = new ImageIcon("Pictures/RS.png");
			}
			else if (getColor() == 2) {
				cardImage = new ImageIcon("Pictures/YS.png");
			}
			else if (getColor() == 3) {
				cardImage = new ImageIcon("Pictures/GS.png");
			}
			else if (getColor() == 4) {
				cardImage = new ImageIcon("Pictures/BS.png");
			}
		} else if (getValue()==12) {
			if (getColor() == 1) {
				cardImage = new ImageIcon("Pictures/R+2.png");
			}
			else if (getColor() == 2) {
				cardImage = new ImageIcon("Pictures/Y+2.png");
			}
			else if (getColor() == 3) {
				cardImage = new ImageIcon("Pictures/G+2.png");
			}
			else if (getColor() == 4) {
				cardImage = new ImageIcon("Pictures/B+2.png");
			}
		} else if (getValue()==13) {
			cardImage = new ImageIcon("Pictures/W+4.png");
		} else if (getValue()==14) {
			cardImage = new ImageIcon("Pictures/W.png");
		}
	}

	public int getColor() {
		return color;
	}

	public int getValue() {
		return value;
	}

	public ImageIcon getImage() {
		return cardImage;
	}

	public String toString() {
		String color, value;

		if (this.color == 1) {
			color = "Red";
		} else if (this.color == 2) {
			color = "Yellow";
		} else if (this.color == 3) {
			color = "Green";
		} else if (this.color == 4) {
			color = "Blue";
		} else {
			color = "Wild";
		}
		if (0 <= this.value && this.value <= 9) {
			value = Integer.toString(this.value);
		} else {
			if (this.value == REVERSE) {
				value = "Reverse";
			} else if (this.value == SKIP) {
				value = "Skip";
			} else if (this.value == PLUS_TWO) {
				value = "Plus Two";
			} else if (this.value == PLUS_FOUR) {
				value = "Plus Four";
			} else
				value = "Wild Card";
		}
		return color + " " + value;
	}

}
