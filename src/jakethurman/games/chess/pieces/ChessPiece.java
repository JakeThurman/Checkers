package jakethurman.games.chess.pieces;

import jakethurman.foundation.Disposable;

public abstract class ChessPiece implements Disposable {
	protected final boolean isWhite;
	
	public ChessPiece(boolean isWhite) {
		this.isWhite = isWhite;
	}
	
	public abstract String getImagePath();
	
	@Override
	public void dispose() {
		// Nothing to dispose
	}
	
	@Override
	public abstract String toString();
}
