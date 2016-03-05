public interface Renderer {
    public ReadOnlyPositionedNodes render(final SafeSceneInteraction scene, Runnable rerender);
}
