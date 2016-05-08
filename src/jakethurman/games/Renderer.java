package jakethurman.games;

import java.util.function.Consumer;

import jakethurman.components.SafeScene;

/* Interface for a class that handles the rendering of a game */
public interface Renderer {
	/* Render a new instance of the game */
    public void render(final Runnable rerender, final Consumer<SafeScene> setScene);
    /* Get the name of the game */
    public String getTitle();
}
