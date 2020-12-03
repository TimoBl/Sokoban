package sokoban.tile;

import sokoban.tile.FloorTile;
import sokoban.tile.GoalTile;
import sokoban.tile.WallTile;

/**
 * Interface for handling all kinds of tiles.
 */
public interface TileVisitor {
    void visitFloorTile(FloorTile floorTile);
    void visitGoalTile(GoalTile goalTile);
    void visitWallTile(WallTile wallTile);

}
