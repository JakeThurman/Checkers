package jakethurman.games.checkers.components;

import java.util.LinkedList;
import java.util.List;
import jakethurman.foundation.CleanupHandler;
import jakethurman.foundation.Disposable;
import jakethurman.foundation.Point;
import jakethurman.foundation.datastructures.SquareFixedAndFilled2DArray;
import jakethurman.components.SafeGridPane;
import jakethurman.games.checkers.CellSearchData;
import jakethurman.games.checkers.CellSearchResult;
import jakethurman.games.checkers.CheckersTurnManager;
import jakethurman.games.checkers.Settings;

public class Checkerboard implements Disposable {
	private final CheckersTurnManager turnManager;
	private final CleanupHandler      cleanup;
	private final Settings            settings;

	private final SquareFixedAndFilled2DArray<CheckerboardSquare> cells;
	private final SafeGridPane                                    visual;
	
	public Checkerboard(CheckersTurnManager turnManager, Settings settings) {
		this.turnManager = turnManager;
		this.settings    = settings;
		this.cleanup     = new CleanupHandler(turnManager, settings);
		this.visual      = new SafeGridPane();
		this.cells       = new SquareFixedAndFilled2DArray<>(settings.getBoardSize(), () -> new CheckerboardSquare());
	}
		
	private CheckerboardSquare getCell(Point i) {
		return cells.get(i);
	}
	
	public void setJumped(Point i) {
		CheckerboardSquare cell   = getCell(i);
		Checker            jumped = cell.getPiece();
		
		remove(jumped);
		turnManager.recordDeadChecker(jumped.getIsPlayer1(), jumped.getIsKing());
		jumped.dispose();
	}
	
	public void pieceIsInCell(Checker checker) {
		Point pos = checker.getPos();
		getCell(pos).setPiece(checker);
		
		// Add visually
		visual.add(checker.getNode(), pos);
	}
	
	private void remove(Checker c) {
		getCell(c.getPos()).setEmpty(); // Record that the cell is now empty
		visual.remove(c.getNode()); // Remove the node
	}

	public void movePieceToCell(Checker checker, Point pos) {
		remove(checker); // Remove it from it's old location
		checker.setPos(pos); // Record the new position to the checker
		pieceIsInCell(checker); // Move it to the new piece
		handleKingship(checker, pos); // Handle Kingship
		turnManager.endTurn(); // This was this players turn so call it over
	}
	
	public void handleKingship(Checker c, Point pos) {
		if ((c.getIsPlayer1() && pos.y == 0) || (!c.getIsPlayer1() && pos.y == (settings.getBoardSize() - 1))) {
			c.kingMe();
			turnManager.playerHasKing(c.getIsPlayer1());
		}
	}
	
	public Iterable<CellSearchResult> getAvailableSpaces(Checker checker) {
		LinkedList<Point>            seenCells = new LinkedList<>();
		LinkedList<CellSearchResult> results   = new LinkedList<>();
		LinkedList<CellSearchData>   original  = new LinkedList<>();
		LinkedList<CellSearchData>   toCheck   = new LinkedList<>();
		
		if (!checker.getIsPlayer1() || checker.getIsKing()) {
			original.add(new CellSearchData(1, 1, checker.getPos()));
			original.add(new CellSearchData(-1, 1, checker.getPos()));
		}
		if (checker.getIsPlayer1() || checker.getIsKing()) {
			original.add(new CellSearchData(-1, -1, checker.getPos()));
			original.add(new CellSearchData(1, -1, checker.getPos()));
		}
		
		toCheck.addAll(original);
		
		boolean iAmPlayer1 = checker.getIsPlayer1();
		
		while (!toCheck.isEmpty()) {			
			CellSearchData checking = toCheck.poll();
			Point          index    = checking.getPoint();
			
			//Record that we've seen this cell
			seenCells.add(index);
			
			if (!cells.isValid(index))
				continue;
						
			// See if this is an empty space
			CheckerboardSquare cell = getCell(index);
			
			if (cell.isEmpty()) {
				if (!results.contains(checking))
					results.add(checking);
				
				// Check if we can double jump
				if (checking.getIsJump()) {
					// Check if the next square from here has another piece in it
					// If it does add it to the queue to be checker for jumpage
					List<CellSearchData> toCheckForDouble = checking.getDoubleJumpOptions(original, (doubleJumpIndex) -> {	
						if (!cells.isValid(doubleJumpIndex))
							return false;
												
						//Don't go back to a cell we've already been to or any cell we've already seen. That will cause an infinite loop.
						if (doubleJumpIndex.equals(checker.getPos()) || seenCells.contains(doubleJumpIndex))
							return false;
						
						seenCells.add(doubleJumpIndex);
						
						CheckerboardSquare doubleJumpCell = getCell(doubleJumpIndex);
						
						return !doubleJumpCell.isEmpty() && doubleJumpCell.getPiece().getIsPlayer1() != iAmPlayer1;
					});
					
					toCheck.addAll(toCheckForDouble);
				}
			}
			// If we aren't already doing so, see if we can jump this piece and it's not our own piece
			else if (!checking.getIsJump() && cell.getPiece().getIsPlayer1() != iAmPlayer1) {
				toCheck.add(checking.withJumpOffset());
			}
		}
		
		return results;
	}
	
	@Override
	public void dispose() {
		cleanup.dispose();
		for (CheckerboardSquare cell : cells) {
			cell.dispose();
		}
	}

	public SafeGridPane getNode() {
		return this.visual;
	}

	@Override
	public String toString() {
		return "{ \"cells\": " + cells + " }";
	}
}
