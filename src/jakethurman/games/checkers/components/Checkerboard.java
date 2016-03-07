package jakethurman.games.checkers.components;

import java.util.LinkedList;
import java.util.List;
import jakethurman.foundation.CleanupHandler;
import jakethurman.foundation.Disposable;
import jakethurman.foundation.Logging;
import jakethurman.components.CellIndex;
import jakethurman.components.SafeGridPane;
import jakethurman.games.checkers.CellSearchData;
import jakethurman.games.checkers.CellSearchResult;
import jakethurman.games.checkers.CheckersTurnManager;
import jakethurman.games.checkers.Settings;

public class Checkerboard implements Disposable {
	private final CheckerboardSquare[][] cells;
	private final CheckersTurnManager    turnManager;
	private final CleanupHandler         cleanup;
	private final SafeGridPane           visual;
	
	public Checkerboard(CheckersTurnManager turnManager) {
		this.turnManager = turnManager;
		this.cleanup     = new CleanupHandler(turnManager);
		this.cells       = new CheckerboardSquare[Settings.BOARD_SIZE][Settings.BOARD_SIZE];		
		this.visual      = new SafeGridPane();
		
		init();
	}
	
	@Override
	public String toString() {
		String output = "Checkerboard:\n";
		for (int row = 0; row < cells.length; row++) {
			output += "  Row " + row + "";
		    if (cells[row] != null && cells[row].length > 0) {
		        for (int column = 0; column < cells[row].length; column++) {
		            output += "\n    " + cells[row][column];
		        }
		    }
		    output += "\n";
		}
		return output;
	}
	
	// Simple required initialization logic
	private void init() {
		// Create the objects for each cell in our "Virtual DOM"
    	for (int x = 0; x < Settings.BOARD_SIZE; x++)
			for (int y = 0; y < Settings.BOARD_SIZE; y++)
				cells[x][y] = new CheckerboardSquare();
    	
    	//Add {Settings.BOARD_SIZE} rows and columns
        for (int i=0; i < Settings.BOARD_SIZE; i++) {
        	visual.addRow(Settings.SQUARE_SIZE);
        	visual.addColumn(Settings.SQUARE_SIZE);
        }
	}
		
	private CheckerboardSquare getCell(CellIndex i) {
		if (!i.isValid()) {
			Logging.report("Attempted Invalid Access! " + i.toString());
			return null;
		}
		return cells[i.x][i.y];
	}
	
	public void setJumped(CellIndex i) {
		CheckerboardSquare cell   = getCell(i);
		Checker            jumped = cell.getPiece();
		
		remove(jumped);
		turnManager.recordDeadChecker(jumped.getIsPlayer1(), jumped.getIsKing());
		jumped.dispose();
	}
	
	public void pieceIsInCell(Checker checker) {
		CellIndex pos = checker.getPos();
		getCell(pos).setPiece(checker);
		
		// Add visually
		visual.add(checker.getNode(), pos);
	}
	
	private void remove(Checker c) {
		getCell(c.getPos()).setEmpty(); // Record that the cell is now empty
		visual.remove(c.getNode()); // Remove the node
	}

	public void movePieceToCell(Checker checker, CellIndex pos) {
		remove(checker); // Remove it from it's old location
		checker.setPos(pos); // Record the new position to the checker
		pieceIsInCell(checker); // Move it to the new piece
		handleKingship(checker, pos); // Handle Kingship
		turnManager.endTurn(); // This was this players turn so call it over
	}
	
	public void handleKingship(Checker c, CellIndex pos) {
		if ((c.getIsPlayer1() && pos.y == 0) || (!c.getIsPlayer1() && pos.y == (Settings.BOARD_SIZE - 1))) {
			c.kingMe();
			turnManager.playerHasKing(c.getIsPlayer1());
		}
	}
	
	public Iterable<CellSearchResult> getAvailableSpaces(Checker checker) {
		LinkedList<CellSearchResult> results  = new LinkedList<>();
		LinkedList<CellSearchData>   original = new LinkedList<>();
		LinkedList<CellSearchData>   toCheck  = new LinkedList<>();
		
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
			CellIndex      index    = checking.getCellIndex();
			
			if (!index.isValid())
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
						if (!doubleJumpIndex.isValid())
							return false;
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
		for (CheckerboardSquare[] arr : cells) {
			for (CheckerboardSquare cell : arr) {
				cell.dispose();
			}
		}
	}

	public SafeGridPane getNode() {
		return this.visual;
	}
}
