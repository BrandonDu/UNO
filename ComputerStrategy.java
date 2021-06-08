
public class ComputerStrategy {
	private Hand hand;

	ComputerStrategy() {
		hand = new Hand();
	}

	public Hand getHand() {
		return hand;
	}

	public void setHand(Hand hand) {
		this.hand = hand;
	}

	public Card chooseCard(Card topCard) {
		for (Card card : hand.getCards()) {
			if ((card.getColor()==topCard.getColor())&&(card.getValue()==12)) {
				hand.getCards().remove(card);
				return card;
			} else if ((card.getColor()==topCard.getColor())||
					(card.getValue()==topCard.getValue())){
				hand.getCards().remove(card);
				return card;
			} else if ((card.getValue()==13)||(card.getValue()==14)) {
				hand.getCards().remove(card);
				return card;
			}
		}
		return null;
	}

	public int chooseColor() {
		int color = 1;
		int track = 0;
		for (int i=0; i<4; i++) {
			if (Hand.findNumberOfEachColor(hand.getCards())[i]>track) {
				color = i+1;
				track = Hand.findNumberOfEachColor(hand.getCards())[i];
			}
		}
		return color;
	}
	
	public static void main (String[] args) {
		ComputerStrategy test = new ComputerStrategy();
		test.hand.addCard(new Card(1, 1));
		test.hand.addCard(new Card(5, 14));
		test.chooseCard(new Card(3, 2));
	}
}