package jakethurman.components;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class SafePaint {
	public static final SafePaint RED           = new SafePaint(Color.RED);
	public static final SafePaint BLUE          = new SafePaint(Color.BLUE);
	public static final SafePaint BLACK         = new SafePaint(Color.BLACK);
	public static final SafePaint WHITE         = new SafePaint(Color.WHITE);
	public static final SafePaint DEEPPINK      = new SafePaint(Color.DEEPPINK);
	public static final SafePaint LIGHTBLUE     = new SafePaint(Color.LIGHTBLUE);
	public static final SafePaint DARKSLATEGRAY = new SafePaint(Color.DARKSLATEGRAY);
	
	private final Paint paint;
	
	public SafePaint(Paint p) {
		this.paint = p;
	}
	
	//TODO: make protected
	public Paint getUnsafe() {
		return paint;
	}
}
