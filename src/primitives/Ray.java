package primitives;

import java.util.List;
import java.util.Objects;

import static primitives.Util.isZero;

/**
 * The Ray class represents a ray in three-dimensional space.
 * It consists of a starting point (head) and a direction vector.
 * A vector in the direction is always normalized for future calculations.
 */
public class Ray {

    /**
     * The starting point of the ray.
     */
    public final Point head;

    /**
     * The direction vector of the ray, always normalized.
     */
    public final Vector direction;

    /**
     * Constructs a Ray with the specified head (starting point) and direction.
     * A vector in the direction is always normalized for future calculations.
     *
     * @param head      The starting point of the ray.
     * @param direction The direction vector of the ray.
     */
    public Ray(Point head, Vector direction) {
        this.head = head;
        this.direction = direction.normalize();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ray ray)) return false;
        return Objects.equals(head, ray.head) && Objects.equals(direction, ray.direction);
    }

    public Point getPoint(double t) {
        if (isZero(t))
            return head;
        return head.add(direction.scale(t));

    }

    @Override
    public int hashCode() {
        return Objects.hash(head, direction);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "head=" + head +
                ", direction=" + direction +
                '}';
    }

    public Point findClosestPoint(List<Point> points) {

        double minDistance = Double.MAX_VALUE;
        double d;
        Point closePoint = null;

        if (points == null) {
            return null;
        }

        for (Point p : points) {

            d = p.distance(head);
            //check if the distance of p is smaller then minDistance
            if (d < minDistance) {
                minDistance = d;
                closePoint = p;
            }
        }
        return closePoint;
    }
}