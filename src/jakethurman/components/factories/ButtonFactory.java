package jakethurman.components.factories;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import jakethurman.components.SafeNode;
import jakethurman.components.SafeSceneInteraction;
import jakethurman.foundation.Disposable;

public class ButtonFactory implements Disposable {
	private final SafeSceneInteraction scene;
	
	public ButtonFactory(SafeSceneInteraction scene) {
		this.scene = scene;
	}
	
	public SafeNode create(String text, Runnable onclick) {
		Button bttn = new Button();
		bttn.setText(text);
		bttn.setAlignment(Pos.CENTER);
		bttn.setOnMouseEntered((e) -> scene.setSelectableCursor());
		bttn.setOnMouseExited((e) -> scene.setDefaultCursor());
		bttn.setOnMouseClicked((e) -> onclick.run());
		return new SafeNode(bttn);
	}
	
	@Override
	public void dispose() {
		// Nothing to dispose
	}
}