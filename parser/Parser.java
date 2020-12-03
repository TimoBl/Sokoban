package sokoban.parser;

import sokoban.game.Game;
import sokoban.io.input.InputHandler;
import sokoban.io.output.Renderer;

public interface Parser {
    Game makeGameFromText(String someText);
}