
public class Card {

	public static final int RED = 1;
	public static final int YELLOW = 2;
	public static final int GREEN = 3;
	public static final int BLUE = 4;

	public static final int REVERSE = 10;
	public static final int SKIP = 11;
	public static final int PLUS_TWO = 12;
	public static final int PLUS_FOUR = 13;
	public static final int WILD_CARD = 14;

	private int color;
	private int value;

	Card(int color, int value) {
		this.color = color;
		this.value = value;
	}

	Card(String color, String value) {
		if(color.toLowerCase().equals("red")) {
			this.color = RED;
		}
		else if(color.toLowerCase().equals("yellow")) {
			this.color = YELLOW;
		}
		else if(color.toLowerCase().equals("green")) {
			this.color = GREEN;
		}
		else if(color.toLowerCase().equals("blue")) {
			this.color = BLUE;
		}
		
		try {
			this.value = Integer.parseInt(value);
		}
		catch(NumberFormatException ex) {
			if(value.toLowerCase().equals("reverse")) {
				this.value = REVERSE;
			}
			else if(value.toLowerCase().equals("skip")) {
				this.value = SKIP;
			}
			else if(value.toLowerCase().equals("plus two")) {
				this.value = PLUS_TWO;
			}
			else if(value.toLowerCase().equals("plus four")) {
				this.value = PLUS_FOUR;
			}
			else if(value.toLowerCase().equals("wild card")) {
				this.value = WILD_CARD;
			}
		}
	}

	public int getColor() {
		return color;
	}

	public int getValue() {
		return value;
	}

	public String toString() {
		String color, value;
		
		if(this.color==1) {
			color = "Red";
		}
		else if(this.color==2) {
			color = "Yellow";
		}
		else if(this.color==3) {
			color = "Green";
		}
		else 
			color = "Blue";
		
		if(0<=this.value && this.value<=9) {
			value = Integer.toString(this.value);
		}
		else {
			if(this.value == REVERSE) {
				value = "Reverse";
			}
			else if(this.value == SKIP) {
				value = "Skip";
			}
			else if(this.value == PLUS_TWO) {
				value = "Plus Two";
			}
			else if(this.value == PLUS_FOUR) {
				value = "Plus Four";
			}
			else 
				value = "Wild Card";
		}
		return color + " " + value;	
	}
}
