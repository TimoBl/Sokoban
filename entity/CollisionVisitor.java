package sokoban.entity;

import sokoban.game.Point;

/**
 * Interface providing methods for colliding with entities.
 */
public interface CollisionVisitor {
    void collideWith(Entity entity, Point delta);
    void collideWithBreakableWallEntity(BreakableWallEntity breakableWallEntity, Point delta);
    void collideWithExplosiveEntity(ExplosiveEntity explosiveEntity, Point delta);
    void collideWithBoxEntity(BoxEntity boxEntity, Point delta);
    void collideWithPlayerEntity(PlayerEntity playerEntity, Point delta);
}
