package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import objects.Card;
import objects.Hand;

public class Game {

	public static Map<String, String> dictionary;

	public Game() {
		dictionary = new HashMap<>();
		buildDiccionary();
	}

	private void buildDiccionary() {
		dictionary.put("h", "Hearts");
		dictionary.put("d", "Diamonds");
		dictionary.put("s", "Spades");
		dictionary.put("c", "Clubs");
	}

	public String checkDraws(List<Card> hand) {

		String ret = "";

		int cont = 1;
		int maxCont = 0;
		boolean gutShot = false;

		for (int i = 0; i < hand.size() - 1; i++) {
			if (maxCont < 4) {
				if (hand.get(i).get_num() - 1 == hand.get(i + 1).get_num()) {
					cont++;
				} else if (!gutShot && hand.get(i).get_num() - 2 == hand.get(i + 1).get_num()) {
					gutShot = true;
					cont++;
				} else if (gutShot && (hand.get(i).get_num() - 1 != hand.get(i + 1).get_num()
						&& hand.get(i).get_num() != hand.get(i + 1).get_num())) {
					gutShot = false;
					cont = 1;
				}
			}
			if (cont > maxCont)
				maxCont = cont;
		}

		if (maxCont == 3 && hand.get(hand.size() - 1).get_num() == 3 && hand.get(0).get_num() == 14) {
			ret = "Straight GutShot";
		} else if (maxCont == 3 && hand.get(hand.size() - 1).get_num() == 2 && hand.get(0).get_num() == 14) {
			if (gutShot)
				ret = "Straight GutShot";
			else
				ret = "Straight Open-ended";
		}

		if (maxCont == 4) {
			if (gutShot)
				ret = "Straight GutShot";
			else
				ret = "Straight Open-ended";
		}

		return ret;
	}

	public ArrayList<Card> checkPair(List<Card> hand) {
		ArrayList<Card> ret = new ArrayList<>();
		for (int i = 0; i < hand.size() - 1; i++) {
			if (hand.get(i).get_num() == hand.get(i + 1).get_num()) {
				ret.add(hand.get(i));
				ret.add(hand.get(i + 1));
				return ret;
			}
		}
		return ret;
	}

	public ArrayList<Card> checkTwoPair(List<Card> hand) {
		ArrayList<Card> ret = new ArrayList<>();
		for (int i = 0; i < hand.size() - 1; i++) {
			if (hand.get(i).get_num() == hand.get(i + 1).get_num()) {
				ret.add(hand.get(i));
				ret.add(hand.get(i + 1));
				if (ret.size() == 4)
					return ret;
			}
		}

		if (ret.size() != 4) {
			ret.clear();
		}
		return ret;
	}

	public ArrayList<Card> checkThreeOfaKind(List<Card> hand) {
		ArrayList<Card> ret = new ArrayList<>();
		int cont = 1;
		for (int i = 0; i < hand.size() - 1; i++) {
			if (cont == 3) {
				ret.add(hand.get(i));
				return ret;
			}

			if (hand.get(i).get_num() == hand.get(i + 1).get_num()) {
				ret.add(hand.get(i));
				cont++;
			} else {
				ret.clear();
				cont = 1;
			}
		}

		if (ret.size() != 0 && ret.get(0).get_num() == hand.get(hand.size() - 1).get_num()) {
			ret.add(hand.get(hand.size() - 1));
		}

		if (ret.size() != 3) {
			ret.clear();
		}
		return ret;
	}

	public ArrayList<Card> checkStraight(List<Card> hand) {
		ArrayList<Card> ret = new ArrayList<>();

		ret.add(hand.get(0));
		for (int i = 0; i < hand.size() - 1; ++i) {
			if (ret.size() == 5) {
				return ret;
			} else if (hand.get(i).get_num() - 1 != hand.get(i + 1).get_num()
					&& hand.get(i).get_num() != hand.get(i + 1).get_num()) {
				ret.clear();
				ret.add(hand.get(i + 1));
			} else if (hand.get(i).get_num() - 1 == hand.get(i + 1).get_num()) {
				ret.add(hand.get(i + 1));
			}
		}

		if (ret.size() == 4 && (hand.get(0).get_num() == 14 && ret.get(ret.size() - 1).get_num() == 2)) {
			ret.add(hand.get(0));
		} else if (ret.size() < 5)
			ret.clear();

		return ret;
	}

	public ArrayList<Card> checkFlush(List<Card> hand) {
		ArrayList<Card> ret = new ArrayList<>();
		ArrayList<Card> h = new ArrayList<>();

		for (Card c : hand)
			h.add(c);

		Hand.sortByColour(h);

		int counter = 1;
		for (int i = 0; i < h.size() - 1; i++) {

			if (h.get(i).getSuit().equals(h.get(i + 1).getSuit())) {
				ret.add(h.get(i));
				counter++;
			} else {
				ret.clear();
				counter = 1;
			}

			if (counter == 5) {
				ret.add(h.get(i + 1));
				return ret;
			}
		}

//		if (counter == 4) {
//			ret.clear();
//			ret.add(hand.get(counter));
//			return ret;// flush draw
//		} else if (counter < 4)
		
		ret.clear();

		return ret;
	}

	public ArrayList<Card> checkFull(List<Card> hand) {
		ArrayList<Card> ret = new ArrayList<>();
		int cont = 0;

		ret.addAll(checkThreeOfaKind(hand));

		if (ret.size() != 0) {

			ret.add(hand.get(0));
			int i = 1;

			while (i < hand.size() && cont != 1) {

				if ((ret.get(0).get_num() != hand.get(i).get_num())
						&& (ret.get(3).get_num() == hand.get(i).get_num())) {
					cont++;
					ret.add(hand.get(i));
				} else {
					ret.remove(ret.size() - 1);
					ret.add(hand.get(i));
				}
				++i;
			}

		}

		if (ret.size() != 5) {
			ret.clear();
		}

		return ret;
	}

	public ArrayList<Card> checkPoker(List<Card> hand) {
		ArrayList<Card> ret = new ArrayList<>();
		int cont = 1;
		for (int i = 0; i < hand.size() - 1; i++) {
			if (cont == 4) {
				ret.add(hand.get(i));
				return ret;
			}
			if (hand.get(i).get_num() == hand.get(i + 1).get_num()) {
				ret.add(hand.get(i));
				cont++;
			} else {
				ret.clear();
				cont = 1;
			}
		}
		ret.clear();
		return ret;
	}

	public List<Card> checkStraightFlush(List<Card> hand) {
		List<Card> ret = new ArrayList<>();

		ret.add(hand.get(0));
		for (int i = 0; i < hand.size() - 1; ++i) {
			if (hand.get(i).get_num() - 1 != hand.get(i + 1).get_num()
					|| !hand.get(i).get_suit().equals(hand.get(i + 1).get_suit())) {
				ret.clear();
			}
			ret.add(hand.get(i + 1));
		}

		if (ret.size() == 4 && ((hand.get(0).get_num() == 14 && ret.get(ret.size() - 1).get_num() == 2))) {
			int j = 0;

			while (j < hand.size() - 1) {
				if (hand.get(j).get_num() == 14 && hand.get(j).get_suit().equals(ret.get(0).get_suit())) {
					ret.add(hand.get(j));
					return ret;
				}
				j++;
			}

		} else if (ret.size() < 5)
			ret.clear();

		return ret;
	}

	public int checkHand(List<Card> hand, List<Card> myCards, List<Card> board) {

		List<Card> a = new ArrayList<>();
		String aux = "";

		Hand.quickSort(hand, 0, hand.size() - 1);
		Hand.quickSort(board, 0, board.size() - 1);

		a = checkStraightFlush(hand);
		if (!a.isEmpty()) {
			for(Card c : myCards) {
				if(a.contains(c))
					return 0;
			}
		}

		a = checkPoker(hand);
		if (!a.isEmpty() && aux.equals("")) {
			for(Card c : myCards) {
				if(a.contains(c))
					return 1;
			}
		}

		a = checkFull(hand);
		if (!a.isEmpty() && aux.equals("")) {
			for(Card c : myCards) {
				if(a.contains(c))
					return 2;
			}
		}

		a = checkFlush(hand);

		if (!a.isEmpty() && aux.equals("")) {
			for(Card c : myCards) {
				if(a.contains(c))
					return 3;
			}
		}

		a = checkStraight(hand);
		if (!a.isEmpty() && aux.equals("")) {
			for(Card c : myCards) {
				if(a.contains(c))
					return 4;
			}
		}

		a = checkThreeOfaKind(hand);
		if (!a.isEmpty() && aux.equals("")) {
			for(Card c : myCards) {
				if(a.contains(c))
					return 5;
			}
		}

		a = checkTwoPair(hand);
		if (!a.isEmpty() && aux.equals("")) {
				if(a.get(0).get_num() == myCards.get(0).get_num() && a.get(2).get_num() == myCards.get(1).get_num())
					return 6;
				else if(a.contains(myCards.get(0)) || a.contains(myCards.get(1)))
					return 13;
		}

		a = checkPair(hand);
		if (!a.isEmpty() && aux.equals("")) {
			for(Card c : myCards) {
				if(a.contains(c)) {
					if(checkOverPair(board, myCards))
						return 7;
					else if(checkTopPair(board, a))
						return 8;
					else if(checkPPBelowTP(board, myCards, hand))
						return 9;
					else if(checkMiddlePair(board, myCards))
						return 10;
				
					return 11;
				}

			}
		}
		
		if(checkAceHigh(myCards))
			return 12;

		return 13;
	}
	
	public boolean checkOverPair(List<Card> hand, List<Card> myCards) {
	    Card highestCard = hand.get(0);
	    return myCards.get(0).get_num() == myCards.get(1).get_num() && myCards.get(0).get_num() > highestCard.get_num();
	}
	
	public boolean checkTopPair(List<Card> board, List<Card> myCards) {
		Card highestCard = board.get(0);
	   return myCards.get(0) == highestCard || myCards.get(1) == highestCard;
	}
	
	public boolean checkPPBelowTP(List<Card> hand, List<Card> myCards, List<Card> a) {
	    Card highestCard = hand.get(0);
	    List<Card> aux = checkPair(hand);
	    
	    if(myCards.get(0).get_num() == myCards.get(1).get_num() && myCards.get(0).get_num() < highestCard.get_num()) {
	    	if(!aux.isEmpty() && myCards.get(0).get_num() > aux.get(0).get_num()) {
	    		return true;
	    	}
	    	else if(aux.isEmpty()) {
	    		return true;
	    	}
	    }
	    
	    return false;
	}
	
	public boolean checkMiddlePair(List<Card> hand, List<Card> myCards) {
	    Card HighestCard = hand.get(0);
	    Card secondHighestCard = null;
	    int i = 1;
	    boolean found = false;
	    
	    while(!found && i < hand.size()) {
	    	if(!hand.get(i).equals(HighestCard)) {
	    		secondHighestCard = hand.get(i);
	    		found = true;
	    	}
	    	i++;
	    }
	    
	    if(secondHighestCard == null)
	    	return false;
	    else
	    	return myCards.get(0) == secondHighestCard || myCards.get(1) == secondHighestCard;
	}
	
	public boolean checkAceHigh(List<Card> hand) {
	    return hand.get(0).get_num() == 14 || hand.get(0).get_num() == 14;
	}




	
	


//	public String checkHand(ArrayList<Card> hand) {
//
//		ArrayList<Card> a = new ArrayList<>();
//		String aux = "";
//		String s = "";
//
//		a = checkStraightFlush(hand);
//		if (!a.isEmpty()) {
//			for (Card c : a) {
//				aux += c.get_id();
//			}
//
//			aux += " (Straight Flush)";
//		}
//
//		a = checkPoker(hand);
//		if (!a.isEmpty() && aux.equals("")) {
//			for (Card c : a) {
//				aux += c.get_id();
//			}
//
//			int cont = 0;
//			while (cont != 7) {
//				if (!hand.get(cont).get_value().equals(a.get(0).get_value())) {
//					aux += hand.get(cont).get_id();
//					cont = 6;
//				}
//				++cont;
//			}
//
//			aux += " (Quads)";
//		}
//
//		a = checkFull(hand);
//		if (!a.isEmpty() && aux.equals("")) {
//			for (Card c : a) {
//				aux += c.get_id();
//			}
//			aux += " (Full House)";
//		}
//
//		a = checkFlush(hand);
//		if (!a.isEmpty() && aux.equals("")) {
//			for (Card c : a) {
//				aux += c.get_id();
//			}
//			aux += " (Flush)";
//		}
//
//		a = checkStraight(hand);
//		if (!a.isEmpty() && aux.equals("")) {
//			for (Card c : a) {
//				aux += c.get_id();
//			}
//			aux += " (Straight)";
//		}
//
//		a = checkThreeOfaKind(hand);
//		if (!a.isEmpty() && aux.equals("")) {
//			for (Card c : a) {
//				aux += c.get_id();
//			}
//
//			int cont = 0;
//			int cont2 = 0;
//			while (cont != 7 && cont2 != 2) {
//				if (!hand.get(cont).get_value().equals(a.get(0).get_value())) {
//					aux += hand.get(cont).get_id();
//					++cont2;
//				}
//				++cont;
//			}
//
//			aux += " (Three of " + a.get(0).toString() + (a.get(0).get_num() == 6 ? "es" : "s") + ")";
//		}
//
//		a = checkTwoPair(hand);
//		if (!a.isEmpty() && aux.equals("")) {
//			for (Card c : a) {
//				aux += c.get_id();
//			}
//
//			int cont = 0;
//			while (cont != 7) {
//				if ((!hand.get(cont).get_value().equals(a.get(0).get_value()))
//						&& (!hand.get(cont).get_value().equals(a.get(2).get_value()))) {
//					aux += hand.get(cont).get_id();
//					cont = 6;
//				}
//				++cont;
//			}
//
//			aux += " (Two pair of " + a.get(0).toString() + (a.get(0).get_num() == 6 ? "es" : "s") + " and "
//					+ a.get(a.size() - 1).toString() + (a.get(a.size() - 1).get_num() == 6 ? "es" : "s") + ")";
//		}
//
//		a = checkPair(hand);
//		if (!a.isEmpty() && aux.equals("")) {
//			for (Card c : a) {
//				aux += c.get_id();
//			}
//
//			int cont = 0;
//			int cont2 = 0;
//			while (cont != 7 && cont2 != 3) {
//				if (!hand.get(cont).get_value().equals(a.get(0).get_value())) {
//					aux += hand.get(cont).get_id();
//					++cont2;
//				}
//				++cont;
//			}
//
//			aux += " (Pair of " + a.get(0).toString() + (a.get(0).get_num() == 6 ? "es" : "s") + ")";
//		}
//
//		if (aux.equals("")) {
//			if (hand.get(hand.size() - 1).get_num() == 1)
//				a.add(hand.get(hand.size() - 1));
//			else
//				a.add(hand.get(0));
//
//			int cont = 1;
//
//			while (cont != 5) {
//				aux += hand.get(cont).get_id();
//				++cont;
//			}
//
//			aux += " (High card: " + a.get(0).toString() + ")";
//		}
//
//		return aux;
//	}

}
