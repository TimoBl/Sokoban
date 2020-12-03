package sokoban.entity;

import sokoban.game.Point;

/**
 * Empty implementation of the {@link Entity} interface.
 *
 * This class can be extended to avoid having to implement all methods defined
 * in {@link Entity} and get cleaner classes.
 */
public abstract class AbstractEntity extends Entity {
    public void collideWith(Entity entity, Point delta) {}
    public void collideWithBreakableWallEntity(BreakableWallEntity breakableWallEntity, Point delta) {}
    public void collideWithExplosiveEntity(ExplosiveEntity explosiveEntity, Point delta) {}
    public void collideWithBoxEntity(BoxEntity boxEntity, Point delta) {}
    public void collideWithPlayerEntity(PlayerEntity playerEntity, Point delta) {}
}
