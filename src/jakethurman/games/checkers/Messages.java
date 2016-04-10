package jakethurman.games.checkers;

import java.util.concurrent.TimeUnit;
import jakethurman.games.GameMessages;
import jakethurman.games.SimpleScoreData;

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

	private static final long MS_IN_1_MINUTE = 1000 * 60;
	private static final long MS_IN_1_HOUR = MS_IN_1_MINUTE * 60;
	private static final long MS_IN_1_DAY = MS_IN_1_HOUR * 24;
	
	@Override
	public String getHighScoreLine(SimpleScoreData data) {
		return data.name + ": " + data.score + " - (" + timeAgoFormat(data.gameEndMs) + ")";
	}
	
	private static String timeAgoFormat(long ms) {
		long now = System.currentTimeMillis();
		long diff = now - ms;
		
		if (diff <= MS_IN_1_MINUTE) {
			long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);
			// We don't return "0 seconds", so lie and say 1!
			return pluralize((seconds == 0 ? 1 : seconds), "{0} second{s} ago");
		}
		if (diff <= MS_IN_1_HOUR)
			return pluralize(TimeUnit.MILLISECONDS.toMinutes(diff), "{0} minute{s} ago");
        if (diff <= MS_IN_1_DAY)
        	return pluralize(TimeUnit.MILLISECONDS.toHours(diff), "{0} hour{s} ago");

        return pluralize(TimeUnit.MILLISECONDS.toDays(diff), "{0} day{s} ago");
	}
	
	private static String pluralize(long value, String src) {
		return src
			.replace("{0}", Long.toString(value))
			.replace("{s}", value == 1 ? "" : "s");
	}

	public String getPlayerName(boolean isPlayer1) {
		return isPlayer1 ? PLAYER1 : PLAYER2;
	}
}
