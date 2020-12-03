package sokoban.game;

import sokoban.entity.BoxEntity;
import sokoban.entity.Entity;
import sokoban.entity.PlayerEntity;
import sokoban.io.input.InputHandler;
import sokoban.io.input.StdinInputHandler;
import sokoban.io.output.PlaintextRenderer;
import sokoban.io.output.Renderer;
import sokoban.parser.PlaintextParser;
import sokoban.tile.FloorTile;
import sokoban.tile.GoalTile;
import sokoban.tile.Tile;
import sokoban.tile.WallTile;

import java.util.*;

/**
 * Represents a Sokoban game.
 *
 * A game consists of a board made out of tiles and entities that move on the
 * board. See {@link Entity} and {@link Tile} for more details.
 *
 * The goal of the game is to push all boxes onto goal tiles. The game knows
 * about the concrete types {@link BoxEntity}, {@link PlayerEntity}, and
 * {@link GoalTile}, since they are essential to the game. However, any
 * additional types of tiles and entities are unknown to the game and the
 * behaviour is defined in these types.
 *
 * A game is responsible for maintaining the tiles and entities in a game. An
 * entity can request movement through the {@link
 * Game#attemptMoveEntity(Entity, Point)} method.
 */
public class Game {
    private final int width, height;
    private final Tile[][] tiles;
    private final Map<Point, Entity> entities;
    private final Renderer renderer;
    private final InputHandler inputHandler;
    private PlayerEntity activePlayer;
    private Set<GoalTile> goalTiles;
    private Set<BoxEntity> boxes;


    /**
     * Instantiate with data provided by the given {@link GameBuilder}.
     *
     * @param builder a builder, must not be null
     */
    private Game(GameBuilder builder) {
        assert builder != null;

        this.width = builder.width;
        this.height = builder.height;
        this.tiles = builder.tiles;
        this.entities = builder.entities;
        this.renderer = builder.renderer;
        this.goalTiles = builder.goalTiles;
        this.boxes = builder.boxes;
        this.inputHandler = builder.inputHandler;

        if (builder.playerPosition != null) {
            setPlayerTo(builder.playerPosition);
        }

        for (Entity e : entities.values()) {
            e.setGame(this);
        }

        assert invariant();
    }

    private boolean invariant() {
        return isValidGame();
    }

    /**
     * Return whether the game is valid, which is the case when
     * there is a player and at least one goal tile and at least
     * as many goal tiles as boxes.
     */
    private boolean isValidGame() {
        return
                activePlayer != null
                && goalTiles.size() > 0
                && boxes.size() <= goalTiles.size();
    }

    /**
     * Run the game, which consists of reading input as long as
     * the game is not over and the input handler has not terminated.
     * Prints a success or failure message at the end.
     */
    public void run() {
        assert isValidGame();

        while (!isOver() && !inputHandler.hasTerminated()) {
            renderer.render(this);
            processNextInput();
        }

        if (isOver()) {
            renderer.renderSuccess(this);
        } else {
            renderer.renderFailure(this);
        }

        assert invariant();
    }

    /**
     * Handle next input action.
     */
    private void processNextInput() {
        inputHandler.handleNextInputOn(activePlayer);
    }

    /**
     * Check whether the given point is valid.
     * @param point point to check, must not be null
     */
    private void assertValidPosition(Point point) {
        assert point != null &&
                point.getX() >= 1 && point.getX() <= width &&
                point.getY() >= 1 && point.getY() <= height;
    }

    /**
     * Look up tile at given point (which must not be null and
     * a valid position).
     */
    public Tile tileAt(Point point) {
        assert point != null;
        assertValidPosition(point);

        return tiles[point.getX()][point.getY()];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * Returns whether there is an entity at the
     * given position. Parameter must not be null and a valid position.
     */
    public boolean hasEntityAt(Point point) {
        assert point != null;
        assertValidPosition(point);

        return entities.containsKey(point);
    }


    /**
     * Look up entity.
     * @param point a valid position where an entity exists
     * @return the entity at the given position
     */
    public Entity entityAt(Point point) {
        assert point != null;
        assertValidPosition(point);
        assert entities.containsKey(point);

        return entities.get(point);
    }

    /**
     * Move the entity by the given vector.
     *
     * After execution, the entity will be position at the new destination.
     *
     * @param entity entity to move, must not be null and be on the board
     * @param delta vector by which the entity is moved, must not be null
     */
    private void moveEntity(Entity entity, Point delta) {
        assert entity != null;
        assert entities.containsValue(entity);
        assert delta != null;

        Point oldPosition = positionOf(entity);
        Point destination = oldPosition.add(delta);

        tileAt(oldPosition).leave(entity);
        tileAt(destination).enter(entity);

        entities.remove(oldPosition);
        entities.put(destination, entity);

        // Postconditions
        assert !hasEntityAt(oldPosition);
        assert hasEntityAt(destination);

        assert invariant();
    }

    /**
     * Try to move the given entity by the vector delta. This method uses
     * the collision mechanism to execute the actual movements. Depending
     * on whether the involved entities have moved, the actual move (the one
     * that is attempted initially) is executed.
     *
     * Example: A player moves towards a box. Since player and box collide,
     * the corresponding collision logic is executed. In case there is an empty
     * space in front of the box, the box is moved (again using the attemptMoveEntity
     * method) and when returning to the initial call, the space in front
     * of the player is now empty, allowing him to move there.
     *
     * @param entity entity that should be moved, must not be null and on board
     * @param delta distance by which the entity should be moved, must not be null
     */
    public void attemptMoveEntity(Entity entity, Point delta) {
        assert entity != null && delta != null;
        assert entities.containsValue(entity);

        Point oldPosition = positionOf(entity);
        Point destination = oldPosition.add(delta);

        // Collide with destination if there is an entity.
        if (hasEntityAt(destination)) {
            entityAt(destination).collideWith(entity, delta);
        }

        // Check whether the destination is now free. If so, execute the move.
        if (!hasEntityAt(destination) &&
                tileAt(destination).canBeOccupied() &&
                entities.containsValue(entity)) {
            moveEntity(entity, delta);
        }

        assert invariant();
    }

    /**
     * Look up the position of the given entity.
     * @param entity entity, must not be null and be on board
     */
    private Point positionOf(Entity entity) {
        assert entity != null;
        assert entities.containsValue(entity);

        for (Point p : entities.keySet()) {
            if (entities.get(p) == entity) {
                return p;
            }
        }
        assert false : "should not be reached";
        return null;
    }

    /**
     * Return whether the game is over, i.e., has been solved.
     *
     * A game is solved when all boxes are on a goal tile.
     */
    public boolean isOver() {
        boolean result = true;
        for (BoxEntity box : boxes) {
            result = result && tileAt(positionOf(box)).isGoalTile();
        }
        return result;
    }

    public PlayerEntity getPlayer() {
        return activePlayer;
    }

    public void render() {
        renderer.render(this);
    }

    /**
     * Create a new player at the given point.
     * @param point position where the player should be placed, must be valid
     */
    private void setPlayerTo(Point point) {
        assert point != null;
        assertValidPosition(point);
        PlayerEntity player = new PlayerEntity(this);
        this.activePlayer = player;
        setEntityTo(point, player);
    }

    /**
     * Place given entity at the specified point.
     */
    private void setEntityTo(Point point, Entity entity) {
        assertValidPosition(point);
        assert !entities.containsValue(entity);

        if (hasEntityAt(point)) {
            tileAt(point).leave(entityAt(point));
        }

        entities.put(point, entity);
        tileAt(point).enter(entity);

        assert entityAt(point) == entity;
    }

    /**
     * Remove entity from board. After removing the entity, the
     * game must still be valid; it is the responsibility of the
     * caller to ensure that this is satisfied.
     * @param entity entity to be removed, must not be null and on the board
     */
    public void removeEntity(Entity entity) {
        assert entity != null && entities.containsValue(entity);

        for (Map.Entry<Point, Entity> entry : entities.entrySet()) {
            if (entry.getValue() == entity) {
                entities.remove(entry.getKey());
                tileAt(entry.getKey()).leave(entry.getValue());

                assert invariant();
                return;
            }
        }
    }

    /**
     * Helper class for creating {@link Game} instances.
     */
    public static class GameBuilder {
        // mandatory fields
        private final int width, height;
        private final Renderer renderer;
        private final InputHandler inputHandler;
        private Point playerPosition; // can be set later, but must be done before calling create()

        // optional; initialize to empty containers
        private final Set<GoalTile> goalTiles = new HashSet<>();
        private final Set<BoxEntity> boxes = new HashSet<>();
        private final Tile[][] tiles;
        private final Map<Point, Entity> entities = new HashMap<>();


        public GameBuilder(int width, int height) {
            this.width = width;
            this.height = height;
            this.renderer = SokobanObjectProvider.instance().getRenderer();
            this.inputHandler = SokobanObjectProvider.instance().getInputHandler();
            this.tiles = new Tile[width+1][height+1];
            for (int x=1; x <= width; x++) {
                for (int y=1; y <= height; y++) {
                    tiles[x][y] = new FloorTile(new Point(x, y));
                }
            }
        }

        /**
         * Add given entity at given position.
         * @param point must be valid
         * @param entity must not be null
         */
        public void setEntityTo(Point point, Entity entity) {
            assertValidPosition(point);
            assert !entities.containsValue(entity);

            entities.put(point, entity);
            tileAt(point).enter(entity);

            assert entityAt(point) == entity;
        }

        /**
         * Look up tile.
         * @param point position, must be valid
         */
        public Tile tileAt(Point point) {
            assertValidPosition(point);

            return tiles[point.getX()][point.getY()];
        }

        /**
         * Before calling this method, all mandatory fields must have
         * been set, in particular {@link GameBuilder#playerPosition} which
         * is not set in the constructor.
         * @return a new game
         */
        public Game build() {
            assert playerPosition != null;
            return new Game(this);
        }


        private void assertValidPosition(Point point) {
            assert point != null &&
                    point.getX() >= 1 && point.getX() <= width &&
                    point.getY() >= 1 && point.getY() <= height;
        }

        /**
         * Look up entity at given position.
         * @param point position, must be valid
         */
        public Entity entityAt(Point point) {
            assertValidPosition(point);
            assert entities.containsKey(point);
            return entities.get(point);
        }

        /**
         * Create a new box at the given position.
         * @param p position, must be valid
         */
        public void addBoxEntity(Point p) {
            assertValidPosition(p);

            BoxEntity boxEntity = new BoxEntity();
            this.setEntityTo(p, boxEntity);
            this.boxes.add(boxEntity);
        }

        /**
         * Replace tile at given position with a goal tile.
         * @param p position, must be valid
         */
        public void setGoalTile(Point p) {
            assertValidPosition(p);
            GoalTile tile = new GoalTile(p);
            this.goalTiles.add(tile);
            setTileTo(p, tile);
        }

        /**
         * Replace tile at given position with given new tile.
         * @param point position, must be valid
         * @param tile new tile, must not be null
         */
        public void setTileTo(Point point, Tile tile) {
            assertValidPosition(point);
            assert tile != null;
            tiles[point.getX()][point.getY()] = tile;
            assert tileAt(point) == tile;
        }

        /**
         * Set the player position to the given point.
         * @param point new player position, must be valid
         */
        public void setPlayerTo(Point point) {
            assertValidPosition(point);
            this.playerPosition = point;
        }
    }
}
