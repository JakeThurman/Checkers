package jakethurman.games.checkers;

/*
 * Keeps track of data about a player
 * 	1) How many pieces they still have and
 *  2) The number of those pieces that are kings.
 */
public class PlayerInfo {
	private int piecesRemaining;
	private int kingCount;
	
	public PlayerInfo(int intialPieces) {
		this.piecesRemaining = intialPieces; // The remaining pieces is the initial to start.
		this.kingCount = 0; // The player will never start with any kings
	}
	
	// Records that the player lost a piece.
	// If it is a king, it also records that 
	// a king is gone.
	public void playerLostPiece(boolean isKing) {
		if (isKing)
			kingCount--;
		
		piecesRemaining--;	
	}
	
	// Gets the player's current number of pieces.
	public int getPiecesRemaining() {
		return piecesRemaining;
	} 
	
	// Gets the player's current king count
	public int getKingCount() {
		return kingCount;
	}

	// Adds to the player's king count
	public void playerHasKing() {
		this.kingCount++;
	}
	
	@Override
	public String toString() {
		return "{ \"piecesRemaining\": " + piecesRemaining + ", \"kingCount\": " + kingCount + " }"; 
	}
	
	@Override
	// Creates a copy of this object.
	// This is used when we keep track 
	// of a players score over time, 
	// and because we mutate this object
	// we cannot just use it directly
	public PlayerInfo clone() {
		PlayerInfo me = new PlayerInfo(piecesRemaining);
		me.kingCount = kingCount;
		return me;
	}
}
