package objects;

import java.util.Objects;

public class Card {

	private String _suit;
	private Suits suit;
	private String _value;
	private String _id;
	private int _num;

	public Card(String value, String suit, String id) {
		_suit = suit;
		_value = value;
		_id = id;
		parseNum(value);
		assignSuit(suit);
	}
 
	private void assignSuit(String value) {
		switch(value) {
			case "h":
				suit = Suits.h;
				break;
			
			case "d":
				suit = Suits.d;
				break;
				
			case "c":
				suit = Suits.c;
				break;
			
			case "s":
				suit = Suits.s;
				break;
		}
	}
	
	private void parseNum(String value) {
		switch (value) {
		case "A": {
			_num = 14;
		}
			break;
		case "K": {
			_num = 13;
		}
			break;
		case "Q": {
			_num = 12;
		}
			break;
		case "J": {
			_num = 11;
		}
			break;
		case "T": {
			_num = 10;
		}
			break;

		case "9": {
			_num = 9;
		}
			break;
		case "8": {
			_num = 8;
		}
			break;
		case "7": {
			_num = 7;
		}
			break;
		case "6": {
			_num = 6;
		}
			break;
		case "5": {
			_num = 5;
		}
			break;

		case "4": {
			_num = 4;
		}
			break;
		case "3": {
			_num = 3;
		}
			break;
		case "2": {
			_num = 2;
		}
			break;
		}

	}

	public String get_suit() {
		return _suit;
	}

	public String get_value() {
		return _value;
	}
	
	public String toString(){
		
		switch (this._value) {
		case "A": 
			return "Aces";
		
		case "K": 
			return "King";
			
		case "Q": 
			return "Queen";
			
		case "J": 
			return "Jack";
			
		case "T": 
			return "Ten"; 

		case "9": 
			return "Nine";
			
		case "8": 
			return "Eight";
			
		case "7": 
			return "Seven";
			
		case "6": 
			return "Six";
		case "5": 
			return "Five";
			
		case "4": 
			return "Four";
			
		case "3": 
			return "Three";
			
		case "2":
			return "Two";
			
		}
		
		return "";
}

	public String get_id() {
		return _id;
	}

	public int get_num() {
		return _num;
	}
	
	public Suits getSuit() {
		return suit;
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) {
	        return true;
	    }
	    if (obj == null || getClass() != obj.getClass()) {
	        return false;
	    }
	    Card other = (Card) obj;
	    return Objects.equals(_suit, other._suit) && Objects.equals(_value, other._value) && Objects.equals(_id, other._id);
	}

	@Override
	public int hashCode() {
	    return Objects.hash(_suit, _value, _id);
	}

}
