import java.util.LinkedList;
import javafx.scene.layout.GridPane;

public class Checkerboard {
	private final CheckerboardSquare[][] cells;
	private final CheckersTurnManager    turnManager;
	public  final GridPane               visual;
	
	public Checkerboard(CheckersTurnManager turnManager) {
		this.turnManager = turnManager;
		this.cells       = new CheckerboardSquare[Settings.BOARD_SIZE][Settings.BOARD_SIZE];		
		this.visual      = new GridPane();
		
		init();
	}
	
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
    	for (int x = 0; x < Settings.BOARD_SIZE; x++)
			for (int y = 0; y < Settings.BOARD_SIZE; y++)
				cells[x][y] = new CheckerboardSquare();
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
		turnManager.recordDeadChecker(jumped.getIsPlayer1());
	}
		
	public void pieceIsInCell(Checker checker) {
		CellIndex pos = checker.getPos();
		getCell(pos).setPiece(checker);
		
		// Add visually
		visual.add(checker.getNode(), pos.x, pos.y);
	}
	
	private void remove(Checker c) {
		visual.getChildren().remove(c.getNode());
	}

	public void movePieceToCell(Checker checker, CellIndex pos) {
		getCell(checker.getPos()).setEmpty(); // Record that the old cell is now empty
		checker.setPos(pos); // Record the new position to the checker
		remove(checker); // Remove it from it's old location
		pieceIsInCell(checker); // Move it to the new piece
		handleKingship(checker, pos); // Handle Kingship
		turnManager.endTurn(); // This was this players turn so call it over
	}
	
	public void handleKingship(Checker c, CellIndex pos) {
		if ((c.getIsPlayer1() && pos.y == 0) || (!c.getIsPlayer1() && pos.y == (Settings.BOARD_SIZE - 1)))
			c.kingMe();
	}
	
	public Iterable<CellSearchResult> getAvailableSpaces(Checker checker) {
		LinkedList<CellSearchResult> results = new LinkedList<CellSearchResult>();
		LinkedList<CellSearchData> toCheck = new LinkedList<CellSearchData>();
		
		if (!checker.getIsPlayer1() || checker.getIsKing()) {
			toCheck.add(new CellSearchData(1, 1, checker.getPos()));
			toCheck.add(new CellSearchData(-1, 1, checker.getPos()));
		}
		if (checker.getIsPlayer1() || checker.getIsKing()) {
			toCheck.add(new CellSearchData(-1, -1, checker.getPos()));
			toCheck.add(new CellSearchData(1, -1, checker.getPos()));
		}
		
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
					CellSearchData doubleJump      = checking.withJumpOffset();
					CellIndex      doubleJumpIndex = doubleJump.getCellIndex();
					if (doubleJumpIndex.isValid()) {
						CheckerboardSquare doubleJumpCell = getCell(doubleJumpIndex);
						
						if (!doubleJumpCell.isEmpty() && doubleJumpCell.getPiece().getIsPlayer1() != iAmPlayer1)
							toCheck.add(doubleJump.withJumpOffset());
					}
				}
			}
			// If we aren't already doing so, see if we can jump this piece and it's not our own piece
			else if (!checking.isJump && cell.getPiece().getIsPlayer1() != iAmPlayer1) {
				toCheck.add(checking.withJumpOffset());
			}
		}
		
		return results;
	}
}
