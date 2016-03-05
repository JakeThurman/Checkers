package jakethurman.games.checkers;

public class ScoreInfo {
	public final boolean currentPlayerIsPlayer1;
	public final int player1CheckersRemaining;
	public final int player2CheckersRemaining;	
	
	public ScoreInfo(boolean currentPlayerIsPlayer1, int player1CheckersRemaining, int player2CheckersRemaining) {
		this.currentPlayerIsPlayer1 = currentPlayerIsPlayer1;
		this.player1CheckersRemaining = player1CheckersRemaining;
		this.player2CheckersRemaining = player2CheckersRemaining;
	}
}
