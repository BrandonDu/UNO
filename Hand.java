import java.util.ArrayList;

public class Hand {
	
	private ArrayList<Card> cards;
	
	Hand() {
		cards = new ArrayList<Card>();
	}
	
	public void addCard(Card card) {
		cards.add(card);
	}
	
	public void removeCard(Card card) {
		if(cards.size()>0) {
			for(Card c: cards) {
				if(c.toString().equals(card.toString())) {
					cards.remove(c);
					return;
				}
			}
		}
		cards.remove(card);
	}
	
	public int numberOfCards() {
		return cards.size();
	}
	
	public Card nthCard(int n) {
		return cards.get(n);
	}
	
}
