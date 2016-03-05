import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
    	final BorderPane pane = new BorderPane();
    	final Scene scene = new Scene(pane);
    	
    	ReadOnlyPositionedNodes board = Rendering.renderCheckerboard(new SafeSceneInteraction(scene));
    	
    	pane.setCenter(board.getCenter());
    	pane.setLeft(board.getLeft());
    	pane.setRight(board.getRight());
    	pane.setTop(board.getTop());
    	pane.setBottom(board.getBottom());
    	
        primaryStage.setScene(scene);
        primaryStage.show();    	
    }
}