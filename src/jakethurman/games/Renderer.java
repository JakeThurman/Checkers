package jakethurman.games;

import jakethurman.components.ReadOnlyPositionedNodes;
import jakethurman.components.SafeSceneInteraction;

public interface Renderer {
    public ReadOnlyPositionedNodes render(final SafeSceneInteraction scene, Runnable rerender);
}
