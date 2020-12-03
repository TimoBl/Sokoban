package sokoban.game;

import sokoban.io.input.InputHandler;
import sokoban.io.output.NullRenderer;
import sokoban.io.output.Renderer;

public class TestObjectProvider extends SokobanObjectProvider {
    private InputHandler handler;
    private Renderer renderer;

    public TestObjectProvider(InputHandler handler) {
        setHandler(handler);
    }

    @Override
    public Renderer getRenderer() {
        return renderer;
    }

    @Override
    public InputHandler getInputHandler() {
        return handler;
    }

    public void setHandler(InputHandler handler) {
        this.handler = handler;
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }
}
