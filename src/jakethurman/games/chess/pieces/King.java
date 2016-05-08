package jakethurman.games.chess.pieces;

/* A class to represent a King in a Chess game */
public class King extends ChessPiece {
	public King(boolean isWhite) {
		super(isWhite);
	}

	@Override
	public String getImagePath() {
		return this.isWhite
			? "resources/white-king.png"
			: "resources/black-king.png";
	}

	@Override
	public String toString() {
		return "{ \"type\": \"king\", \"isWhite\": " + this.isWhite + ", \"point\": " + getPoint() + " }";
	}
}
