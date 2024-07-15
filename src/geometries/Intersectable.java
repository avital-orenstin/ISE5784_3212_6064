package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;
import java.util.Objects;

/**
 * Interface for intersectable geometries
 */
public abstract class Intersectable {


    protected abstract List<GeoPoint>
    findGeoIntersectionsHelper(Ray ray, double maxDistance);

    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    /**
     * Represents a geometric point with an associated geometry and position.
     * This is an internal class and should not be used directly.*
     */
    /**
     * @param ray to find intersections points with the geomtry
     * @return list of the intersections points with the geomtry
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }


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

        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }




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
