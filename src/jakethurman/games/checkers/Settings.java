package jakethurman.games.checkers;

import jakethurman.components.SafePaint;
import jakethurman.components.factories.ShapeSettings;

public class Settings implements ShapeSettings {
    private static final int BOARD_SIZE    = 8,
                             SQUARE_SIZE   = 60,
                             NUM_PIECES    = 12,
                             CIRCLE_RADIUS = SQUARE_SIZE/2-6,
                             CIRCLE_BORDER = 2;
            
    private static final SafePaint SELECTED_COLOR = SafePaint.BLUE;
    
    //Get methods
	@Override public int getCircleRadius() { return CIRCLE_RADIUS; }
	@Override public int getCircleBorder() { return CIRCLE_BORDER; }
	@Override public int getSquareSize()   { return SQUARE_SIZE;   }
	public int getNumPieces() { return NUM_PIECES; }
	public int getBoardSize() { return BOARD_SIZE; }	
	public SafePaint getSelectColor() { return SELECTED_COLOR; }
	
	@Override public void dispose() { /* Nothing to dispose */ }
}
