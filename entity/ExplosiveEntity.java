package sokoban.entity;

import sokoban.game.Game;
import sokoban.game.Point;

/**
 * Entity that explodes (i.e., disappear from the board) when colliding with
 * a {@link BreakableWallEntity}.
 */
public class ExplosiveEntity extends AbstractEntity {

    public ExplosiveEntity() {}

    public ExplosiveEntity(Game game) {
        this.game = game;
    }

    @Override
    public void accept(EntityVisitor entityVisitor) {
        entityVisitor.visitExplosiveEntity(this);
    }

    @Override
    public void collideWith(Entity entity, Point delta) {
        entity.collideWithExplosiveEntity(this, delta);
    }

    @Override
    public void collideWithBreakableWallEntity(BreakableWallEntity breakableWallEntity, Point delta) {
        game.removeEntity(breakableWallEntity);
        game.removeEntity(this);
    }

    @Override
    public String toString() {
        return "anExplosive";
    }
}
