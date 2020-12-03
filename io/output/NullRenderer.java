package sokoban.io.output;

import sokoban.entity.BoxEntity;
import sokoban.entity.BreakableWallEntity;
import sokoban.entity.ExplosiveEntity;
import sokoban.entity.PlayerEntity;
import sokoban.game.Game;
import sokoban.tile.FloorTile;
import sokoban.tile.GoalTile;
import sokoban.tile.WallTile;

/**
 * Trivial "null" renderer that does not do anything.
 */
public class NullRenderer implements Renderer {
    @Override
    public void render(Game game) { /* do nothing */ }

    @Override
    public void renderSuccess(Game game) {

    }

    @Override
    public void renderFailure(Game game) {
    }

    @Override
    public void visitBoxEntity(BoxEntity boxEntity) {

    }

    @Override
    public void visitFloorTile(FloorTile floorTile) {

    }

    @Override
    public void visitGame(Game game) {

    }

    @Override
    public void visitGoalTile(GoalTile goalTile) {

    }

    @Override
    public void visitPlayerEntity(PlayerEntity playerEntity) {

    }

    @Override
    public void visitExplosiveEntity(ExplosiveEntity explosiveEntity) {

    }

    @Override
    public void visitBreakableWallEntity(BreakableWallEntity breakableWallEntity) {

    }

    @Override
    public void visitWallTile(WallTile wallTile) {

    }
}
