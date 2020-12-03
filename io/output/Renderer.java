package sokoban.io.output;

import sokoban.game.Game;
import sokoban.game.GameVisitor;

/**
 * A renderer passes through the game and renders it's current state.
 */
public interface Renderer extends GameVisitor {
    void render(Game game);
    void renderSuccess(Game game);
    void renderFailure(Game game);
}
