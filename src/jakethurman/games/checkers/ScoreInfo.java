package jakethurman.games.checkers;

public class ScoreInfo {
	public final boolean currentPlayerIsPlayer1;
	public final int player1CheckersRemaining,
	                 player2CheckersRemaining,
	                 player1Kings,
	                 player2Kings;
	
	public ScoreInfo(boolean currentPlayerIsPlayer1, int player1CheckersRemaining, int player2CheckersRemaining, int player1Kings, int player2Kings) {
		this.currentPlayerIsPlayer1 = currentPlayerIsPlayer1;
		this.player1CheckersRemaining = player1CheckersRemaining;
		this.player2CheckersRemaining = player2CheckersRemaining;
		this.player1Kings = player1Kings;
		this.player2Kings = player2Kings;
	}
}
