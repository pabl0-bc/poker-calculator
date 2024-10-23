package objects;

import java.util.ArrayList;
import java.util.List;


public class Hand {

	public static int handSize = 5;
	private String[] _cards;
	private ArrayList<Card> _hand;

	public Hand(String[] cards) {
		_hand = new ArrayList<>();
		_cards = cards;
	}

	public void addCards() {
		String suit;
		String value;
		for (int i = 0; i < _cards.length; i++) {
			String v = _cards[i].substring(0, 1);
			value = v;
			String s = _cards[i].substring(1, 2);
			suit = s;

			_hand.add(new Card(value, suit, _cards[i]));
		}

		quickSort(_hand, 0, _hand.size() - 1);		
	}

	public static void quickSort(List<Card> hand, int izq, int der) {

		Card pivote = hand.get(izq);
		int i = izq; 
		int j = der; 
		Card aux;

		while (i < j) { 
			while (hand.get(i).get_num() >= pivote.get_num() && i < j)
				i++; 
			while (hand.get(j).get_num() < pivote.get_num())
				j--; 
			if (i < j) { 
				aux = hand.get(i);
				hand.set(i, hand.get(j));
				hand.set(j, aux);
			}
		}

		hand.set(izq, hand.get(j)); 
		hand.set(j, pivote);

		if (izq < j - 1)
			quickSort(hand, izq, j - 1); 
		if (j + 1 < der)
			quickSort(hand, j + 1, der);
	}

	public ArrayList<Card> getHand() {
		return _hand;
	}
	
	public static final void sortByColour(ArrayList<Card> hand) {
		Card aux;
		
		for(int i = 0; i < hand.size(); i++) {
			for(int j = i + 1; j < hand.size(); j++) {
				if(hand.get(i).getSuit().compareTo(hand.get(j).getSuit()) < 0) {
					aux = hand.get(i);
					hand.set(i, hand.get(j));
					hand.set(j, aux);
				}
			}
		}
		
		for (int i = 0; i < hand.size() - 1; i++) {
			for (int j = i + 1; j < hand.size(); j++) {
				if (hand.get(j).get_num() > hand.get(i).get_num() && hand.get(j).get_suit().equals(hand.get(i).get_suit())) {
					aux = hand.get(i);
					hand.set(i, hand.get(j));
					hand.set(j, aux);
				}
			}
		}
		
	}

}
