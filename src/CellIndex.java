public class CellIndex {
	public final int x;
	public final int y;
	
	public CellIndex(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public String toString() {
		return "CellIndex: { x = " + x + ", y = " + y + " }";
	}
	
	public boolean equals(Object obj) {		
		return obj instanceof CellIndex && ((CellIndex)obj).x == this.x && ((CellIndex)obj).y == this.y;
	}
}
