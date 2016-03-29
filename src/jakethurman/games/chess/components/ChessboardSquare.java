package jakethurman.games.chess.components;

import jakethurman.foundation.Disposable;
import jakethurman.games.chess.pieces.ChessPiece;

public class ChessboardSquare implements Disposable {
	private ChessPiece piece = null;
	
	public boolean isEmpty() {
		return this.piece == null;
	}
	
	public void setEmpty() {
		this.piece = null;
	}
	
	public void setPiece(ChessPiece c) {
		this.piece = c;
	}
	
	public ChessPiece getPiece() {
		return this.piece;
	}

	@Override
	public String toString() {
		return isEmpty() 
			? "{ piece: null }" 
			: "{ piece: " + this.piece.toString() + " }";
	}
	
	@Override
	public void dispose() {
		if (!isEmpty()) {
			this.piece.dispose();
			this.piece = null;
		}
	}
}	