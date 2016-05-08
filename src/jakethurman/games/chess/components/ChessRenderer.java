package jakethurman.games.chess.components;

import java.util.function.Consumer;
import jakethurman.components.PositionedNodes;
import jakethurman.components.SafeBorderPane;
import jakethurman.components.SafeScene;
import jakethurman.components.factories.ButtonFactory;
import jakethurman.components.factories.ShapeFactory;
import jakethurman.components.helpers.GridHelpers;
import jakethurman.games.Renderer;
import jakethurman.games.chess.Messages;
import jakethurman.games.chess.Settings;

/* 
 * Handles the rendering of a Chess game.
 */
public class ChessRenderer implements Renderer {
	// Renders a new instance of Chess
	@Override
	public void render(final Runnable rerender, final Consumer<SafeScene> setScene) {
		//Create scene
		SafeBorderPane content = new SafeBorderPane();
		SafeScene      scene   = new SafeScene(content);
		
		//Create dependencies
		final ChessboardInitialization initialization = new ChessboardInitialization(
			new GridHelpers(new ShapeFactory(new Settings())), 
			new ButtonFactory(scene));
		
		//Render the board
		Chessboard chessboard = new Chessboard();
		initialization.init(chessboard);
		
		//Render to the scene
		content.setChildren(new PositionedNodes()
			.setCenter(chessboard.getNode()));
		
		// Set the scene
		setScene.accept(scene);
	}

	// Gets the name of the game
	@Override
	public String getTitle() {
		return new Messages().getGameTitle();
	}
}
