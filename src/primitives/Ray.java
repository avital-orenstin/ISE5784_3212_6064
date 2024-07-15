package primitives;

import geometries.Intersectable.GeoPoint;

import java.util.List;
import java.util.Objects;

import static primitives.Util.isZero;


/**
 * The Ray class represents a ray in three-dimensional space.
 * It consists of a starting point (head) and a direction vector.
 * A vector in the direction is always normalized for future calculations.
 */
public class Ray {
    private static final double DELTA = 0.1;

    /**
     * The starting point of the ray.
     */
    public final Point head;

    /**
     * The direction vector of the ray, always normalized.
     */
    public final Vector direction;

    /**
     * Constructor for ray with offset
     *
     * @param point     original point laying on the surface of the geometry
     * @param direction normal vector from the geometry
     */
    public Ray(Point point, Vector direction, Vector n) {
        // Compute the offset vector based on the orientation of the normal
        double nl = direction.dotProduct(n);
        Vector offset = n.scale(nl > 0 ? DELTA : -DELTA);
        this.head = point.add(offset);
        this.direction = direction.normalize();

    }

    /**
     * Constructs a Ray with the specified head (starting point) and direction.
     * A vector in the direction is always normalized for future calculations.
     *
     * @param head       The starting point of the ray.
     * @param direction The direction vector of the ray.
     */
    public Ray(Point head, Vector direction) {
        this.head = head;
        this.direction = direction.normalize();
    }



    /**
     * The get functions.
     */
    public Point getHead() {
        return head;
    }

    public Vector getDirection() {
        return direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ray ray)) return false;
        return Objects.equals(head, ray.head) && Objects.equals(direction, ray.direction);
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

    /**
     * Returns a point on the ray at a given distance `t` from the head.
     *
     * @param t The distance from the head along the ray direction.
     * @return The point on the ray at distance `t`.
     */
    public Point getPoint(double t) {
        if (isZero(t))
            return head;
        return head.add(direction.scale(t));
    }

    /**
     * Finds the closest point to the ray's head among a list of points.
     *
     * @param points The list of points to consider.
     * @return The closest point to the ray's head in the list, or null if the list is empty.
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points
                .stream()
                .map(p -> new GeoPoint(null, p))
                .toList()).point;
    }


    /**
     * Finds the closest intersection point (GeoPoint) of the ray with a list of intersectable objects.
     *
     * @param intersections The list of intersectable objects to check for intersection.
     * @return The closest intersection point (GeoPoint) with any object in the list, or null if there are no intersections.
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> intersections) {
        if (intersections == null) {
            return null;
        }

        GeoPoint closest_point = null;
        double closestDistance = Double.MAX_VALUE;

        for (GeoPoint geoPoint : intersections) {
            // Access the point and distance information directly from the GeoPoint
            double distance = head.distance(geoPoint.point);
            if (distance < closestDistance) {
                closestDistance = distance;
                closest_point = geoPoint;
            }
        }

        return closest_point;
    }


}



