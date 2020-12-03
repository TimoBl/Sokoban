package sokoban.entity;

import sokoban.game.Game;
import sokoban.game.Point;

/**
 * Implementation of {@link AbstractEntity} that represents the player.
 *
 * The player is the main entity that can be moved around by the user and
 * provides corresponding methods. The player knows about the game and
 * delegats actual movement on the board to it.
 */
public class PlayerEntity extends AbstractEntity {
    private final Game game;

    public PlayerEntity(Game game) {
        this.game = game;
    }

    public void moveBy(Point delta) {
        game.attemptMoveEntity(this, delta);
    }

    public void moveRight() {
        moveBy(new Point(1, 0));
    }

    public void moveDown() {
        moveBy(new Point(0, 1));
    }

    public void moveLeft() {
        moveBy(new Point(-1, 0));
    }

    public void moveUp() {
        moveBy(new Point(0, -1));
    }

    @Override
    public void accept(EntityVisitor entityVisitor) {
        entityVisitor.visitPlayerEntity(this);
    }

    @Override
    public void collideWith(Entity entity, Point delta) {
        entity.collideWithPlayerEntity(this, delta);
    }

    @Override
    public void collideWithExplosiveEntity(ExplosiveEntity explosiveEntity, Point delta) {
        game.attemptMoveEntity(explosiveEntity, delta);
    }

    @Override
    public void collideWithBoxEntity(BoxEntity boxEntity, Point delta) {
        game.attemptMoveEntity(boxEntity, delta);
    }

    @Override
    public String toString() {
        return "aPlayer";
    }
}
