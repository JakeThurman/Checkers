package jakethurman.games;

import java.util.function.Consumer;

import jakethurman.components.Point;
import jakethurman.components.PositionedNodes;
import jakethurman.components.SafeBorderPane;
import jakethurman.components.SafeGridPane;
import jakethurman.components.SafeScene;
import jakethurman.components.factories.ButtonFactory;
import jakethurman.components.factories.TextFactory;

public class GameChoiceScene {
	private final Renderer[] renderers;
	
	public GameChoiceScene(Renderer...renderers) {
		this.renderers = renderers;
	}
	
	public void render(Consumer<Renderer> render, Consumer<SafeScene> setScene) {
		if (!GlobalSettings.IS_DEBUG) {
			render.accept(renderers[0]);
			return;
		}
		
		SafeBorderPane content = new SafeBorderPane();
		SafeScene      scene   = new SafeScene(content);
		
		ButtonFactory bf = new ButtonFactory(scene);
		TextFactory   tf = new TextFactory();
				
		SafeGridPane buttons = new SafeGridPane();
		buttons.setPadding(10);
		content.setMinSize(200, 400);
		
		for (int i = 0; i < renderers.length; i++) {
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
