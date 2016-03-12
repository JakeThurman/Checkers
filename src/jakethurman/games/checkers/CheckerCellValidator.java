package jakethurman.games.checkers;

import jakethurman.components.CellIndex;
import jakethurman.foundation.Disposable;

public class CheckerCellValidator implements Disposable {
	private final Settings settings;
	
	public CheckerCellValidator(Settings s) {
		this.settings = s;
	}
	
	public boolean isValid(CellIndex c) {
		int boardSize = settings.getBoardSize();
		
		return c.x < boardSize && c.x >= 0 
				&& c.y < boardSize && c.y >= 0;
	}
	
	@Override
	public void dispose() {
		// Nothing to dispose
	}
}
