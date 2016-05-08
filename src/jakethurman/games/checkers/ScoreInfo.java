package jakethurman.games.checkers;

/*
 * Holds information about the score
 * at a given point for a given player.
 */
public class ScoreInfo {
	// Data
	public final boolean currentPlayerIsPlayer1;
	public final PlayerInfo player1,
	                        player2;
	
	// C'tor
	public ScoreInfo(boolean currentPlayerIsPlayer1, PlayerInfo player1, PlayerInfo player2) {
		this.currentPlayerIsPlayer1 = currentPlayerIsPlayer1;
		this.player1 = player1;
		this.player2 = player2;
	}
	
	// Gets the info for the current player
	public PlayerInfo getCurrentPlayer() {
		return currentPlayerIsPlayer1 ? player1 : player2;
	}
	
	@Override
	public String toString() {
		return "{ \"currentPlayerIsPlayer1\": " + currentPlayerIsPlayer1 + 
				", \"player1\": " + player1 + 
				", \"player2\": " + player2 + " }";
	}
}
