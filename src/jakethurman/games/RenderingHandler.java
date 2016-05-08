package jakethurman.games;

import java.util.function.Consumer;
import jakethurman.components.SafeScene;

//Handles rendering
public class RenderingHandler {
	private final Renderer renderer;
	
	// C'tor
	public RenderingHandler(Renderer renderer) {
		this.renderer = renderer;
	}
    
	// Renders the given renderer
    public void render(Consumer<SafeScene> setScene) {
    	renderer.render(() -> this.render(setScene), setScene);
    }
}
