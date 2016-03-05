public class Messages {
	private static final String TURNSTATUS  = "It's %1$ss turn.",
	                            SCORESTATUS = "%2$s has %3$d/%1$d checkers remaining.\n%4$s has %5$d/%1$d checkers remaining.",
	                            PLAYER1     = "Red",
	                            PLAYER2     = "Black";
	
	public String getTurnStatus(boolean isPlayer1) {
		return String.format(TURNSTATUS, isPlayer1 ? PLAYER1 : PLAYER2);
	}
	
	public String getScoreStatus(int player1Checkers, int player2Checkers) {
		return String.format(SCORESTATUS, Settings.NUM_PIECES, PLAYER1, player1Checkers, PLAYER2, player2Checkers);
	}
}
