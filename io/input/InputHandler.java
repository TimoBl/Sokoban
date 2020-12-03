package sokoban.io.input;

import sokoban.entity.PlayerEntity;

/**
 * Input handlers are responsible for generating actions (i.e., movements in
 * any direction in {@link Action}) and instructing the player to execute the
 * command.
 */
public abstract class InputHandler {

    /**
     * Obtain next input and, if the input is valid, execute it (see {@link InputHandler#act}).
     * @param player player that executes the input if possible, must not be null
     */
    public abstract void handleNextInputOn(PlayerEntity player);

    /**
     * Returns whether further inputs can be obtained.
     */
    public abstract boolean hasTerminated();

    /**
     * Execute the given {@link Action} with the given {@link PlayerEntity}.
     * This is basically a direct mapping from actions to
     * movement methods in {@link PlayerEntity}.
     * @param player player that executes the movement, must not be null
     * @param action action that is mapped to player movements, must not be null
     */
    public void act(PlayerEntity player, Action action) {
        assert player != null;
        assert action != null;
        action.execute(player);
    }
}
