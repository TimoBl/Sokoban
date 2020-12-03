package sokoban.game;

import sokoban.tile.TileVisitor;
import sokoban.entity.EntityVisitor;

/**
 * Interface for visiting games, entities, and tiles.
 */
public interface GameVisitor extends EntityVisitor, TileVisitor {
    void visitGame(Game game);
}
