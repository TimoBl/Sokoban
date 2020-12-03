package sokoban.io.output;

import sokoban.entity.BoxEntity;
import sokoban.entity.BreakableWallEntity;
import sokoban.entity.ExplosiveEntity;
import sokoban.entity.PlayerEntity;
import sokoban.game.Game;
import sokoban.game.Point;
import sokoban.tile.FloorTile;
import sokoban.tile.GoalTile;
import sokoban.tile.WallTile;

/**
 * The plaintext renderer prints the game state to standard output and formats
 * it using ASCII graphics similar to level input files.
 */
public class PlaintextRenderer implements Renderer {
    StringBuilder out;

    @Override
    public void render(Game game) {
        out = new StringBuilder();
        visitGame(game);
        System.out.println(out.toString());
    }

    @Override
    public void renderSuccess(Game game) {
        render(game);
        System.out.println("Success!");
    }

    @Override
    public void renderFailure(Game game) {
        render(game);
        System.out.println("Failure!");
    }

    @Override
    public void visitBoxEntity(BoxEntity boxEntity) {
        out.append('B');
    }

    @Override
    public void visitFloorTile(FloorTile floorTile) {
        out.append('.');
    }

    @Override
    public void visitGame(Game game) {
        if (out == null) out = new StringBuilder();

        for (int y=1; y <= game.getHeight(); y++) {
            for (int x=1; x <= game.getWidth(); x++) {
                Point p = new Point(x, y);
                if (game.hasEntityAt(p)) {
                    game.entityAt(p).accept(this);
                } else {
                    game.tileAt(p).accept(this);
                }
            }
            visitNewRow();
        }
    }

    private void visitNewRow() {
        out.append('\n');
    }

    @Override
    public void visitGoalTile(GoalTile goalTile) {
        out.append('G');
    }

    @Override
    public void visitPlayerEntity(PlayerEntity playerEntity) {
        out.append('P');
    }

    @Override
    public void visitExplosiveEntity(ExplosiveEntity explosiveEntity) {
        out.append('O');
    }

    @Override
    public void visitBreakableWallEntity(BreakableWallEntity breakableWallEntity) {
        out.append('X');
    }

    @Override
    public void visitWallTile(WallTile wallTile) {
        out.append('#');
    }
}
