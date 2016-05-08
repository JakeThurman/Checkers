package jakethurman.games.chess.pieces;

/* A class to represent a Rook in a Chess game */
public class Rook extends ChessPiece {
	public Rook(boolean isWhite) {
		super(isWhite);
	}

	@Override
	public String getImagePath() {
		return this.isWhite
			? "resources/white-rook.png"
			: "resources/black-rook.png";
	}

	@Override
	public String toString() {
		return "{ \"type\": \"rook\", \"isWhite\": " + this.isWhite + ", \"point\": " + getPoint() + " }";
	}
}
