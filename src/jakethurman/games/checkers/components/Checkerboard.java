package jakethurman.games.checkers.components;

import java.util.LinkedList;
import java.util.List;
import jakethurman.foundation.CleanupHandler;
import jakethurman.foundation.Disposable;
import jakethurman.foundation.Point;
import jakethurman.foundation.datastructures.Queue;
import jakethurman.foundation.datastructures.SquareFixedAndFilled2DArray;
import jakethurman.components.SafeGridPane;
import jakethurman.games.BoardSpace;
import jakethurman.games.checkers.CellSearchData;
import jakethurman.games.checkers.CellSearchResult;
import jakethurman.games.checkers.CheckersTurnManager;
import jakethurman.games.checkers.Settings;

/* 
 * Handles the checker board grid and its virtual contents 
 */
public class Checkerboard implements Disposable {
	/* Dependencies */
	private final CheckersTurnManager turnManager;
	private final CleanupHandler      cleanup;
	private final Settings            settings;

	/* Local Variables */
	private final SquareFixedAndFilled2DArray<BoardSpace<Checker>> cells;
	private final SafeGridPane                                     visual;
	
	/* C'tor */
	public Checkerboard(CheckersTurnManager turnManager, Settings settings) {
		this.turnManager = turnManager;
		this.settings    = settings;
		this.cleanup     = new CleanupHandler(turnManager, settings);
		this.visual      = new SafeGridPane();
		this.cells       = new SquareFixedAndFilled2DArray<>(settings.getBoardSize(), () -> new BoardSpace<>());
	}
		
	/* Records the checker in a cell as jumped */
	private void setJumped(Point i) {
		// Get the checker in the cell
		BoardSpace<Checker> cell   = cells.get(i);
		Checker             jumped = cell.getPiece();
		
		// Remove the checker from the screen
		remove(jumped);
		
		// Tell the turn manager that a checker was jumped.
		turnManager.recordDeadChecker(jumped.getIsPlayer1(), jumped.getIsKing());
		jumped.dispose();
	}
	
	// Adds the checker to the screen
	public void add(Checker checker) {
		// Get where the checker wants to be
		Point pos = checker.getPos();
		
		// Place the checker to that cell
		cells.get(pos).setPiece(checker);
		
		// Add visually also
		visual.add(checker.getNode(), pos);
	}
	
	// Removes a checker from the screen
	private void remove(Checker c) {
		cells.get(c.getPos()).setEmpty(); // Record that the cell is now empty
		visual.remove(c.getNode()); // Remove the node
	}

	// Moves a checker to a new cell
	public void makeMove(Checker checker, CellSearchResult searchData) {
		Point pos = searchData.getPoint();
		remove(checker); // Remove it from it's old location
		checker.setPos(pos); // Record the new position to the checker
		add(checker); // Move it to the new piece
		handleKingship(checker, pos); // Handle Kingship
		
		// Set any jumped pieces as such
		for (Point i : searchData.getJumpedCells())
			setJumped(i);
		
		turnManager.endTurn(); // This was this players turn so call it over
	}
	
	// Checkers if a checker moving to Point pos will make it a king, and handles that case
	private void handleKingship(Checker c, Point pos) {
		// Do not worry about making the player a king if they already are one.
		if (c.getIsKing())
			return;
		
		// If player 1 is at the top, or player 2 is at the bottom, they should be a king.
		if ((c.getIsPlayer1() && pos.y == 0) || (!c.getIsPlayer1() && pos.y == (settings.getBoardSize() - 1))) {
			c.kingMe(); // Convert the checker to a king, since it is at the top/bottom as appropriate.
			turnManager.playerHasKing(c.getIsPlayer1()); // Tell the turn manager the player has a new king
		}
	}
	
	// Gets all of the checkers belonging to a given player.
	public Iterable<Checker> getAllCheckers(boolean forPlayer1) {
		LinkedList<Checker> results = new LinkedList<>();
		
		// Loop through every board cell
		for (BoardSpace<Checker> cell : cells) {
			// If this cell contains a checker, and 
			// that checker belongs to the correct 
			// player, add it to the results list
			if (!cell.isEmpty() && cell.getPiece().getIsPlayer1() == forPlayer1)
				results.add(cell.getPiece());
		}
		
		return results;
	}
	
	// Get available spaces for a checker to move to.
	// This is one fancy, complex algorithm!
	public Iterable<CellSearchResult> getAvailableSpaces(Checker checker) {
		// Create a bunch of lists to remember what we've done 
		LinkedList<Point>            seenCells = new LinkedList<>(); // All the cells we've already seen (prevents infinite loops)
		LinkedList<CellSearchResult> results   = new LinkedList<>(); // Stores all of the valid moves we find
		LinkedList<CellSearchData>   original  = new LinkedList<>(); // Holds the initial set of searches.
		Queue<CellSearchData>        toCheck   = new Queue<>(); // (Custom Queue class) - holds all of the places we still want to check for validity
		
		// Player 2 (or any king) can move down, so add a
		// search to the original queue for down-left (-1,1)  -- (DeltaX, DeltaY)
		// and down-right (1, 1).
		if (!checker.getIsPlayer1() || checker.getIsKing()) {
			original.add(new CellSearchData(1, 1, checker.getPos()));
			original.add(new CellSearchData(-1, 1, checker.getPos()));
		}
		// Player 1 (or any king) can move up, so add a
		// search to the original queue for up-left (-1,-1)  -- (DeltaX, DeltaY)
		// and up-right (1, -1).
		if (checker.getIsPlayer1() || checker.getIsKing()) {
			original.add(new CellSearchData(-1, -1, checker.getPos()));
			original.add(new CellSearchData(1, -1, checker.getPos()));
		}
		
		// Enqueue all of the original delta searches to be searched.
		toCheck.enqueue(original);
		
		// Does this belong to player 1?
		boolean iAmPlayer1 = checker.getIsPlayer1();
		
		// Loop through the queue until it is empty.
		while (toCheck.hasNext()) {
			// Take the item at the from of the queue to check
			CellSearchData checking = toCheck.poll();
			Point          index    = checking.getPoint();
			
			// Record that we've seen this cell
			seenCells.add(index);
			
			// If this is not a valid search, validate the next queue item.
			if (!cells.isValid(index))
				continue;
			
			// See if this is an empty space
			BoardSpace<Checker> cell = cells.get(index);
			
			// If the cell is empty we can move there!
			if (cell.isEmpty()) {
				// Add the cell the results list.
				if (!results.contains(checking))
					results.add(checking);
				
				// Check if we can double jump
				if (checking.getIsJump()) {
					// Check if the next square from here has another piece in it
					// If it does add it to the queue to be checker for jumpage
					List<CellSearchData> toCheckForDouble = checking.getDoubleJumpOptions(original, (doubleJumpIndex) -> {	
						// First of all, see if this is a real location
						if (!cells.isValid(doubleJumpIndex))
							return false;
										
						//Don't go back to a cell we've already been to or any cell we've already seen. That will cause an infinite loop.
						if (doubleJumpIndex.equals(checker.getPos()) || seenCells.contains(doubleJumpIndex))
							return false;
						
						// Record that we have seen this index so we don't check it again
						seenCells.add(doubleJumpIndex);
						
						// get the cell at the this location to see if it is a valid place to move
						BoardSpace<Checker> doubleJumpCell = cells.get(doubleJumpIndex);
						
						// It is valid if we've made it here, 
						// and the cell we're trying to jump 
						// over is not empty, and the piece
						// in it belongs to the other player.
						return !doubleJumpCell.isEmpty() && doubleJumpCell.getPiece().getIsPlayer1() != iAmPlayer1;
					});
					
					// Add all of the double jump options to the queue to check for validity
					toCheck.enqueue(toCheckForDouble);
				}
			}
			// If we aren't already doing so, see if we can jump this piece and it's not our own piece
			else if (!checking.getIsJump() && cell.getPiece().getIsPlayer1() != iAmPlayer1) {
				toCheck.enqueue(checking.withJumpOffset());
			}
		}
		
		// Respond with all of the found locations the player can move too.
		return results;
	}
	
	@Override
	public void dispose() {
		cleanup.dispose();
		
		// Dispose of all of the cells
		for (BoardSpace<Checker> cell : cells)
			cell.dispose();
	}

	// Gets the visual node for the checkerboard
	public SafeGridPane getNode() {
		return this.visual;
	}

	@Override
	public String toString() {
		return "{ \"cells\": " + cells + " }";
	}
}
