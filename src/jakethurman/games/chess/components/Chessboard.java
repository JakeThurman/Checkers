package jakethurman.games.chess.components;

import jakethurman.components.SafeGridPane;
import jakethurman.components.SafeNode;
import jakethurman.foundation.Disposable;
import jakethurman.foundation.datastructures.SquareFixedAndFilled2DArray;
import jakethurman.games.BoardSpace;
import jakethurman.games.chess.Settings;
import jakethurman.games.chess.pieces.ChessPiece;

/*
 * Manages all of the cells on chess board.
 */
public class Chessboard implements Disposable {
	// Local variables
	private final SquareFixedAndFilled2DArray<BoardSpace<ChessPiece>> cells;
	private final SafeGridPane visual;
	
	// C'tor
	public Chessboard() {
		this.cells = new SquareFixedAndFilled2DArray<>(Settings.BOARD_SIZE, () -> new BoardSpace<>());
		this.visual = new SafeGridPane();
	}
	
	// Initializes a piece on the board
	public void initPiece(ChessPiece piece, SafeNode node) {		
		cells.get(piece.getPoint()).setPiece(piece); // Place the piece on the board
		visual.add(node, piece.getPoint()); // Render piece's node
	}
	
	// Returns the rendered node for the Chessboard
	public SafeGridPane getNode() {
		return visual;
	}
	
	@Override
	public void dispose() {
		// Nothing to dispose
	}

	@Override
	public String toString() {
		return "{ \"cells\": " + cells + " }";
	}
}
