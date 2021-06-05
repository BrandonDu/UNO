import java.util.ArrayList;

public class Hand {
	
	private ArrayList<Card> cards;
	
	Hand() {
		setCards(new ArrayList<Card>());
	}
	
	public void addCard(Card card) {
		getCards().add(card);
	}
	
	public void removeCard(Card card) {
		if(getCards().size()>0) {
			for(Card c: getCards()) {
				if(c.toString().equals(card.toString())) {
					getCards().remove(c);
					return;
				}
			}
		}
		getCards().remove(card);
	}
	
	public int numberOfCards() {
		return getCards().size();
	}
	
	public Card nthCard(int n) {
		return getCards().get(n);
	}

	public ArrayList<Card> getCards() {
		return cards;
	}

	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}
	
	
}
