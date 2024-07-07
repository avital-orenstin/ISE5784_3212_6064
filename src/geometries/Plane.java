package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * The Plane class represents a plane in three-dimensional space.
 * A plane is defined by a point on the plane and its normal vector.
 * @author Avital Orenshtein and Isca Fitousi
 */
public class Plane extends Geometry {

    /**
     * A point on the plane.
     */
    private final Point point;

    /**
     * The normal vector to the plane.
     */
    private final Vector normal;

    /**
     * Constructs a plane with the given point and normal vector.
     *
     * @param point1 A point on the plane.
     * @param normal The normal vector to the plane.
     */
    public Plane(Point point1, Vector normal) {
        this.point = point1;
        this.normal = normal.normalize();
    }

    /**
     * Constructs a plane passing through three given points.
     *
     * @param point1 The first point on the plane.
     * @param point2 The second point on the plane.
     * @param point3 The third point on the plane.
     */
    public Plane(Point point1, Point point2, Point point3) {
        Vector myVec1 = point1.subtract(point2);
        Vector myVec2 = point1.subtract(point3);
        this.point = new Point(point1.xyz);
        this.normal = myVec1.crossProduct(myVec2).normalize();
    }

    /**
     * Creates a plane passing through three given points.
     *
     * @param point1 The first point on the plane.
     * @param point2 The second point on the plane.
     * @param point3 The third point on the plane.
     * @return The plane passing through the three given points.
     */
    public static Plane createPlane(Point point1, Point point2, Point point3) {
        return new Plane(point1, point2, point3);
    }

    @Override
    public Vector getNormal(Point point) {
        return normal;
    }

    /**
     * Returns the normal vector to the plane.
     *
     * @return The normal vector.
     */

    public Vector getNormal() {
        return normal;
    }

    private boolean isPointOnPlane(Point point) {
        Vector v = point.subtract(this.point);
        double dotProduct = v.dotProduct(normal);
        return Math.abs(dotProduct) < 0.00001;
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        double t_denominator = normal.dotProduct(ray.direction);
        //if the ray is parallel to the plane - there is no intersections points
        if(isZero(t_denominator))
            return null;
        // Check if the ray's head is on the plane
        if (point.equals(ray.head)) {
            return null; // No intersection
        }

        // Calculate the normalized direction vector from the ray's head to the point representing the plane
        Vector vectorToPlane = point.subtract(ray.head);

        // Dot product between the ray's direction vector and the normalized direction vector to calculate t
        double t = alignZero(normal.dotProduct(vectorToPlane) / t_denominator);

        // If t is close to zero, the ray is parallel to the plane - no intersection
        if (isZero(t)) {
            return null;
        }

        // Calculate the distance along the ray by multiplying t by the ray's direction
        double distanceOnRay = t * ray.direction.length();

        // Check if the distance is positive (intersection happens before the ray's head)
        if (distanceOnRay <= 0) {
            return null;
        }

        // Intersection point on the ray
        Point intersectionPoint = ray.getPoint(distanceOnRay);
        List<GeoPoint> intersections = null;
        // Return a list with a GeoPoint object containing the intersection point and the plane as geometry
        intersections = List.of(new GeoPoint(this, intersectionPoint));

        return intersections;
    }


}