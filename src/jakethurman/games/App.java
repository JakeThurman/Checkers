package jakethurman.games;

import java.util.function.Consumer;
import javafx.application.Application;
import javafx.stage.Stage;
import jakethurman.components.SafeScene;
import jakethurman.games.checkers.components.CheckersRenderer;

public class App extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {		
		Consumer<SafeScene> setScene = s -> primaryStage.setScene(s.getUnsafe());
				
		new GameChoiceScene(
			new CheckersRenderer(false), 
			new CheckersRenderer(true)
			//, new jakethurman.games.chess.components.ChessRenderer()
		).render(r -> new RenderingHandler(r).render(setScene), setScene);
		
		primaryStage.show();    	
	}
}