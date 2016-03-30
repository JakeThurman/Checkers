package jakethurman.games;

import java.util.function.Consumer;

import jakethurman.components.SafeScene;

public interface Renderer {
    public void render(final Runnable rerender, final Consumer<SafeScene> setScene);
    public String getTitle();
}
