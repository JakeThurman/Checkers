package jakethurman.games.chess.pieces;

/* Class to represent a Bishop in a Chess game */
public class Bishop extends ChessPiece {
	public Bishop(boolean isWhite) {
		super(isWhite);
	}

	@Override
	public String getImagePath() {
		return this.isWhite
			? "resources/white-bishop.png"
			: "resources/black-bishop.png";
	}

	@Override
	public String toString() {
		return "{ \"type\": \"bishop\", \"isWhite\": " + this.isWhite + ", \"point\": " + getPoint() + " }";
	}
}
