package jakethurman.games;

import jakethurman.foundation.Disposable;

/* 
 * Represents a cell on a board. 
 * It contains a piece of generic type <Piece>.
 */
public class BoardSpace<Piece extends Disposable> implements Disposable {
	private Piece piece = null;
	
	// Is this cell empty?
	public boolean isEmpty() {
		return this.piece == null;
	}
	
	// Removes the piece from the space.
	public void setEmpty() {
		this.piece = null;
	}
	
	// Sets the piece in this space.
	public void setPiece(Piece p) {
		this.piece = p;
	}
	
	// Gets the piece in the space
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