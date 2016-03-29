package jakethurman.games;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jakethurman.components.SafeBorderPane;
import jakethurman.components.SafeScene;
import jakethurman.games.checkers.components.CheckersRenderer;

public class App extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		final BorderPane content = new BorderPane();
		final Scene      scene   = new Scene(content);
		
		new RenderingHandler(
			new CheckersRenderer(), //We will only ever render checkers at this time
			new SafeScene(scene), 
			new SafeBorderPane(content))
				.render(s -> primaryStage.setScene(s.getUnsafe()));
		
		primaryStage.setScene(scene);
		primaryStage.show();    	
	}
}