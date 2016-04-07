package jakethurman.games.checkers;

import jakethurman.foundation.Point;

public interface CellSearchResult {
	public boolean getIsJump();
	public Iterable<Point> getJumpedCells();
	public Point getPoint();
}
