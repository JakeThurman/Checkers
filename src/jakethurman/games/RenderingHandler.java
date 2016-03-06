package jakethurman.games;

import jakethurman.components.ReadOnlyPositionedNodes;
import jakethurman.components.SafeBorderPane;
import jakethurman.components.SafeSceneInteraction;

public class RenderingHandler {
	private final Renderer             renderer;
	private final SafeSceneInteraction scene;
	private final SafeBorderPane       content;
	
	public RenderingHandler(Renderer renderer, SafeSceneInteraction scene, SafeBorderPane content) {
		this.renderer = renderer;
		this.scene    = scene;
		this.content  = content;
	}
    
    public void render() {
    	ReadOnlyPositionedNodes data = renderer.render(scene, () -> this.render());
    	
    	content.setChildren(data);
    	
    	data.dispose();
    }
}
