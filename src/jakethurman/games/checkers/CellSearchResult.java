package jakethurman.games.checkers;

import jakethurman.components.CellIndex;

public interface CellSearchResult {
	public CellIndex getJumpedCellIndex();
	public CellIndex getCellIndex();
	public boolean getIsJump();
}
