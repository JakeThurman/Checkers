package jakethurman.games.checkers;

import jakethurman.components.SafePaint;

public class Settings {
    public static final int BOARD_SIZE    = 8,
                            SQUARE_SIZE   = 60,
                            NUM_PIECES    = 12,
                            CIRCLE_RADIUS = SQUARE_SIZE/2-6,
                            CIRCLE_BORDER = 2;
    
    public static final SafePaint SELECTED_COLOR = SafePaint.BLUE;
}
