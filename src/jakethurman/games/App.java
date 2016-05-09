package jakethurman.games;

import java.util.function.Consumer;
import javafx.application.Application;
import javafx.stage.Stage;
import jakethurman.components.SafeScene;
import jakethurman.games.checkers.components.CheckersRenderer;

/*
 * PROJECT TODOs:
 * 		- Add named high scores
 * 
 */
public class App extends Application {
	
	public static void main(String[] args) {
		launch(args); // Start the application
	}

	@Override
	public void start(Stage primaryStage) {
		// This is a helper function to allow for setting 
		// the scene without passing down the stage
		Consumer<SafeScene> setScene = s -> primaryStage.setScene(s.getUnsafe());
				
		// GameChoiceScene is a scene where 
		// The player can choose between any 
		// passed in renderer to render the game
		new GameChoiceScene(
			new CheckersRenderer(Difficulty.HUMAN), 
			new CheckersRenderer(Difficulty.EASY), 
			new CheckersRenderer(Difficulty.MEDIUM), 
			new CheckersRenderer(Difficulty.HARD)
			//, new jakethurman.games.chess.components.ChessRenderer() // TODO: Finish or remove
		).render(r -> new RenderingHandler(r).render(setScene), setScene); // We give render a lambda that renders the scene using a rendering handler
		
		// Show the stage!
		primaryStage.show();    	
	}
}