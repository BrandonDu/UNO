
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

	public Card chooseCard(Card topCard, boolean lastPlus) {
		System.out.println(topCard.getColor());
		System.out.println(topCard.getValue());
		for (Card card : hand.getCards()) {
			System.out.println(card.getColor());
			System.out.println(card.getValue());
			if ((card.getColor()==topCard.getColor())&&(card.getValue()==12)) {
				System.out.println("a");
				return card;
			} else if ((card.getColor()==topCard.getColor() && !lastPlus)||
					(card.getValue()==topCard.getValue())){
				System.out.println("b");
				return card;
			} else if ((card.getValue()==13)||(card.getValue()==14) && !lastPlus) {
				System.out.println("c");
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
}