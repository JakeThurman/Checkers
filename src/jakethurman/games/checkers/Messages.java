package jakethurman.games.checkers;

import jakethurman.games.GameMessages;

public class Messages implements GameMessages {
	private static final String TURNSTATUS        = "It's %1$ss turn.",
	                            SCORESTATUS_KINGS = "%2$s has %3$d/%1$d checkers remaining with %4$d king%8$s.\n%5$s has %6$d/%1$d checkers remaining with %7$d king%9$s.",
	                            SCORESTATUS       = "%2$s has %3$d/%1$d checkers remaining.\n%5$s has %6$d/%1$d checkers remaining.",
	                            YOUWIN            = "Congratulations %1$s! You won!",
	                            PLAYER1           = "Red",
	                            PLAYER2           = "Black",
	                            PLAYAGAIN         = "Play Again",
                                VIEW_GAME_STATS   = "View Game Statistics",
                                BACK_BUTTON       = "Back",
                                NONE              = "none",
                                WAS_KING_PLURAL   = "were kings",
                                WAS_KING_SINGULAR = "was a king",
                                GAME_STATS_MSG    = "%1$s won with %2$d/%3$d checkers remaining, %4$s of which %5$s. The game lasted a total of %6$.1fs",
                                GAME_TITLE        = "Checkers",
                                HIGH_SCORES_LIST  = "High Scores";
	
	private final Settings settings;
	
	public Messages(Settings settings) {
		this.settings = settings;
	}
	
	public String getTurnStatus(boolean isPlayer1) {
		return String.format(TURNSTATUS, isPlayer1 ? PLAYER1 : PLAYER2);
	}
	
	@SuppressWarnings("boxing") // It's okay that we're boxing ints to Integers here
	public String getScoreStatus(int player1Checkers, int player2Checkers, int player1Kings, int player2Kings) {
		// Use the kings format string if either player has a king
		String src = player1Kings == 0 && player2Kings == 0 ? SCORESTATUS : SCORESTATUS_KINGS;
		return String.format(src, settings.getNumPieces(), PLAYER1, player1Checkers, player1Kings, PLAYER2, player2Checkers, player2Kings, player1Checkers == 1 ? "" : "s", player2Checkers == 1 ? "" : "s");
	}
	
	@SuppressWarnings("boxing")
	public String getGameStatsMessage(boolean player1Won, int kings, int checkers, double gameLengthMS) {
		String playerName  = player1Won ? PLAYER1 : PLAYER2;
		String kingsString = kings == 0 ? NONE : Integer.toString(kings);
		String wereKings   = kings == 1 ? WAS_KING_SINGULAR : WAS_KING_PLURAL;
		
		return String.format(GAME_STATS_MSG, playerName, checkers, settings.getNumPieces(), kingsString, wereKings, (gameLengthMS / 100));
	}
	
	public String getWinnerMessage(boolean isPlayer1) {
		return String.format(YOUWIN, isPlayer1 ? PLAYER1 : PLAYER2);
	}
	
	public String getPlayAgain() {
		return PLAYAGAIN;
	}
	
	public String getViewGameStats() {
		return VIEW_GAME_STATS;
	}

	public String getGameTitle() {
		return GAME_TITLE;
	}
	
	@Override
	public String getBackButton() {
		return BACK_BUTTON;
	}
	
	@Override
	public void dispose() {
		// Nothing to dispose
	}

	@Override
	public String getHighScoreListHeader() {
		return HIGH_SCORES_LIST;
	}
}
