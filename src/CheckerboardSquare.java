public class CheckerboardSquare {
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
	
	public String toString() {
		return isEmpty() 
			? "CheckerboardSquare: { Empty }" 
			: "CheckerboardSquare: { " + this.checker.toString() + " }";
	}
}	