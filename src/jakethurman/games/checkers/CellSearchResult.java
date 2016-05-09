package jakethurman.games.checkers;

import java.util.List;
import jakethurman.foundation.Point;

/* 
 * Read-only interface for CellSearchData
 */
public interface CellSearchResult extends Comparable<CellSearchResult> {
	public boolean getIsJump(); // Will this move jump over any pieces?
	public List<Point> getJumpedCells(); // Get all of the pieces that are to be jumped
	public Point getPoint(); // Get the point that the result it located at
	public boolean isSame(CellSearchResult other); // basically .equals() but named different for clairity that this is not the object imp'l
}
