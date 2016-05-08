package jakethurman.games;

import jakethurman.foundation.Disposable;

/* Messages needed for all games */
public interface GameMessages extends Disposable {
	public String getBackButton(); // "Back"
	public String getHighScoreListHeader(); // "High Scores"
	public String getHighScoreLine(SimpleScoreData simpleScoreData); // "99 Bob - 10 minutes ago"
}
