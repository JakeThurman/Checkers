package jakethurman.components;

import jakethurman.foundation.Disposable;
import jakethurman.games.checkers.Settings;

public class CellIndex implements Disposable {
	public final int x;
	public final int y;
	
	public CellIndex(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean isValid() {
		return x < Settings.BOARD_SIZE && x >= 0 
			&& y < Settings.BOARD_SIZE && y >= 0;
	}
	
	@Override
	public String toString() {
		return "CellIndex: { x = " + x + ", y = " + y + " }";
	}
	
	@Override
	public boolean equals(Object obj) {		
		return obj instanceof CellIndex && ((CellIndex)obj).x == this.x && ((CellIndex)obj).y == this.y;
	}
	
	@Override
	public void dispose() {
		// Nothing to dispose
	}
}
