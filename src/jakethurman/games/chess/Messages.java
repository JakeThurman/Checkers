package jakethurman.games.chess;

import jakethurman.games.GameMessages;
import jakethurman.games.SimpleScoreData;

// User facing messages for a chess game
public class Messages implements GameMessages {
	/* Constant message values */
	private final String GAME_TITLE = "Chess";

	// Gets the name of the game
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
