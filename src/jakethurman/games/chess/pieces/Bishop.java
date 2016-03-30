package jakethurman.games.chess.pieces;

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
		return "{ \"type\": \"bishop\", \"isWhite\": " + this.isWhite + " }";
	}
}
