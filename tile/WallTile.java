package sokoban.tile;

import sokoban.game.Game;
import sokoban.game.GameVisitor;
import sokoban.game.Point;

/**
 * Tile representing a fixed wall that can not be occupied.
 */
public class WallTile extends Tile {
    public WallTile(Point position) {
        super(position);
    }

    @Override
    public void accept(GameVisitor gameVisitor) {
        gameVisitor.visitWallTile(this);
    }

    @Override
    public boolean canBeOccupied() {
        return false;
    }

    @Override
    public boolean isGoalTile() {
        return false;
    }
}
