import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        List<Point> points = new LinkedList<>();

        Scanner scanner = new Scanner(new File("input.txt"));
        String input = scanner.nextLine();
        for (String point: input.split(", ")) {
            points.add(Point.fromString(point));
        }

        for (Point a: points) {
            for (Point b: points) {
                if (a == b) {
                    continue;
                }
                if (a.x == b.x || a.y == b.y) {
                    a.connected.add(b);
                    b.connected.add(a);
                }
            }
        }

        boolean filtered = false;
        while (!filtered) {
            filtered = true;
            for (Point a: points) {
                List<Point> brothers = a.connected.stream().toList();
                checkChain: for (Point b: brothers) {
                    for (Point c: brothers) {
                        if (b == c) {
                            continue;
                        }
                        if (b.connected.contains(c)) {
                            double ab = a.distanceTo(b);
                            double ac = a.distanceTo(c);
                            Point toRemove = ab > ac ? b : c;
                            a.connected.remove(toRemove);
                            toRemove.connected.remove(a);
                            filtered = false;
                            break checkChain;
                        }
                    }
                }
            }
        }

        List<List<Point>> paths = new LinkedList<>();
        for (Point connected: points.getFirst().connected) {
            List<Point> newPath = new LinkedList<>();
            newPath.add(points.getFirst());
            newPath.add(connected);
            paths.add(newPath);
        }
        boolean pathsUnfinished = true;
        while (pathsUnfinished) {
            pathsUnfinished = false;

            for (List<Point> path: paths) {
                Point current = path.getLast();
                boolean pathsAdded = false;

                if (current.connected.size() == 2) {
                    for (Point connected: current.connected) {
                        if (!path.contains(connected)) {
                            path.add(connected);
                        }
                    }
                } else {
                    for (Point connected: current.connected) {
                        if (path.getLast() == current && !path.contains(connected)) {
                            path.add(connected);
                            continue;
                        }
                        List<Point> newPath = new LinkedList<>(path);
                        newPath.removeLast();
                        if (newPath.contains(connected)) {
                            continue;
                        }
                        newPath.add(connected);
                        paths.add(newPath);
                        pathsAdded = true;
                    }
                    if (pathsAdded) {
                        break;
                    }
                }
            }
            for (List<Point> path: paths) {
                for (Point potentialNext: path.getLast().connected) {
                    if (!path.contains(potentialNext)) {
                        pathsUnfinished = true;
                        break;
                    }
                }
            }
        }

        List<List<Point>> connectedPaths = new LinkedList<>();
        for (List<Point> path: paths) {
            if (path.getLast().connected.contains(path.getFirst())) {
                path.add(path.getFirst());
                connectedPaths.add(path);
            }
        }

        for (List<Point> path: connectedPaths) {
            System.out.println(path);
        }

        List<Point> longest = connectedPaths.getFirst();
        for (List<Point> path: connectedPaths) {
            if (path.size() > longest.size()) {
                longest = path;
            }
        }
        System.out.println();
        System.out.println(longest);
    }
}