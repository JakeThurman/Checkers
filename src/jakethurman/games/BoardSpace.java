package jakethurman.games;

import jakethurman.foundation.Disposable;

public class BoardSpace<Piece extends Disposable> implements Disposable {
	private Piece piece = null;
	
	public boolean isEmpty() {
		return this.piece == null;
	}
	
	public void setEmpty() {
		this.piece = null;
	}
	
	public void setPiece(Piece p) {
		this.piece = p;
	}
	
	public Piece getPiece() {
		return this.piece;
	}

	@Override
	public String toString() {
		return isEmpty()
			? "{ \"piece\": null }" 
			: "{ \"piece\": " + this.piece.toString() + " }";
	}
	
	@Override
	public void dispose() {
		if (!isEmpty()) {
			this.piece.dispose();
			this.piece = null;
		}
	}
}	