import java.util.ArrayList;
import java.util.Collections;

public class Hand {

	private ArrayList<Card> cards;

	Hand() {
		setCards(new ArrayList<Card>());
	}

	public void addCard(Card card) {
		getCards().add(card);
	}

	public void removeCard(Card card) {
		if (getCards().size() > 0) {
			for (Card c : getCards()) {
				if (c.toString().equals(card.toString())) {
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

	public static void swap(ArrayList<Card> arr, int i, int j) {
		Card temp = arr.get(i);
		arr.set(i, arr.get(j));
		arr.set(j, temp);
	}

	public static int partition(ArrayList<Card> arr, int low, int high, boolean color) {
		if (color) {
			// pivot
			int pivot = arr.get(high).getColor();

			// Index of smaller element and
			// indicates the right position
			// of pivot found so far
			int i = (low - 1);

			for (int j = low; j <= high - 1; j++) {
				// If current element is smaller
				// than the pivot
				if (arr.get(j).getColor() < pivot) {
					// Increment index of
					// smaller element
					i++;
					swap(arr, i, j);
				}
			}
			swap(arr, i + 1, high);
			return (i + 1);
		} else {
			int pivot = arr.get(high).getValue();

			// Index of smaller element and
			// indicates the right position
			// of pivot found so far
			int i = (low - 1);

			for (int j = low; j <= high - 1; j++) {
				// If current element is smaller
				// than the pivot
				if (arr.get(j).getValue() < pivot) {
					// Increment index of
					// smaller element
					i++;
					swap(arr, i, j);
				}
			}
			swap(arr, i + 1, high);
			return (i + 1);
		}
	}

	/*
	 * The main function that implements QuickSort arr[] --> Array to be sorted, low
	 * --> Starting index, high --> Ending index
	 */
	static void quickSort(ArrayList<Card> arr, int low, int high, boolean color) {
		if (low < high) {
			int pi = partition(arr, low, high, color);
			quickSort(arr, low, pi - 1, color);
			quickSort(arr, pi + 1, high, color);
		}
	}

	static void printArray(ArrayList<Card> arr) {
		for (int i = 0; i < arr.size(); i++)
			System.out.println(arr.get(i));
		System.out.println();
	}

	static int[] findNumberOfEachColor(ArrayList<Card> arr) {
		int[] ans = new int[5];
		for (Card card : arr) {
			if (card.getColor() == Card.RED)
				ans[0]++;
			else if (card.getColor() == Card.YELLOW)
				ans[1]++;
			else if (card.getColor() == Card.GREEN)
				ans[2]++;
			else if (card.getColor() == Card.BLUE)
				ans[3]++;
			else
				ans[4]++;
		}
		return ans;
	}

	public static ArrayList<Card> sortCards(ArrayList<Card> arr) {
		quickSort(arr, 0, arr.size() - 1, true);
		int[] nums = findNumberOfEachColor(arr);
		int start = 0;
		int end = nums[0] - 1;
		int i = 0;
		while (end < arr.size() && i < 4) {
			quickSort(arr, start, end, false);
			start += nums[i];
			end += nums[i + 1];
			i++;
		}
		start += nums[i];
		quickSort(arr, start, arr.size() - 1, false);
		Collections.reverse(arr);
		return arr;
	}
}
