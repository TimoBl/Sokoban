package sokoban.entity;

import sokoban.entity.BoxEntity;
import sokoban.entity.BreakableWallEntity;
import sokoban.entity.ExplosiveEntity;
import sokoban.entity.PlayerEntity;

public interface EntityVisitor {
    void visitBoxEntity(BoxEntity boxEntity);
    void visitPlayerEntity(PlayerEntity playerEntity);
    void visitExplosiveEntity(ExplosiveEntity explosiveEntity);
    void visitBreakableWallEntity(BreakableWallEntity breakableWallEntity);
}
