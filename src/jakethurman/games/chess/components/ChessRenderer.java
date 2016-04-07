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

public class ChessRenderer implements Renderer {
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
		
		setScene.accept(scene);
	}

	@Override
	public String getTitle() {
		return new Messages().getGameTitle();
	}
}
