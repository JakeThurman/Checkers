package jakethurman.games.checkers;

import java.util.List;
import jakethurman.foundation.Point;

public interface CellSearchResult extends Comparable<CellSearchResult> {
	public boolean getIsJump();
	public List<Point> getJumpedCells();
	public Point getPoint();
}
