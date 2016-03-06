package jakethurman.games;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jakethurman.components.SafeBorderPane;
import jakethurman.components.SafeSceneInteraction;
import jakethurman.games.checkers.components.CheckerboardRenderer;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
    	final BorderPane content = new BorderPane();
    	final Scene      scene   = new Scene(content);
    	
    	new RenderingHandler(
    		new CheckerboardRenderer(), 
    		new SafeSceneInteraction(scene), 
    		new SafeBorderPane(content))
    			.render();
    	
        primaryStage.setScene(scene);
        primaryStage.show();    	
    }
}