package sokoban.parser;

import sokoban.entity.BreakableWallEntity;
import sokoban.entity.ExplosiveEntity;
import sokoban.game.Game;
import sokoban.game.Point;
import sokoban.io.input.InputHandler;
import sokoban.io.output.Renderer;
import sokoban.tile.FloorTile;
import sokoban.tile.WallTile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses level files according to the description and creates a {@link Game}
 * instance.
 */
public class PlaintextParser implements Parser {
    public Game makeGameFromText(String someText) {
        StringReader stringReader = new StringReader(someText);
        BufferedReader reader = new BufferedReader(stringReader);

        try {
            String line;
            line = reader.readLine();
            Pattern pattern = Pattern.compile("(\\d+) (\\d+)");
            Matcher matcher = pattern.matcher(line);
            int width = 0, height = 0;
            if (matcher.matches()) {
                width = Integer.parseInt(matcher.group(1));
                height = Integer.parseInt(matcher.group(2));
            }

            Game.GameBuilder gameBuilder = new Game.GameBuilder(width, height);

            int x, y = 0;
            while ((line = reader.readLine()) != null) {
                y++;
                x = 0;
                for (Character c : line.toCharArray()) {
                    x++;
                    Point p = new Point(x, y);
                    switch (c) {
                        case '#':
                            gameBuilder.setTileTo(p, new WallTile(p));
                            break;
                        case ' ':
                            gameBuilder.setTileTo(p, new FloorTile(p));
                            break;
                        case 'P':
                            gameBuilder.setPlayerTo(p);
                            break;
                        case 'G':
                            gameBuilder.setGoalTile(p);
                            break;
                        case 'B':
                            gameBuilder.addBoxEntity(p);
                            break;
                        // extended rules
                        case 'O':
                            gameBuilder.setEntityTo(p, new ExplosiveEntity());
                            break;
                        case 'X':
                            gameBuilder.setEntityTo(p, new BreakableWallEntity());
                            break;
                        default:
                            throw new AssertionError("invalid character: " + c);
                    }
                }
            }
            return gameBuilder.build();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
