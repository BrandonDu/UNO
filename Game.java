
public class Game {
	private Hand playerHand;
	private static Hand deck;
	private ComputerStrategy cp1;
	private ComputerStrategy cp2;
	private Card startingCard;
	private int turn;

	Game(boolean multiplayer) {
		setDeck(Game.makeDeck());

		setPlayerHand(new Hand());
		setCp1(new ComputerStrategy());

		if (multiplayer) {
			cp2 = new ComputerStrategy();
		}

		for (int i = 0; i < 7; i++) {
			dealCard(getPlayerHand());
		}

		for (int i = 0; i < 7; i++) {
			dealCard(getCp1().getHand());
		}

		if (multiplayer) {
			dealCard(cp2.getHand());
		}

		turn = 0;

		setStartingCard();
	}

	public static Hand makeDeck() {
		setDeck(new Hand());
		getDeck().addCard(new Card(1, 0));
		for (int i = 1; i < 13; i++) {
			for (int j = 0; j < 2; j++) {
				getDeck().addCard(new Card(1, i));
			}
		}
		getDeck().addCard(new Card(2, 0));
		for (int i = 1; i < 13; i++) {
			for (int j = 0; j < 2; j++) {
				getDeck().addCard(new Card(2, i));
			}
		}
		getDeck().addCard(new Card(3, 0));
		for (int i = 1; i < 13; i++) {
			for (int j = 0; j < 2; j++) {
				getDeck().addCard(new Card(3, i));
			}
		}
		getDeck().addCard(new Card(4, 0));
		for (int i = 1; i < 13; i++) {
			for (int j = 0; j < 2; j++) {
				getDeck().addCard(new Card(4, i));
			}
		}
		for (int i = 0; i < 2; i++) {
			getDeck().addCard(new Card(5, 13));
			getDeck().addCard(new Card(5, 14));
		}
		return getDeck();
	}

	public void dealCard(Hand hand) {
		int randIndex = (int) (getDeck().numberOfCards() * Math.random());
		hand.addCard(getDeck().nthCard(randIndex));
		getDeck().removeCard(getDeck().nthCard(randIndex));
	}

	public Card deal() {
		int randIndex = (int) (getDeck().numberOfCards() * Math.random());
		getDeck().removeCard(getDeck().nthCard(randIndex));
		return getDeck().nthCard(randIndex);
	}

	public int getTurn() {
		return turn;
	}

	public void changeTurn(boolean skip) {
		turn = (turn + 1) % 3;
		if (skip)
			turn = (turn + 1) % 3;
	}

	public Hand getPlayerHand() {
		return playerHand;
	}

	public void setPlayerHand(Hand playerHand) {
		this.playerHand = playerHand;
	}

	public ComputerStrategy getCp1() {
		return cp1;
	}

	public void setCp1(ComputerStrategy cp1) {
		this.cp1 = cp1;
	}

	public Card getStartingCard() {
		return startingCard;
	}

	public void setStartingCard() {
		int randIndex = (int) (getDeck().numberOfCards() * Math.random());
		Card card = getDeck().nthCard(randIndex);
		while(card.getValue()>9 || card.getColor()==Card.WILD) {
			randIndex = (int) (getDeck().numberOfCards() * Math.random());
			card = getDeck().nthCard(randIndex);
		}
		this.startingCard = card;
		getDeck().removeCard(card);
	}

	public static Hand getDeck() {
		return deck;
	}

	public static void setDeck(Hand deck) {
		Game.deck = deck;
	}

}
