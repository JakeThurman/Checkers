public class Messages implements Disposable {
	private static final String TURNSTATUS  = "It's %1$ss turn.",
	                            SCORESTATUS = "%2$s has %3$d/%1$d checkers remaining.\n%4$s has %5$d/%1$d checkers remaining.",
	                            YOUWIN      = "Congratulations %1$s! You won!",
	                            PLAYER1     = "Red",
	                            PLAYER2     = "Black",
	                            PLAYAGAIN   = "Play Again";
	
	public String getTurnStatus(boolean isPlayer1) {
		return String.format(TURNSTATUS, isPlayer1 ? PLAYER1 : PLAYER2);
	}
	
	public String getScoreStatus(int player1Checkers, int player2Checkers) {
		return String.format(SCORESTATUS, Settings.NUM_PIECES, PLAYER1, player1Checkers, PLAYER2, player2Checkers);
	}
	
	public String getWinnerMessage(boolean isPlayer1) {
		return String.format(YOUWIN, isPlayer1 ? PLAYER1 : PLAYER2);
	}
	
	public String getPlayAgain() {
		return PLAYAGAIN;
	}
	
	public void dispose() {
		// Nothing to dispose
	}
}
