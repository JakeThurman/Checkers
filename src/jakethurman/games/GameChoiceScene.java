package jakethurman.games;

import java.util.function.Consumer;

import jakethurman.components.PositionedNodes;
import jakethurman.components.SafeBorderPane;
import jakethurman.components.SafeGridPane;
import jakethurman.components.SafeScene;
import jakethurman.components.factories.ButtonFactory;
import jakethurman.components.factories.TextFactory;
import jakethurman.foundation.Point;

public class GameChoiceScene {
	private final Renderer[] renderers;
	
	public GameChoiceScene(Renderer...renderers) {
		this.renderers = renderers;
	}
	
	public void render(Consumer<Renderer> render, Consumer<SafeScene> setScene) {		
		SafeBorderPane content = new SafeBorderPane();
		SafeScene      scene   = new SafeScene(content);
		
		ButtonFactory bf = new ButtonFactory(scene);
		TextFactory   tf = new TextFactory();
		
		SafeGridPane buttons = new SafeGridPane();
		buttons.setPadding(10);
		content.setMinSize(200, 400);
		int i = 0;
		for (; i < renderers.length; i++) {
			final Renderer r = renderers[i];
			buttons.addColumn();
			buttons.add(bf.create(r.getTitle(), () -> render.accept(r)), new Point(i, 0));
		}
		
		content.setChildren(new PositionedNodes()
			.setCenter(tf.createLeftAlign("Choose a Game:")) //TODO: Localize me
			.setBottom(buttons));
		
		setScene.accept(scene);
	}	
}
