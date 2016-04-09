package jakethurman.games;

import jakethurman.foundation.Disposable;

public interface GameMessages extends Disposable {
	public String getBackButton();
	public String getHighScoreListHeader();
	public String getHighScoreLine(SimpleScoreData simpleScoreData);
}
