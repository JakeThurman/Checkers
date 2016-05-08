package jakethurman.components.factories;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

import jakethurman.components.SafeNode;
import jakethurman.components.SafeScene;
import jakethurman.foundation.Disposable;

/* Handles simple generation of buttons. */
public class ButtonFactory implements Disposable {
	private final SafeScene scene;
	
	// We need the current scene as a dependency 
	// so we can set the cursor to show the user 
	// they can click on the button.
	public ButtonFactory(SafeScene scene) {
		this.scene = scene;
	}
	
	/* Creates a button */
	public SafeNode create(String text, Runnable onclick) {
		// Create a javafx button
		Button bttn = new Button(); 
		bttn.setText(text); // With the specified text..
		bttn.setAlignment(Pos.CENTER); // centered on the button
		
		// Add normal button events for the button
		bttn.setOnMouseEntered((e) -> scene.setSelectableCursor());
		bttn.setOnMouseExited((e) -> scene.setDefaultCursor());
		bttn.setOnMouseClicked((e) -> onclick.run());
		
		// Reutrn a protected version of the node
		return new SafeNode(bttn);
	}
	
	/* Creates an image with button clicking behavior */
	public SafeNode createImageButton(String resourcesUrl, Runnable onclick) {
		// Open the image and create an imageview for it
		File file = new File(resourcesUrl);
		Image image = new Image(file.toURI().toString());
		ImageView iv = new ImageView(image);
		
		// Set the standard button events for the image.
		iv.setOnMouseEntered((e) -> scene.setSelectableCursor());
		iv.setOnMouseExited((e) -> scene.setDefaultCursor());
		iv.setOnMouseClicked((e) -> onclick.run());
		
		// Return a protected version of the node.
		return new SafeNode(iv);
	}
	
	@Override
	public void dispose() {
		// Nothing to dispose
	}
}