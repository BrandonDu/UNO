
public class Game {
	private Hand playerHand;
	private Hand deck;
	private ComputerStrategy cp1;
	private ComputerStrategy cp2;
	private Card startingCard;
	private int stackValue;
	private int turn;
	
	Game(boolean multiplayer) {
		
		deck = Game.makeDeck();
		
		playerHand = new Hand();
		cp1 = new ComputerStrategy();
		
		if(multiplayer) {
			cp2 = new ComputerStrategy();
		}
		
		for(int i=0; i<7; i++) {
			dealCard(playerHand);
		}
		
		for(int i=0; i<7; i++) {
			dealCard(cp1.getHand());
		}
		
		if(multiplayer) {
			dealCard(cp2.getHand());
		}
		
		turn = 0;
		
		startingCard = deal();
	}
	
	public static Hand makeDeck() {
		deck.addCard(new Card(0, 1));
		for (int i=1; i<13; i++) {
			for (int x=0; x<2; x++) {
				deck.addCard(new Card (i, 1));
			}
		}
		deck.addCard(new Card(0, 2));
		for (int i=1; i<13; i++) {
			for (int x=0; x<2; x++) {
				deck.addCard(new Card (i, 2));
			}
		}
		deck.addCard(new Card(0, 3));
		for (int i=1; i<13; i++) {
			for (int x=0; x<2; x++) {
				deck.addCard(new Card (i, 3));
			}
		}
		deck.addCard(new Card(0, 4));
		for (int i=1; i<13; i++) {
			for (int x=0; x<2; x++) {
				deck.addCard(new Card (i, 4));
			}
		}
		for (int x=0; x<2; x++) {
			deck.addCard(newCard (13, 5));
			deck.addCard(newCard (14, 5));
		}
		return deck;
	}
	
	public void dealCard(Hand hand) {
		int randIndex = (int) (deck.numberOfCards() * Math.random());
		
		hand.addCard(deck.nthCard(randIndex));
		deck.removeCard(deck.nthCard(randIndex));
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
		turn = (turn+1)%3;
		if(skip)
			turn = (turn+1)%3;
	}
	
	
}
