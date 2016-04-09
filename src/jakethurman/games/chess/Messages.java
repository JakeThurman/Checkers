package jakethurman.games.chess;

import jakethurman.games.GameMessages;
import jakethurman.games.SimpleScoreData;

public class Messages implements GameMessages {
	private final String GAME_TITLE = "Chess";

	public String getGameTitle() {
		return GAME_TITLE;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getBackButton() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHighScoreListHeader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHighScoreLine(SimpleScoreData simpleScoreData) {
		// TODO Auto-generated method stub
		return null;
	}
}
