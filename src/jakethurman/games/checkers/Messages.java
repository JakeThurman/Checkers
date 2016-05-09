package jakethurman.games.checkers;

import jakethurman.games.GameMessages;
import jakethurman.games.SimpleScoreData;
import jakethurman.util.RelativeTime;

// Messages specific to Checkers
public class Messages implements GameMessages {
	/* Constant Message Values */
	private static final String TURNSTATUS         = "It's %1$ss turn.",
	                            SCORESTATUS_KINGS  = "%2$s has %3$d/%1$d checkers remaining with %4$d king%8$s.\n%5$s has %6$d/%1$d checkers remaining with %7$d king%9$s.",
	                            SCORESTATUS        = "%2$s has %3$d/%1$d checkers remaining.\n%5$s has %6$d/%1$d checkers remaining.",
	                            YOUWIN             = "Congratulations %1$s! You won!",
	                            PLAYER1            = "Red",
	                            PLAYER2            = "Black",
	                            PLAYAGAIN          = "Play Again",
                                VIEW_GAME_STATS    = "View Game Statistics",
                                BACK_BUTTON        = "Back",
                                NONE               = "none",
                                WAS_KING_PLURAL    = "were kings",
                                WAS_KING_SINGULAR  = "was a king",
                                GAME_STATS_MSG     = "%1$s won with a score of: %7$d.\n    %2$d/%3$d checkers remaining\n    %4$s of which %5$s.\nThe game lasted a total of %6$s",
                                GAME_TITLE__HUMAN  = "Checkers",
                                GAME_TITLE__EASY   = "Checkers (vs Easy Computer Player)",
                                GAME_TITLE__MEDIUM = "Checkers (vs Medium Computer Player)",
                                GAME_TITLE__HARD   = "Checkers (vs Hard Computer Player)",
                                HIGH_SCORES_LIST   = "High Scores";
	
	private final Settings settings;
	
	public Messages(Settings settings) {
		this.settings = settings;
	}
	
	/* Gets the turn status message stating that it is a given player */
	public String getTurnStatus(boolean isPlayer1) {
		return String.format(TURNSTATUS, isPlayer1 ? PLAYER1 : PLAYER2);
	}
	
	/* Gets a complete score status message. */
	@SuppressWarnings("boxing") // Ignore that we're boxing ints to Integers here
	public String getScoreStatus(int player1Checkers, int player2Checkers, int player1Kings, int player2Kings) {
		// Use the kings format string if either player has a king
		String src = player1Kings == 0 && player2Kings == 0 ? SCORESTATUS : SCORESTATUS_KINGS;
		return String.format(src, settings.getNumPieces(), PLAYER1, player1Checkers, player1Kings, PLAYER2, player2Checkers, player2Kings, player1Checkers == 1 ? "" : "s", player2Checkers == 1 ? "" : "s");
	}
	
	/* Gets game results (statistics) text */
	@SuppressWarnings("boxing")
	public String getGameStatsMessage(boolean player1Won, int kings, int checkers, long gameLengthMS, int score) {
		String playerName  = player1Won ? PLAYER1 : PLAYER2;
		String kingsString = kings == 0 ? NONE : Integer.toString(kings);
		String wereKings   = kings == 1 ? WAS_KING_SINGULAR : WAS_KING_PLURAL;
		
		return String.format(GAME_STATS_MSG, playerName, checkers, settings.getNumPieces(), kingsString, wereKings, RelativeTime.timeSpanFormat(gameLengthMS), score);
	}
	
	/* Gets the message for a player telling them that they won */
	public String getWinnerMessage(boolean isPlayer1) {
		return String.format(YOUWIN, isPlayer1 ? PLAYER1 : PLAYER2);
	}
	
	/* Gets the play again message */
	public String getPlayAgain() {
		return PLAYAGAIN;
	}
	
	/* Gets the view game statistics message */
	public String getViewGameStats() {
		return VIEW_GAME_STATS;
	}

	/* Gets the game title, including the optional AI modifier */
	public String getGameTitle() {
		switch(settings.getDifficulty()) {
			case EASY:
				return GAME_TITLE__EASY;
			case MEDIUM:
				return GAME_TITLE__MEDIUM;
			case HARD:
				return GAME_TITLE__HARD;
			case HUMAN:
				return GAME_TITLE__HUMAN;
			default:
				throw new Error("The given game difficulty was invalid!"); // TODO: Use a better exception class
		}
	}
	
	/* Gets the back button text */
	@Override
	public String getBackButton() {
		return BACK_BUTTON;
	}
	
	@Override
	public void dispose() {
		// Nothing to dispose
	}

	/* Gets the high score list name */
	@Override
	public String getHighScoreListHeader() {
		return HIGH_SCORES_LIST;
	}

	/* Returns a line of text to be displayed in the  */
	@Override
	public String getHighScoreLine(SimpleScoreData data) {
		return data.name + ": " + data.score + " - (" + RelativeTime.timeAgoFormat(data.gameEndMs) + ")";
	}

	// Gets the name of a player
	public String getPlayerName(boolean isPlayer1) {
		return isPlayer1 ? PLAYER1 : PLAYER2;
	}
}
