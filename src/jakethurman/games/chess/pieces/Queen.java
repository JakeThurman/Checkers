package jakethurman.games.chess.pieces;

/* A class to represent a Queen in a Chess game */
public class Queen extends ChessPiece {
	public Queen(boolean isWhite) {
		super(isWhite);
	}

	@Override
	public String getImagePath() {
		return this.isWhite
			? "resources/white-queen.png"
			: "resources/black-queen.png";
	}

	@Override
	public String toString() {
		return "{ \"type\": \"queen\", \"isWhite\": " + this.isWhite + ", \"point\": " + getPoint() + " }";
	}
}
