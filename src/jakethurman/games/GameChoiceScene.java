package jakethurman.games;

import java.util.function.Consumer;

import jakethurman.components.PositionedNodes;
import jakethurman.components.SafeBorderPane;
import jakethurman.components.SafeGridPane;
import jakethurman.components.SafeScene;
import jakethurman.components.factories.ButtonFactory;
import jakethurman.components.factories.TextFactory;
import jakethurman.foundation.Point;

/*
 * Creates a scene where the user can choose 
 * a game to play. All of these are passed 
 * into the constructor as Renderer instances
 */
public class GameChoiceScene {
	private final Renderer[] renderers;
	
	public GameChoiceScene(Renderer...renderers) {
		this.renderers = renderers;
	}
	
	public void render(Consumer<Renderer> render, Consumer<SafeScene> setScene) {		
		// Create a scene to show the game in
		SafeBorderPane content = new SafeBorderPane();
		SafeScene      scene   = new SafeScene(content);
		
		// Create the factories we need
		ButtonFactory bf = new ButtonFactory(scene);
		TextFactory   tf = new TextFactory();
		
		// Create a container for the game-buttons
		SafeGridPane buttons = new SafeGridPane();
		buttons.setPadding(10); // Give the buttons some space
		content.setMinSize(200, 400); // TODO: This value is hard coded
		
		// Look through each Renderer instance 
		// and add a button to the display.
		for (int i = 0; i < renderers.length; i++) {
			final Renderer r = renderers[i];
			buttons.addColumn();
			// Create and add a button for the renderer 
			// with the game title as the text and that 
			// renders the game on button click.
			buttons.add(bf.create(r.getTitle(), () -> render.accept(r)), new Point(i, 0));
		}
		
		// Put the buttons and a scene title message 
		// ("Choose a Game:") on the scene's content
		content.setChildren(new PositionedNodes()
			.setCenter(tf.createLeftAlign("Choose a Game:")) //TODO: !! Localize me
			.setBottom(buttons));
		
		// Call set scene
		setScene.accept(scene);
	}	
}
