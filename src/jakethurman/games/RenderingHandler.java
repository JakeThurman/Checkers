package jakethurman.games;

import java.util.function.Consumer;
import jakethurman.components.SafeScene;

public class RenderingHandler {
	private final Renderer renderer;
	
	public RenderingHandler(Renderer renderer) {
		this.renderer = renderer;
	}
    
    public void render(Consumer<SafeScene> setScene) {
    	renderer.render(() -> this.render(setScene), setScene);
    }
}
