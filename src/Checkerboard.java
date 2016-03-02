import java.util.LinkedList;
import javafx.scene.layout.GridPane;

public class Checkerboard {
	private final CheckerboardSquare[][] cells;
	public  final GridPane visual;
	
	public Checkerboard() {
		this.cells  = new CheckerboardSquare[Settings.BOARD_SIZE][Settings.BOARD_SIZE];		
		this.visual = new GridPane();
		
		init();
	}
	
	// Simple required initialization logic
	private void init() {
    	for (int x = 0; x < Settings.BOARD_SIZE; x++)
			for (int y = 0; y < Settings.BOARD_SIZE; y++)
				cells[x][y] = new CheckerboardSquare();
	}
		
	private CheckerboardSquare getCell(CellIndex i) {
		if (!isValid(i))
		{
			Logging.report("Attempted Invalid Access! " + i.toString());
			return null;
		}
		return cells[i.x][i.y];
	}
	
	private boolean isValid(CellIndex ci) {
		return ci.x < Settings.BOARD_SIZE && ci.x >= 0 
			&& ci.y < Settings.BOARD_SIZE && ci.y >= 0;
	}
		
	public void pieceIsInCell(Checker checker) {
		CellIndex pos = checker.getPos();
		getCell(pos).setPiece(checker);
		
		// Add visually
		visual.add(checker.getNode(), pos.x, pos.y);
	}
	
	public void cellIsEmpty(CellIndex i) {
		getCell(i).setEmpty();
	}
	
	public Iterable<CellIndex> getAvailableSpaces(Checker checker) {
		LinkedList<CellIndex>      results = new LinkedList<CellIndex>();
		LinkedList<CellSearchData> toCheck = new LinkedList<CellSearchData>();
		
		toCheck.add(new CellSearchData(1, 1, checker.getPos()));
		toCheck.add(new CellSearchData(-1, -1, checker.getPos()));
		toCheck.add(new CellSearchData(-1, 1, checker.getPos()));
		toCheck.add(new CellSearchData(1, -1, checker.getPos()));
		
		while (!toCheck.isEmpty()) {
			CellSearchData checking = toCheck.poll();
			CellIndex      index    = checking.getCellIndex();
			
			if (!isValid(index))
				continue;
						
			// See if this is an empty space
			CheckerboardSquare cell = getCell(index);
			
			if (cell.isEmpty()) {
				if (!results.contains(index))
					results.add(index);
				
				//TODO: Check if we can double/triple jump here
			}
			// If we aren't already doing so, see if we can jump this piece and it's not our own piece
			else if (!checking.isJump && cell.getPiece().isPlayer1() != checker.isPlayer1()) {
				toCheck.add(checking.withDoubleOffsetCellIndex());
			}
		}
		
		return results;
	}
	
	private class CellSearchData {
		private final CellIndex source;
		private final int       deltaX, 
		                        deltaY;
		public  final boolean   isJump;
			
		public CellSearchData(int deltaX, int deltaY, CellIndex source) {
			this(deltaX, deltaY, source, false);
		}

		private CellSearchData(int deltaX, int deltaY, CellIndex source, boolean isJump) {
			this.deltaX = deltaX;
			this.deltaY = deltaY;
			this.source = source;
			this.isJump = isJump;
		}
		
		public CellIndex getCellIndex() {
			return new CellIndex(source.x + deltaX, source.y + deltaY);
		}
		
		public CellSearchData withDoubleOffsetCellIndex() {
			return new CellSearchData(deltaX * 2, deltaY * 2, source, true);
		}
	}
}
