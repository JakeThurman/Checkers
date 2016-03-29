package jakethurman.games.chess.components;

import jakethurman.components.SafeGridPane;
import jakethurman.foundation.CleanupHandler;
import jakethurman.foundation.Disposable;
import jakethurman.components.helpers.GridHelpers;
import jakethurman.games.chess.Settings;

public class ChessboardInitialization implements Disposable {
	private final GridHelpers gridHelpers;
	private CleanupHandler cleanup;
	
	public ChessboardInitialization(GridHelpers gridHelpers) {
		this.gridHelpers = gridHelpers;
		this.cleanup = new CleanupHandler(gridHelpers);
	}
	
	public void init(Chessboard data) {
		initGrid(data.getNode(), Settings.BOARD_SIZE, Settings.SQUARE_SIZE);
	}
	
	private void initGrid(SafeGridPane pane, int boardHeightAndWidth, int squareSize) {
		//Add {Settings.BOARD_SIZE} rows and columns
        for (int i=0; i < boardHeightAndWidth; i++) {
        	pane.addRow(squareSize);
        	pane.addColumn(squareSize);
        }        
        
        gridHelpers.fillGridWithSquares(pane);
	}
	
	@Override
	public void dispose() {
		cleanup.dispose();
	}

}
