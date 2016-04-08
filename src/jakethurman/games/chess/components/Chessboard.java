package jakethurman.games.chess.components;

import jakethurman.components.SafeGridPane;
import jakethurman.components.SafeNode;
import jakethurman.foundation.Disposable;
import jakethurman.foundation.datastructures.SquareFixedAndFilled2DArray;
import jakethurman.games.BoardSpace;
import jakethurman.games.chess.Settings;
import jakethurman.games.chess.pieces.ChessPiece;

public class Chessboard implements Disposable {
	private final SquareFixedAndFilled2DArray<BoardSpace<ChessPiece>> cells;
	private final SafeGridPane visual;
	
	public Chessboard() {
		this.cells = new SquareFixedAndFilled2DArray<>(Settings.BOARD_SIZE, () -> new BoardSpace<>());
		this.visual = new SafeGridPane();
	}
	
	public void initPiece(ChessPiece piece, SafeNode node) {		
		cells.get(piece.getPoint()).setPiece(piece);
		visual.add(node, piece.getPoint());
	}
	
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
