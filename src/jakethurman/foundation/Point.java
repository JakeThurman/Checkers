package jakethurman.foundation;

/* Handles basic X, Y named point storage */
public class Point {
	public final int x;
	public final int y;
	
	// C'tor
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "{ \"x\": " + x + ", \"y\": " + y + " }";
	}
	
	@Override
	public boolean equals(Object obj) {		
		// If the object is also a point, and the x and y are the same, it is equal.
		return obj instanceof Point && ((Point)obj).x == this.x && ((Point)obj).y == this.y;
	}
}
