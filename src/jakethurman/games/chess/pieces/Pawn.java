package jakethurman.games.chess.pieces;

/* A class to represent a Pawn in a Chess game */
public class Pawn extends ChessPiece {
	public Pawn(boolean isWhite) {
		super(isWhite);
	}

	@Override
	public String getImagePath() {
		return this.isWhite
			? "resources/white-pawn.png"
			: "resources/black-pawn.png";
	}

	@Override
	public String toString() {
		return "{ \"type\": \"pawn\", \"isWhite\": " + this.isWhite + ", \"point\": " + getPoint() + " }";
	}
}
