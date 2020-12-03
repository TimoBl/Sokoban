package sokoban.tile;

import sokoban.entity.Entity;
import sokoban.game.Game;
import sokoban.game.GameVisitor;
import sokoban.game.Point;

/**
 * A tile is a fixed element on the board, e.g. a wall or a floor.
 *
 * Tiles can not be moved, but may interact with entities. For example, the
 * behaviour of a player differs depending on whether the tile in front of it
 * is a wall or a floor.
 */
public abstract class Tile {
    protected final Point position;
    protected Entity entity;

    public Tile(Point position) {
        this.position = position;
    }

    public abstract void accept(GameVisitor gameVisitor);

    public abstract boolean canBeOccupied();

    public Point getPosition() { return position; }

    public abstract boolean isGoalTile();

    public void enter(Entity anEntity) {
        assert this.entity == null;
        this.entity = anEntity;
    }

    public void leave(Entity anEntity) {
        assert anEntity == this.entity;
        this.entity = null;
    }
}
