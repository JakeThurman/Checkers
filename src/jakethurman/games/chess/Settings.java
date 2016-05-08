package jakethurman.games.chess;

import jakethurman.components.factories.ShapeSettings;

// All of the chess specific settings
public class Settings implements ShapeSettings {
	/* Design Settings */
    public  static final int BOARD_SIZE    = 8,
    		                 SQUARE_SIZE   = 60;
    private static final int CIRCLE_RADIUS = SQUARE_SIZE/2-6,
	            	         CIRCLE_BORDER = 2;
	
	//Get methods
	@Override public int getCircleRadius() { return CIRCLE_RADIUS; }
	@Override public int getCircleBorder() { return CIRCLE_BORDER; }
	@Override public int getSquareSize()   { return SQUARE_SIZE;   }
	@Override public void dispose() { /* Nothing to dispose */ }
}
