public class CheckerboardPiece {
	private boolean isPlayer1 = false;
	private boolean isEmpty = true;
	
	public void setPlayer(boolean isPlayer1) {
		this.isPlayer1 = isPlayer1;
	}
	
	public void setEmpty() {
		this.isEmpty = true;
	}
	
	public boolean getIsPlayer1() {
		return isPlayer1;
	}
	
	public boolean getIsEmpty() {
		return isEmpty;
	}
}
