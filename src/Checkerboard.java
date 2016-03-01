import java.util.LinkedList;

public class Checkerboard {
	private final CheckerboardPiece[][] cells;
	
	public Checkerboard() {
		this.cells = new CheckerboardPiece[Settings.BOARD_SIZE][Settings.BOARD_SIZE];
		
		for (int x = 0; x < Settings.BOARD_SIZE; x++)
			for (int y = 0; y < Settings.BOARD_SIZE; y++)
				cells[x][y] = new CheckerboardPiece();
	}
	
	private CheckerboardPiece getPiece(CellIndex i) {
		if (!isValid(i))
			Logging.report("Attempted Invalid Access! " + i.toString());
		
		return cells[i.x][i.y];
	}
	
	private boolean isValid(CellIndex ci) {
		return ci.x < Settings.BOARD_SIZE && ci.x >= 0 
			&& ci.y < Settings.BOARD_SIZE && ci.y >= 0;
	}
		
	public void pieceIsInCell(boolean isPlayer1, CellIndex i) {
		getPiece(i).setPlayer(isPlayer1); 
	}
	
	public void cellIsEmpty(CellIndex i) {
		getPiece(i).setEmpty();
	}
	
	public Iterable<CellIndex> getAvailableSpaces(CellIndex source) {
		LinkedList<CellIndex>      results = new LinkedList<CellIndex>();
		LinkedList<CellSearchData> toCheck = new LinkedList<CellSearchData>();
		
		boolean isPlayer1 = getPiece(source).isPlayer1();

		toCheck.add(new CellSearchData(1, 1, source));
		toCheck.add(new CellSearchData(-1, -1, source));
		toCheck.add(new CellSearchData(-1, 1, source));
		toCheck.add(new CellSearchData(1, 1, source));
		
		while (!toCheck.isEmpty()) {
			CellSearchData checking = toCheck.poll();
			
			if (!isValid(checking.getCellIndex()))
				continue;
			
			// See if this is an empty space
			if (getPiece(checking.getCellIndex()).isEmpty()) {
				if (!results.contains(checking.getCellIndex()))
					results.add(checking.getCellIndex());
				
				//TODO: Check if we can double/triple jump here
			}
			// If we aren't already doing so, see if we can jump this piece and it's not our own piece
			else if (!checking.isJump && getPiece(checking.getCellIndex()).isPlayer1() != isPlayer1) {
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
	
		private CellIndex _cellIndex = null;
		
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
			return _cellIndex == null 
					? _cellIndex = new CellIndex(source.x + deltaX, source.y + deltaY)
					: _cellIndex;
		}
		
		public CellSearchData withDoubleOffsetCellIndex() {
			return new CellSearchData(deltaX * 2, deltaY * 2, source, true);
		}
	}
}
