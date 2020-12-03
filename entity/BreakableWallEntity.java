package sokoban.entity;

import sokoban.game.Game;
import sokoban.game.Point;

/**
 * Implementation of an {@link AbstractEntity} that is removed from the board
 * when colliding with an {@link ExplosiveEntity}.
 */
public class BreakableWallEntity extends AbstractEntity {
    @Override
    public void accept(EntityVisitor entityVisitor) {
        entityVisitor.visitBreakableWallEntity(this);
    }

    @Override
    public void collideWith(Entity entity, Point delta) {
        entity.collideWithBreakableWallEntity(this, delta);
    }

    @Override
    public String toString() {
        return "aBreakableWall";
    }
}
