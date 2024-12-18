import java.util.HashSet;
import java.util.Set;

public class Point {
    public final int x;
    public final int y;
    public Set<Point> connected = new HashSet<>();

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Point fromString(String s) {
        String[] split = s.split(" ");
        return new Point(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }

    public double distanceTo(Point other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2.0) + Math.pow(this.y - other.y, 2.0));
    }

    @Override
    public String toString() {
        return String.format("(%s; %s)", x, y);
    }
}
