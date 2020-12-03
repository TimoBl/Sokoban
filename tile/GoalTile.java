package sokoban.tile;

import sokoban.entity.BoxEntity;
import sokoban.entity.Entity;
import sokoban.game.Game;
import sokoban.game.GameVisitor;
import sokoban.game.Point;

/**
 * Tile representing a "box destination". In order to solve a puzzle, all
 * boxes must be pushed onto goal tiles.
 */
public class GoalTile extends Tile {

    public GoalTile(Point position) {
        super(position);
    }

    @Override
    public void accept(GameVisitor gameVisitor) {
        gameVisitor.visitGoalTile(this);
    }

    @Override
    public boolean canBeOccupied() {
        return true;
    }

    @Override
    public boolean isGoalTile() {
        return true;
    }

    public boolean isOccupiedByBox() {
        return entity != null && entity instanceof BoxEntity;
    }
}
