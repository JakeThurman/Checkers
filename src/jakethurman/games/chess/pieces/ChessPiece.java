package jakethurman.games.chess.pieces;

import jakethurman.foundation.Disposable;
import jakethurman.foundation.Point;

/* Abstract base class to represent a Piece in a Chess game */
public abstract class ChessPiece implements Disposable {
	protected final boolean isWhite;
	private Point point;
	
	public ChessPiece(boolean isWhite) {
		this.isWhite = isWhite;
	}
	
	public abstract String getImagePath();
	
	public Point getPoint() {
		return this.point;
	}
	
	public void setPoint(Point point) {
		this.point = point;
	}
	
	@Override
	public void dispose() {
		// Nothing to dispose
	}
	
	@Override
	public abstract String toString();
}
