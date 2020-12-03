package sokoban.entity;

import sokoban.game.Game;
import sokoban.game.Point;

/**
 * Implementation of an {@link AbstractEntity} representing a pushable box.
 *
 * Box entities need to be pushed onto {@link sokoban.tile.GoalTile} tiles in
 * order to win the game.
 */
public class BoxEntity extends AbstractEntity {
    @Override
    public void accept(EntityVisitor entityVisitor) {
        entityVisitor.visitBoxEntity(this);
    }

    @Override
    public void collideWith(Entity entity, Point delta) {
        entity.collideWithBoxEntity(this, delta);
    }

    @Override
    public String toString() {
        return "aBox";
    }
}
