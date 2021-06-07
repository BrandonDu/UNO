
public class Game {
	private Hand playerHand;
	private Hand deck;
	private ComputerStrategy cp1;
	private ComputerStrategy cp2;
	private Card startingCard;
	private Card topCard;
	private int turn;

	Game(boolean multiplayer) {
		setDeck(this.makeDeck());

		setPlayerHand(new Hand());
		setCp1(new ComputerStrategy());

		if (multiplayer) {
			cp2 = new ComputerStrategy();
		}

		for (int i = 0; i < 7; i++) {
			dealCard(playerHand);
		}
		playerHand.addCard(new Card(5,14));

		for (int i = 0; i < 7; i++) {
			dealCard(getCp1().getHand());
		}

		if (multiplayer) {
			dealCard(cp2.getHand());
		}

		turn = 0;

		setStartingCard();
		topCard = startingCard;
	}

	public Hand makeDeck() {
		deck = new Hand();
		deck.addCard(new Card(1, 0));
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

	public ComputerStrategy getCp2() {
		return cp2;
	}

	public void setCp2(ComputerStrategy cp2) {
		this.cp2 = cp2;
	}

	public Card getTopCard() {
		return topCard;
	}

	public void setTopCard(Card topCard) {
		this.topCard = topCard;
	}

	public void setStartingCard(Card startingCard) {
		this.startingCard = startingCard;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public void dealCard(Hand hand) {
		int randIndex = (int) (deck.numberOfCards() * Math.random());
		hand.addCard(deck.nthCard(randIndex));
		getDeck().removeCard(deck.nthCard(randIndex));
	}

	public Card deal() {
		int randIndex = (int) (deck.numberOfCards() * Math.random());
		deck.removeCard(deck.nthCard(randIndex));
		return deck.nthCard(randIndex);
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
		int randIndex = (int) (deck.numberOfCards() * Math.random());
		Card card = deck.nthCard(randIndex);
		while(card.getValue()>9 || card.getColor()==Card.WILD) {
			randIndex = (int) (deck.numberOfCards() * Math.random());
			card = deck.nthCard(randIndex);
		}
		this.startingCard = card;
		deck.removeCard(card);
	}

	public Hand getDeck() {
		return deck;
	}

	public void setDeck(Hand deck) {
		this.deck = deck;
	}

	public boolean playCard(Hand hand, Card card) {
		if(card.getColor()==Card.WILD) {
			hand.removeCard(card);
			this.setTopCard(card);
			return true;
		}
		else if(card.getColor()==topCard.getColor() || card.getValue()==topCard.getValue()) {
			hand.removeCard(card);
			this.setTopCard(card);
			return true;
		}
		return false;
	}

}
