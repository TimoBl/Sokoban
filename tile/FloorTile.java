package sokoban.tile;

import sokoban.game.Game;
import sokoban.game.GameVisitor;
import sokoban.game.Point;

/**
 * Tile representing "empty space" where the player can move and push other
 * entities to.
 */
public class FloorTile extends Tile {
    public FloorTile(Point point) {
        super(point);
    }

    @Override
    public void accept(GameVisitor gameVisitor) {
        gameVisitor.visitFloorTile(this);
    }

    @Override
    public boolean canBeOccupied() {
        return true;
    }

    @Override
    public boolean isGoalTile() {
        return false;
    }
}
