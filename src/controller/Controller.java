package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import logic.CombinationGenerator;
import logic.Game;
import objects.Card;
import objects.Hand;
import objects.Pair;
import objects.Statics;

public class Controller {

	private Map<String, List<String>> staticsMap;
	private Game _game;
	private String[] suits = { "d", "c", "s", "h" };
	private String[] ret = { "Straight flush", "Quads", "Full", "Flush", "Straight", "3 of a kind", "Two pair",
			"Over pair", "Top pair", "pp below tp", "Middle pair", "Weak pair", "Ace high", "No made hand" };
	private int num = 0;
	private int totComb = 0;
	private Card highBoardCard;

	public Controller(Game game) {
		_game = game;
	}

	public Map<String, List<String>> checkAll(List<String> range, List<Card> board) {

		int bestHand = 20;
		int hand = 20;
		List<Card> topPair;
		num = 0;
		boolean ok = true;
		staticsMap = new LinkedHashMap<>();
		initMap();

		Hand.quickSort(board, 0, board.size() - 1);
		highBoardCard = board.get(0);

		List<Card> cards = new ArrayList<>();
		for (String r : range) {
			if (r.contains("s")) {
				for (String s : suits) {
					cards.add(new Card("" + r.charAt(0), s, "" + r.charAt(0) + s));
					cards.add(new Card("" + r.charAt(1), s, "" + r.charAt(1) + s));
					List<Card> allCards = new ArrayList<>(board);
					if (allCards.contains(cards.get(0)) || allCards.contains(cards.get(1))) {
						ok = false;
					}

					allCards.addAll(cards);

					if (allCards.size() >= 5 && ok) {
						List<List<Card>> allComb = generarCombinaciones(allCards, 5);
						totComb += allComb.size();
						for (List<Card> c : allComb) {
							hand = _game.checkHand(c, cards, board);

							if (hand < bestHand) {
								bestHand = hand;
							}
						}

						staticsMap.get(ret[bestHand]).add(r);

						num++;
					}
					cards.clear();
					allCards.clear();
					hand = 20;
					ok = true;
				}
				bestHand = 20;

			} else if (r.contains("o")) {

				for (int i = 0; i < 4; i++) {
					for (int j = 0; j < 4; j++) {
						if (i != j) {
							cards.add(new Card("" + r.charAt(0), suits[i], "" + r.charAt(0) + suits[i]));
							cards.add(new Card("" + r.charAt(1), suits[j], "" + r.charAt(1) + suits[j]));
							List<Card> allCards = new ArrayList<>(board);
							if (allCards.contains(cards.get(0)) || allCards.contains(cards.get(1))) {
								ok = false;
							}

							allCards.addAll(cards);

							if (allCards.size() >= 5 && ok) {
								List<List<Card>> allComb = generarCombinaciones(allCards, 5);
								totComb += allComb.size();
								for (List<Card> c : allComb) {
									hand = _game.checkHand(c, cards, board);

									if (hand < bestHand) {
										bestHand = hand;
									}
								}

								staticsMap.get(ret[bestHand]).add(r);

								num++;
							}
							cards.clear();
							allCards.clear();
							hand = 20;
							ok = true;
						}

					}
					bestHand = 20;
				}

			} else {
				for (int i = 0; i < 3; i++) {
					for (int j = i + 1; j < 4; j++) {
						cards.add(new Card("" + r.charAt(0), suits[i], "" + r.charAt(0) + suits[i]));
						cards.add(new Card("" + r.charAt(1), suits[j], "" + r.charAt(1) + suits[j]));
						List<Card> allCards = new ArrayList<>(board);
						if (allCards.contains(cards.get(0)) || allCards.contains(cards.get(1))) {
							ok = false;
						}

						allCards.addAll(cards);

						if (allCards.size() >= 5 && ok) {
							List<List<Card>> allComb = generarCombinaciones(allCards, 5);
							totComb += allComb.size();
							for (List<Card> c : allComb) {
								hand = _game.checkHand(c, cards, board);

								if (hand < bestHand) {
									bestHand = hand;
								}
							}

							staticsMap.get(ret[bestHand]).add(r);

							num++;
						}
						cards.clear();
						allCards.clear();
						hand = 20;
						ok = true;
					}
					bestHand = 20;
				}
			}
		}
		
		for (Map.Entry<String, List<String>> entry : staticsMap.entrySet()) {
		    entry.getValue().sort((pair1, pair2) -> {
		        return comparePokerHands(pair2, pair1);
		    });
		}

		staticsMap.put("total", new ArrayList<>());
		staticsMap.get("total").add("" + num);
		return staticsMap;
	}
	
	private int comparePokerHands(String hand1, String hand2) {
	    String[] pokerHandRankings = {"AA", "KK", "AKs", "QQ", "AKo", "JJ", "AQs", "TT", "AQo", "99", "AJs", "88", "ATs", "AJo", "77", "66", "ATo", "A9s", "55", "A8s", "KQs", "44", "A9o", "A7s", "KJs", "A5s", "A8o", "A6s", "A4s", "33", "KTs", "A7o", "A3s", "KQo", "A2s", "A5o", "A6o", "A4o", "KJo", "QJs", "A3o", "22", "K9s", "A2o", "KTo", "QTs", "K8s", "K7s", "K9o", "JTs", "QJo", "K6s", "Q9s", "K5s", "K8o", "K4s", "QTo", "K7o", "K3s", "Q8s", "K2s", "K6o", "J9s", "K5o", "Q9o", "JTo", "K4o", "Q7s", "T9s", "Q6s", "K3o", "Q5s", "K2o", "Q8o", "Q4s", "J9o", "T8s", "J7s", "Q3s", "Q7o", "Q2s", "Q6o", "98s", "Q5o", "T9o", "J8o", "J6s", "J5s", "T7s", "Q4o", "J7o", "J4s", "J3s", "J2s", "T6s", "T8o", "J7o", "J6o", "Q4o", "Q2o", "J5o", "T7o", "98o", "97o", "T5s", "T4s", "T3s", "T2s", "97s", "96s", "87s", "86s", "95s", "76s", "J4o", "J3o", "J2o", "T6o", "T5o", "T4o", "T3o", "T2o", "94s", "93s", "92s", "85s", "84s", "83s", "82s", "76s", "75s", "74s", "73s", "72s", "87o", "96o", "95o", "94o", "93o", "92o", "87o", "86o", "85o", "84o", "83o", "82o", "65s", "64s", "63s", "62s", "76o", "75o", "74o", "73o", "72o", "54s", "53s", "52s", "65o", "64o", "63o", "62o", "43s", "42s", "32s", "54o", "53o", "52o", "43o", "42o", "32o"};

	    int index1 = Arrays.asList(pokerHandRankings).indexOf(hand1);
	    int index2 = Arrays.asList(pokerHandRankings).indexOf(hand2);

	    return Integer.compare(index2, index1);
	}

	public static List<List<Card>> generarCombinaciones(List<Card> cartas, int k) {
		List<List<Card>> combinaciones = new ArrayList<>();
		generarCombinacionesAux(cartas, k, 0, new ArrayList<Card>(), combinaciones);
		return combinaciones;
	}

	private static void generarCombinacionesAux(List<Card> cartas, int k, int start, List<Card> combinacionActual,
			List<List<Card>> combinaciones) {
		if (k == 0) {
			combinaciones.add(new ArrayList<>(combinacionActual));
			return;
		}

		for (int i = start; i < cartas.size(); i++) {
			combinacionActual.add(cartas.get(i));
			generarCombinacionesAux(cartas, k - 1, i + 1, combinacionActual, combinaciones);
			combinacionActual.remove(combinacionActual.size() - 1);
		}
	}

	private void initMap() {
		staticsMap.put("Straight flush", new ArrayList<>());
		staticsMap.put("Quads", new ArrayList<>());
		staticsMap.put("Full", new ArrayList<>());
		staticsMap.put("Flush", new ArrayList<>());
		staticsMap.put("Straight", new ArrayList<>());
		staticsMap.put("3 of a kind", new ArrayList<>());
		staticsMap.put("Two pair", new ArrayList<>());
		staticsMap.put("Over pair", new ArrayList<>());
		staticsMap.put("Top pair", new ArrayList<>());
		staticsMap.put("pp below tp", new ArrayList<>());
		staticsMap.put("Middle pair", new ArrayList<>());
		staticsMap.put("Weak pair", new ArrayList<>());
		staticsMap.put("Ace high", new ArrayList<>());
		staticsMap.put("No made hand", new ArrayList<>());
	}

}
