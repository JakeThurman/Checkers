package jakethurman.games;

import java.util.function.Consumer;

import jakethurman.components.ReadOnlyPositionedNodes;
import jakethurman.components.SafeScene;

public interface Renderer {
    public ReadOnlyPositionedNodes render(final SafeScene scene, final Runnable rerender, final Consumer<SafeScene> setScene);
}
