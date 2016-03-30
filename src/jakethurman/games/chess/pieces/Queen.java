package jakethurman.games.chess.pieces;

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
		return "{ \"type\": \"queen\", \"isWhite\": " + this.isWhite + " }";
	}
}
