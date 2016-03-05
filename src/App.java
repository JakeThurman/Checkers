import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
    	final BorderPane content = new BorderPane();
    	final Scene      scene   = new Scene(content);
    	
    	renderGame(
    		new CheckerboardRenderer(), 
    		new SafeSceneInteraction(scene), 
    		content);
    	
        primaryStage.setScene(scene);
        primaryStage.show();    	
    }
    
    private void renderGame(Renderer renderer, SafeSceneInteraction scene, BorderPane content) {
    	ReadOnlyPositionedNodes data = renderer.render(scene, () -> this.renderGame(renderer, scene, content));
    	
    	content.setCenter(data.getCenter());
    	content.setLeft(data.getLeft());
    	content.setRight(data.getRight());
    	content.setTop(data.getTop());
    	content.setBottom(data.getBottom());
    	
    	data.dispose();
    }
}