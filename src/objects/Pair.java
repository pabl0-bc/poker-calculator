package objects;

public class Pair {

	private String _firstCard;
	private String _secondCard;
	private int _num1;
	private int _num2;
	private String _suited;
	private String _name;

	public Pair(String firstCard, String secondCard, String suited) {
		this._firstCard = firstCard;
		this._num1 = setValue(firstCard);
		this._secondCard = secondCard;
		this._num2 = setValue(secondCard);
		this._suited = suited;
		this._name = firstCard + secondCard + suited;
	}

	private int setValue(String value) {
		switch (value) {
		case "A": {
			return 14;
		}
		case "K": {
			return 13;
		}
		case "Q": {
			return 12;
		}
		case "J": {
			return 11;
		}
		case "T": {
			return 10;
		}
		case "9": {
			return 9;
		}
		case "8": {
			return 8;
		}
		case "7": {
			return 7;
		}
		case "6": {
			return 6;
		}
		case "5": {
			return 5;
		}

		case "4": {
			return 4;
		}
		case "3": {
			return 3;
		}
		case "2": {
			return 2;
		}
		}
		return -1;
	}

	public String get_firstCard() {
		return _firstCard;
	}

	public String get_secondCard() {
		return _secondCard;
	}

	public String get_suited() {
		return _suited;
	}

	public int get_num1() {
		return _num1;
	}

	public int get_num2() {
		return _num2;
	}
	
	public String get_name() {
		return _name;
	}

}
