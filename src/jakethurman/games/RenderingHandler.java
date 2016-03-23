package jakethurman.games;

import java.util.function.Consumer;

import jakethurman.components.ReadOnlyPositionedNodes;
import jakethurman.components.SafeBorderPane;
import jakethurman.components.SafeScene;

public class RenderingHandler {
	private final Renderer       renderer;
	private final SafeScene      scene;
	private final SafeBorderPane content;
	
	public RenderingHandler(Renderer renderer, SafeScene scene, SafeBorderPane content) {
		this.renderer = renderer;
		this.scene    = scene;
		this.content  = content;
	}
    
    public void render(Consumer<SafeScene> setScene) {
    	ReadOnlyPositionedNodes data = renderer.render(scene, () -> this.render(setScene), setScene);
    	
    	content.setChildren(data);
    	
    	data.dispose();
    }
}
