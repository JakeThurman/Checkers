package jakethurman.games.chess.components;

import java.util.function.Consumer;

import jakethurman.components.PositionedNodes;
import jakethurman.components.ReadOnlyPositionedNodes;
import jakethurman.components.SafeScene;
import jakethurman.components.factories.ShapeFactory;
import jakethurman.components.helpers.GridHelpers;
import jakethurman.games.Renderer;
import jakethurman.games.chess.Settings;

public class ChessRenderer implements Renderer {
	@Override
	public ReadOnlyPositionedNodes render(final SafeScene scene, final Runnable rerender, final Consumer<SafeScene> setScene) {
		//Create dependencies
		final ChessboardInitialization initialization = new ChessboardInitialization(new GridHelpers(new ShapeFactory(new Settings())));
		
		//Render the board
		Chessboard chessboard = new Chessboard();
		initialization.init(chessboard);
		
		return new PositionedNodes()
			.setCenter(chessboard.getNode());
	}
}
