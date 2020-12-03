package sokoban.entity;

import sokoban.game.Game;
import sokoban.game.Point;

/**
 * An entity is a object on the board with the ability to collide with other
 * entity.  The main purpose of this class/interface is to provide methods for
 * collisions.
 *
 * {@link AbstractEntity} is a default implementation of this interface.
 */
public abstract class Entity implements CollisionVisitor {
    public abstract void accept(EntityVisitor entityVisitor);

    protected Game game;
    public void setGame(Game game) {
        this.game = game;
    }
}
