package sokoban.io.input;

import sokoban.entity.PlayerEntity;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Input handler that can be provided with a list of actions that will be used
 * for the {@link ScriptedInputHandler#handleNextInputOn(PlayerEntity)}
 * invocations. Is terminated after all provided actions have been handled
 * once.
 */
public class ScriptedInputHandler extends InputHandler {

    private Queue<Action> actions;

    public ScriptedInputHandler(Action... actions) {
        this.actions = new LinkedList<>();
        this.actions.addAll(Arrays.asList(actions));
    }

    @Override
    public void handleNextInputOn(PlayerEntity player) {
        assert player != null;
        Action action = actions.remove();
        act(player, action);
    }

    public boolean hasTerminated() {
        return actions.isEmpty();
    }
}
