package jakethurman.games.chess.components;

import java.util.function.Consumer;

import jakethurman.components.PositionedNodes;
import jakethurman.components.ReadOnlyPositionedNodes;
import jakethurman.components.SafeGridPane;
import jakethurman.components.SafeScene;
import jakethurman.components.factories.ShapeFactory;
import jakethurman.games.GridHelpers;
import jakethurman.games.Renderer;
import jakethurman.games.chess.Settings;

public class ChessRenderer implements Renderer {
	@Override
	public ReadOnlyPositionedNodes render(final SafeScene scene, final Runnable rerender, final Consumer<SafeScene> setScene) {
		//Create dependencies
		Settings settings = new Settings();
		GridHelpers gridHelpers = new GridHelpers(new ShapeFactory(settings));
		
		//Render the board
		SafeGridPane board = new SafeGridPane();
		initGrid(board, settings.getBoardSize(), settings.getSquareSize());
		gridHelpers.fillGridWithSquares(board);
    	
		return new PositionedNodes()
			.setCenter(board);
	}
	
	private static void initGrid(SafeGridPane pane, int boardHeightAndWidth, int squareSize) {
		//Add {Settings.BOARD_SIZE} rows and columns
        for (int i=0; i < boardHeightAndWidth; i++) {
        	pane.addRow(squareSize);
        	pane.addColumn(squareSize);
        }
        
        
	}
}
