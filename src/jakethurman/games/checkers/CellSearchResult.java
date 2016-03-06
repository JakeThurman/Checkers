package jakethurman.games.checkers;

import jakethurman.components.CellIndex;

public interface CellSearchResult {
	public boolean getIsJump();
	public Iterable<CellIndex> getJumpedCells();
	public CellIndex getCellIndex();
}
