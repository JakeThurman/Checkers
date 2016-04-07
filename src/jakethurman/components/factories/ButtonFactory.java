package jakethurman.components.factories;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

import jakethurman.components.SafeNode;
import jakethurman.components.SafeScene;
import jakethurman.foundation.Disposable;

public class ButtonFactory implements Disposable {
	private final SafeScene scene;
	
	public ButtonFactory(SafeScene scene) {
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
	
	public SafeNode createImageButton(String resourcesUrl, Runnable onclick) {
		File file = new File(resourcesUrl);
		Image image = new Image(file.toURI().toString());
		ImageView iv = new ImageView(image);
		
		iv.setOnMouseEntered((e) -> scene.setSelectableCursor());
		iv.setOnMouseExited((e) -> scene.setDefaultCursor());
		iv.setOnMouseClicked((e) -> onclick.run());
		
		return new SafeNode(iv);
	}
	
	@Override
	public void dispose() {
		// Nothing to dispose
	}
}