package jakethurman.components;

import jakethurman.foundation.Disposable;

public class CellIndex implements Disposable {
	public final int x;
	public final int y;
	
	public CellIndex(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "{ x: " + x + ", y: " + y + " }";
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
