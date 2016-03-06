package jakethurman.games.checkers;

import jakethurman.foundation.Disposable;

public class Messages implements Disposable {
	private static final String TURNSTATUS        = "It's %1$ss turn.",
	                            SCORESTATUS_KINGS = "%2$s has %3$d/%1$d checkers remaining with %4$d king%8$s.\n%5$s has %6$d/%1$d checkers remaining with %7$d king%9$s.",
	                            SCORESTATUS       = "%2$s has %3$d/%1$d checkers remaining.\n%5$s has %6$d/%1$d checkers remaining.",
	                            YOUWIN            = "Congratulations %1$s! You won!",
	                            PLAYER1           = "Red",
	                            PLAYER2           = "Black",
	                            PLAYAGAIN         = "Play Again";
	
	public String getTurnStatus(boolean isPlayer1) {
		return String.format(TURNSTATUS, isPlayer1 ? PLAYER1 : PLAYER2);
	}
	
	public String getScoreStatus(int player1Checkers, int player2Checkers, int player1Kings, int player2Kings) {
		// Use the kings format string if either player has a king
		String src = player1Kings == 0 && player2Kings == 0 ? SCORESTATUS : SCORESTATUS_KINGS;
		return String.format(src, Settings.NUM_PIECES, PLAYER1, player1Checkers, player1Kings, PLAYER2, player2Checkers, player2Kings, player1Checkers == 1 ? "" : "s", player2Checkers == 1 ? "" : "s");
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
