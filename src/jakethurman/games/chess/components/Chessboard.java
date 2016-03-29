package jakethurman.games.chess.components;

import java.util.Arrays;

import jakethurman.components.SafeGridPane;
import jakethurman.foundation.Disposable;
import jakethurman.games.chess.Settings;
import jakethurman.games.chess.components.ChessboardSquare;

public class Chessboard implements Disposable {
	private final ChessboardSquare[][] cells;
	private final SafeGridPane visual;
	
	public Chessboard() {
		this.cells = new ChessboardSquare[Settings.BOARD_SIZE][Settings.BOARD_SIZE];
		this.visual = new SafeGridPane();
		
		init();
	}
	
	@Override
	public String toString() {
		String output = "{ cells: [";
		for (int row = 0; row < cells.length; row++) {
			output += Arrays.toString(cells[row]);
					    
		    if (row != cells.length - 1)
		       output += ", ";
		}
		return output + "] }";
	}
	
	private void init() {
		// Create the objects for each cell in our "Virtual DOM"
    	for (int x = 0; x < Settings.BOARD_SIZE; x++)
			for (int y = 0; y < Settings.BOARD_SIZE; y++)
				cells[x][y] = new ChessboardSquare();
		
		System.out.println(toString());
	}
	
	public SafeGridPane getNode() {
		return visual;
	}
	
	@Override
	public void dispose() {
		// Nothing to dispose
	}
}