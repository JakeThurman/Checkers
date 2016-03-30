package jakethurman.games.chess.components;

import jakethurman.components.SafeGridPane;
import jakethurman.foundation.CleanupHandler;
import jakethurman.foundation.Disposable;
import jakethurman.components.helpers.GridHelpers;
import jakethurman.games.chess.Settings;
import jakethurman.components.Point;
import jakethurman.games.chess.pieces.*;

public class ChessboardInitialization implements Disposable {
	private final GridHelpers gridHelpers;
	private CleanupHandler cleanup;
	
	public ChessboardInitialization(GridHelpers gridHelpers) {
		this.gridHelpers = gridHelpers;
		this.cleanup = new CleanupHandler(gridHelpers);
	}
	
	public void init(Chessboard data) {
		initGrid(data.getNode());
		initPieces(data);
	}
	
	private static void initPieces(Chessboard data) {
		for (int i = 0; i < Settings.BOARD_SIZE; i++) {
			data.initPeice(new Pawn(true), new Point(i, Settings.BOARD_SIZE - 2));
			data.initPeice(new Pawn(false), new Point(i, 1));
			data.initPeice(getOuterRowPeice(i, true), new Point(i, Settings.BOARD_SIZE - 1));
			data.initPeice(getOuterRowPeice(i, false), new Point(i, 0));
		}
	}	
	
	private static ChessPiece getOuterRowPeice(int i, boolean isWhite) {
		switch (i) {
			case 0:
			case 7:
				return new Rook(isWhite);
			case 1:
			case 6:
				return new Knight(isWhite);
			case 2:
			case 5:
				return new Bishop(isWhite);
			case 3:
				return new King(isWhite);
			case 4:
				return new Queen(isWhite);
			default:
				return null;
		}
	}
	
	private void initGrid(SafeGridPane pane) {
		//Add {Settings.BOARD_SIZE} rows and columns
		for (int i=0; i < Settings.BOARD_SIZE; i++) {
			pane.addRow(Settings.SQUARE_SIZE);
			pane.addColumn(Settings.SQUARE_SIZE);
		}
		
		gridHelpers.fillGridWithSquares(pane);
	}
	
	@Override
	public void dispose() {
		cleanup.dispose();
	}

}
