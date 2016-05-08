package jakethurman.games.chess.components;

import java.util.function.BiConsumer;
import jakethurman.components.SafeGridPane;
import jakethurman.components.SafeNode;
import jakethurman.components.factories.ButtonFactory;
import jakethurman.components.helpers.GridHelpers;
import jakethurman.foundation.CleanupHandler;
import jakethurman.foundation.Disposable;
import jakethurman.foundation.Point;
import jakethurman.games.chess.Settings;
import jakethurman.games.chess.pieces.*;

/* 
 * Handles the initialization of a chess board.
 */
public class ChessboardInitialization implements Disposable {
	// Dependencies
	private final GridHelpers gridHelpers;
	private final ButtonFactory buttonFactory;
	private final CleanupHandler cleanup;
	
	// C'tor
	public ChessboardInitialization(GridHelpers gridHelpers, ButtonFactory buttonFactory) {
		this.gridHelpers = gridHelpers;
		this.buttonFactory = buttonFactory;
		this.cleanup = new CleanupHandler(gridHelpers, buttonFactory);
	}
	
	// Initializes the chess board
	public void init(Chessboard data) {
		initGrid(data.getNode());
		createPieces((peice, point) -> {
			peice.setPoint(point);
			
			SafeNode node = buttonFactory.createImageButton(peice.getImagePath(), 
				() -> System.out.println("You Clicked Me! - " + peice.toString()));
			
			data.initPiece(peice, node);
		});
	}
	
	// Creates all of the pieces for a board and passes the Piece and its Point into @initPiece
	private static void createPieces(BiConsumer<ChessPiece, Point> initPiece) {
		for (int i = 0; i < Settings.BOARD_SIZE; i++) {
			initPiece.accept(new Pawn(true), new Point(i, Settings.BOARD_SIZE - 2));
			initPiece.accept(new Pawn(false), new Point(i, 1));
			initPiece.accept(getOuterRowPeice(i, true), new Point(i, Settings.BOARD_SIZE - 1));
			initPiece.accept(getOuterRowPeice(i, false), new Point(i, 0));
		}
	}
	
	// Creates the appropriate chess piece for a cell in the outer row.
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
	
	// Initializes the grid for a chess board.
	private void initGrid(SafeGridPane pane) {
		//Add {Settings.BOARD_SIZE} rows and columns
		for (int i=0; i < Settings.BOARD_SIZE; i++) {
			pane.addRow(Settings.SQUARE_SIZE);
			pane.addColumn(Settings.SQUARE_SIZE);
		}
		
		// Initialize the background rectangles for each cell
		gridHelpers.fillGridWithSquares(pane);
	}
	
	@Override
	public void dispose() {
		cleanup.dispose();
	}

}
