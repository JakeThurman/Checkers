package jakethurman.components.factories;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import jakethurman.components.SafeNode;
import jakethurman.foundation.Disposable;

public class ButtonFactory implements Disposable {
	public SafeNode create(String text, Runnable onclick) {
		Button bttn = new Button();
		bttn.setText(text);
		bttn.setAlignment(Pos.CENTER);
		bttn.setOnMouseClicked((e) -> onclick.run());
		return new SafeNode(bttn);
	}
	
	@Override
	public void dispose() {
		// Nothing to dispose
	}
}