package jakethurman.games.checkers.components;

import jakethurman.foundation.Disposable;

public class CheckerboardSquare implements Disposable {
	private Checker checker = null;
	
	public boolean isEmpty() {
		return this.checker == null;
	}
	
	public void setEmpty() {
		this.checker = null;
	}
	
	public void setPiece(Checker c) {
		this.checker = c;
	}
	
	public Checker getPiece() {
		return this.checker;
	}

	@Override
	public String toString() {
		return isEmpty() 
			? "CheckerboardSquare: { Empty }" 
			: "CheckerboardSquare: { " + this.checker.toString() + " }";
	}
	
	@Override
	public void dispose() {
		if (!isEmpty()) {
			this.checker.dispose();
			this.checker = null;
		}
	}
}	