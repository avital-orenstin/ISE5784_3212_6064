package geometries;
import primitives.*;
import java.util.List;
import java.util.Objects;

/**
 * Interface for intersectable geometries
 */
public abstract class Intersectable {
    /**
     * Find intersections of a ray with the geometry
     * @param ray the ray to intersect with
     * @return list of intersection points or null if no intersections found
     */
    public abstract List<Point> findIntersections(Ray ray);
    /**
     * Represents a geometric point with an associated geometry and position.
     * This is an internal class and should not be used directly.*
     */
    public static class GeoPoint {

        /**
         * The associated geometry of the point.
         */
        public Geometry geometry;

        /**
         * The position of the point in 3D space.
         */
        public Point point;

        /**
         * Constructs a new GeoPoint with the specified geometry and position.
         * This constructor is not intended for public use.**
         *
         * @param geometry The geometry associated with the point.
         * @param point The position of the point in 3D space.
         */

        GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        /**
         * Finds all intersection points between the geometry and the given ray.
         *
         * @param ray The ray to check for intersections with.
         * @return A list of intersection points, or an empty list if there are no intersections.
         */
        public List<GeoPoint> findGeoIntersections(Ray ray) {
            return findGeoIntersectionsHelper(ray);
        }

        /**
         * A helper method for finding intersection points between the geometry and the given ray.
         * This method is implemented by subclasses.
         *
         * @param ray The ray to check for intersections with.
         * @return A list of intersection points, or an empty list if there are no intersections.
         */
        protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray);


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return geometry.equals(geoPoint.geometry) && point.equals(geoPoint.point);
        }

        @Override
        public int hashCode() {
            return Objects.hash(geometry, point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }

}
