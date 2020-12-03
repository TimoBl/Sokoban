package sokoban.game;

/**
 * Representation of coordinates and vectors in a game.
 */
public class Point {
    private int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Point) return hashCode() == o.hashCode();
        return false;
    }

    public Point add(Point delta) {
        return new Point(x + delta.getX(), y + delta.getY());
    }

    @Override
    public String toString() {
        return String.format("Point(%d, %d)", x, y);
    }
}
