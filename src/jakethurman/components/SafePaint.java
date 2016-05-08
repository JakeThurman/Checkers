package jakethurman.components;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

// Basic Color class wrapping javafx.scene.paint.Color more than anything.
public class SafePaint {
	// Constant value wrapper instances
	public static final SafePaint RED           = new SafePaint(Color.RED);
	public static final SafePaint BLUE          = new SafePaint(Color.BLUE);
	public static final SafePaint BLACK         = new SafePaint(Color.BLACK);
	public static final SafePaint WHITE         = new SafePaint(Color.WHITE);
	public static final SafePaint DEEPPINK      = new SafePaint(Color.DEEPPINK);
	public static final SafePaint LIGHTBLUE     = new SafePaint(Color.LIGHTBLUE);
	public static final SafePaint DARKSLATEGRAY = new SafePaint(Color.DARKSLATEGRAY);
	
	//Base value
	private final Paint paint;
	
	// C'tor
	public SafePaint(Paint p) {
		this.paint = p;
	}
	
	// Get the underlying paint value
	public Paint getUnsafe() {
		return paint;
	}
}
