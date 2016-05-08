package jakethurman.games.chess.pieces;

/* A class to represent a Knight in a Chess game */
public class Knight extends ChessPiece {
	public Knight(boolean isWhite) {
		super(isWhite);
	}

	@Override
	public String getImagePath() {
		return this.isWhite
			? "resources/white-knight.png"
			: "resources/black-knight.png";
	}

	@Override
	public String toString() {
		return "{ \"type\": \"knight\", \"isWhite\": " + this.isWhite + ", \"point\": " + getPoint() + " }";
	}
}
