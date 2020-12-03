package sokoban.game;

import sokoban.io.input.InputHandler;
import sokoban.io.input.StdinInputHandler;
import sokoban.io.output.PlaintextRenderer;
import sokoban.io.output.Renderer;

public abstract class SokobanObjectProvider {
    private static SokobanObjectProvider instance;

    protected SokobanObjectProvider() {}

    public static SokobanObjectProvider instance() {
        if (instance == null) {
            instance = defaultInstance();
        }
        return instance;
    }

    public static SokobanObjectProvider defaultInstance() {
        return new DefaultSokobanObjectProvider();
    }

    public static void setProvider(SokobanObjectProvider provider) {
        instance = provider;
    }

    public abstract Renderer getRenderer();

    public abstract InputHandler getInputHandler();

    private static class DefaultSokobanObjectProvider extends SokobanObjectProvider {
        @Override
        public Renderer getRenderer() {
            return new PlaintextRenderer();
        }

        @Override
        public InputHandler getInputHandler() {
            return new StdinInputHandler();
        }
    }
}
