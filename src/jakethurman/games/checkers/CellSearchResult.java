package jakethurman.games.checkers;

import jakethurman.components.Point;

public interface CellSearchResult {
	public boolean getIsJump();
	public Iterable<Point> getJumpedCells();
	public Point getPoint();
}
