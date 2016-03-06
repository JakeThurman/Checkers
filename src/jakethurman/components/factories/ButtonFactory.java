package jakethurman.components.factories;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import jakethurman.foundation.Disposable;

public class ButtonFactory implements Disposable {
	public Node create(String text, Runnable onclick) {
		Button bttn = new Button();
		bttn.setText(text);
		bttn.setAlignment(Pos.CENTER);
		bttn.setOnMouseClicked((e) -> onclick.run());
		return bttn;
	}
	
	public void dispose() {
		// Nothing to dispose
	}
};