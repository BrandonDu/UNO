
public class Game {
	private Hand playerHand;
	private Hand deck;
	private ComputerStrategy cp1;
	private ComputerStrategy cp2;
	private Card startingCard;
	private Card topCard;
	private int turn;
	private int totalPlus;
	private boolean multiplayer;
	private boolean clockwise;

	Game(boolean multiplayer) {
		this.multiplayer = multiplayer;
		setDeck(this.makeDeck());

		setPlayerHand(new Hand());
		setCp1(new ComputerStrategy());

		if (multiplayer) {
			cp2 = new ComputerStrategy();
		}

		for (int i = 0; i < 7; i++) {
			dealCard(playerHand);
		}

		for (int i = 0; i < 7; i++) {
			dealCard(cp1.getHand());
		}

		if (multiplayer) {
			for (int i = 0; i < 7; i++) {
				dealCard(cp2.getHand());
			}
		}

		turn = 0;
		totalPlus = 0;
		setStartingCard();
		topCard = startingCard;
		clockwise = true;
	}

	public int getTotalPlus() {
		return totalPlus;
	}

	public void setTotalPlus(int totalPlus) {
		this.totalPlus = totalPlus;
	}

	public boolean isMultiplayer() {
		return multiplayer;
	}

	public void setMultiplayer(boolean multiplayer) {
		this.multiplayer = multiplayer;
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

	public int dealCard(Hand hand) {
		for(int i=0; i<totalPlus + 1; i++) {
			int randIndex = (int) (deck.numberOfCards() * Math.random());
			hand.addCard(deck.nthCard(randIndex));
			getDeck().removeCard(deck.nthCard(randIndex));
			changeTurn(false);
		}
		int tBuffer = totalPlus + 1;
		totalPlus = 0;

		return tBuffer;
	}

	public int getTurn() {
		return turn;
	}

	public void changeTurn(boolean skip) {
		if (!multiplayer) {
			turn = (turn + 1) % 2;
			if (skip)
				turn = (turn + 1) % 2;
		} else {
			turn = (turn + 1) % 3;
			if (skip && clockwise)
				turn = (turn + 1) % 3;
		}

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
		while (card.getValue() > 9 || card.getColor() == Card.WILD) {
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

		if (!multiplayer) {
			if (turn == 0 && hand == playerHand || turn == 1 && hand == cp1.getHand()) {
				if ((card.getColor() == Card.WILD || card.getValue() == Card.WILD_CARD) 
						&& (topCard.getValue()!=Card.PLUS_TWO && topCard.getValue() !=Card.PLUS_FOUR)) {
					hand.removeCard(card);
					this.setTopCard(card);
					if (card.getValue() == 10)
						changeTurn(true);
					else if (card.getValue() == 11) {
						changeTurn(true);
						clockwise = false;
					} else
						changeTurn(false);
					totalPlus = 0;
					return true;
				} else if ((card.getColor() == topCard.getColor() && topCard.getValue()!=Card.PLUS_TWO && topCard.getValue()!=Card.PLUS_FOUR) || card.getValue() == topCard.getValue()) {
					if (card.getValue() == Card.PLUS_TWO)
						totalPlus += 2;
					else if (card.getValue() == Card.PLUS_FOUR)
						totalPlus += 4;
					else
						totalPlus = 0;
					hand.removeCard(card);
					this.setTopCard(card);
					if (card.getValue() == 10)
						changeTurn(true);
					else if (card.getValue() == 11) {
						changeTurn(true);
						clockwise = false;
					} else
						changeTurn(false);
					return true;
				}
			}
			return false;
		} else {
			if (turn == 0 && hand == playerHand || turn == 1 && hand == cp1.getHand()
					|| turn == 2 && hand == cp2.getHand()) {
				if (card.getColor() == Card.WILD) {
					hand.removeCard(card);
					this.setTopCard(card);
					changeTurn(false);
					return true;
				} else if (card.getColor() == topCard.getColor() || card.getValue() == topCard.getValue()) {
					if (card.getValue() == Card.PLUS_TWO)
						totalPlus += 2;
					if (card.getValue() == Card.PLUS_FOUR)
						totalPlus += 4;
					hand.removeCard(card);
					this.setTopCard(card);
					changeTurn(false);
					return true;
				}
			}
			return false;
		}
	}

}
