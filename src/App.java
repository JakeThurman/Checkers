import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App  extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
    	final BorderPane pane = new BorderPane();
    	final Scene scene = new Scene(pane);
    	
    	Pane checkerBoard = new CheckerBoardFactory()
    	    .render(new CheckerboardInteractionManager(new SafeSceneInteraction(scene)));
    	
    	pane.setCenter(checkerBoard);
        
        primaryStage.setScene(scene);
        primaryStage.show();    	
    }
}