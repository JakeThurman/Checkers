package jakethurman.games.checkers;

public class ScoreInfo {
	public final boolean currentPlayerIsPlayer1;
	public final PlayerInfo player1,
	                        player2;
	
	public ScoreInfo(boolean currentPlayerIsPlayer1, PlayerInfo player1, PlayerInfo player2) {
		this.currentPlayerIsPlayer1 = currentPlayerIsPlayer1;
		this.player1 = player1;
		this.player2 = player2;
	}
	
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
