package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * Interface for intersectable geometries
 */
public interface Intersectable {
    /**
     * Find intersections of a ray with the geometry
     *
     * @param ray the ray to intersect with
     * @return list of intersection points or null if no intersections found
     */
    List<Point> findIntersections(Ray ray);
}
