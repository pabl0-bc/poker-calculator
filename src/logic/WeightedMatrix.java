package logic;

public class WeightedMatrix {
	private int rows;
	private int columns;
	private MatrixElement[][] matrix;


	public WeightedMatrix(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		this.matrix = new MatrixElement[rows][columns];
	}

	public void setElement(int row, int column, String name, float weight, String firstCard, String secondCard, String suited) {
		if (row >= 0 && row < rows && column >= 0 && column < columns) {
			matrix[row][column] = new MatrixElement(name, weight, firstCard, secondCard, suited);
		} else {
			throw new IllegalArgumentException("Invalid row or column index.");
		}
	}

	public MatrixElement getElement(int row, int column) {
		if (row >= 0 && row < rows && column >= 0 && column < columns) {
			return matrix[row][column];
		} else {
			throw new IllegalArgumentException("Invalid row or column index.");
		}
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public class MatrixElement {
		public String get_firstCard() {
			return _firstCard;
		}

		public String get_secondCard() {
			return _secondCard;
		}



		private String name;
		private float weight;
		private String _firstCard;
		private String _secondCard;
		private String _suited;

		public MatrixElement(String name, float weight, String firstCard, String secondCard, String suited) {
			this.name = name;
			this.weight = weight;
			this._firstCard = firstCard;
			this._secondCard = secondCard;
			this._suited = suited;
		}

		public String getName() {
			return name;
		}

		public float getWeight() {
			return weight;
		}

		public String get_suited() {
			// TODO Auto-generated method stub
			return _suited;
		}
	}
}