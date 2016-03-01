import java.util.Iterator;
import java.util.LinkedList;

public class Checkerboard {
	private final CheckerboardPiece[][] cells;
	
	public Checkerboard(final int size) {
		this.cells = new CheckerboardPiece[size][size];
		
		for (int x = 0; x < size; x++)
			for (int y = 0; y < size; y++)
				cells[x][y] = new CheckerboardPiece();
	}
	
	private CheckerboardPiece getPiece(CellIndex i) {
		return cells[i.x][i.y];
	}
		
	public void pieceIsInCell(boolean isPlayer1, CellIndex i) {
		getPiece(i).setPlayer(isPlayer1); 
	}
	
	public void cellIsEmpty(CellIndex i) {
		getPiece(i).setEmpty();
	}
	
	public Iterator<CellIndex> getAvailableSpaces(CellIndex source) {
		LinkedList<CellIndex> results = new LinkedList<CellIndex>();
		results.add(new CellIndex(1, 2));
		
		return results.iterator();
	}
}
