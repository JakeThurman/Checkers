import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
    	final BorderPane pane = new BorderPane();
    	final Scene scene = new Scene(pane);
    	
    	Pane visual = CheckerBoardFactory.render(new SafeSceneInteraction(scene));
    	
    	pane.setCenter(visual);
    	
        primaryStage.setScene(scene);
        primaryStage.show();    	
    }
}