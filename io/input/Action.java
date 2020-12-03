package sokoban.io.input;

import sokoban.entity.PlayerEntity;

public enum Action implements PlayerMovement {
    MOVE_UP {
        @Override
        public void execute(PlayerEntity p) {
            p.moveUp();
        }
    },
    MOVE_DOWN {
        @Override
        public void execute(PlayerEntity player) {
            player.moveDown();
        }
    },
    MOVE_LEFT {
        @Override
        public void execute(PlayerEntity player) {
            player.moveLeft();
        }
    },
    MOVE_RIGHT {
        @Override
        public void execute(PlayerEntity player) {
            player.moveRight();
        }
    }
}
