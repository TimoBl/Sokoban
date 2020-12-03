package sokoban.io.input;

import sokoban.entity.PlayerEntity;
import sokoban.game.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Queue;

/**
 * Input handler that generates actions read from standard input.
 */
public class StdinInputHandler extends InputHandler {
    private final BufferedReader reader;
    private boolean terminated;

    private Queue<Action> actions;

    public StdinInputHandler() {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void handleNextInputOn(PlayerEntity player) {
        String line;
        Action action = null;
        try {
            line = reader.readLine();
            if (line.matches("^u$")) {
                action = Action.MOVE_UP;
            } else if (line.matches("^d$")) {
                action = Action.MOVE_DOWN;
            } else if (line.matches("^l")) {
                action = Action.MOVE_LEFT;
            } else if (line.matches("^r")) {
                action = Action.MOVE_RIGHT;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (action != null) {
            act(player, action);
        }
    }

    public boolean hasTerminated() {
        return terminated;
    }
}
