package jakethurman.games.checkers;

public class PlayerInfo {
	private int piecesRemaining;
	private int kingCount;
	
	public PlayerInfo(int intialPieces) {
		this.piecesRemaining = intialPieces;
		this.kingCount = 0;
	}
	
	public void playerLostPiece(boolean isKing) {
		if (isKing)
			kingCount--;
		
		piecesRemaining--;	
	}
	
	public int getPiecesRemaining() {
		return piecesRemaining;
	} 
	
	public int getKingCount() {
		return kingCount;
	}

	public void playerHasKing() {
		this.kingCount++;
	}
}
