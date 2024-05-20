package geometries;

import primitives.*;

/**
 * The Plane class represents a plane in three-dimensional space.
 * A plane is defined by a point on the plane and its normal vector.
 * * @author Isca Fitousi and Avital Orenstin
 */
public class Plane implements Geometry {

    /**
     * A point on the plane.
     */
    final private Point q;

    /**
     * The normal vector to the plane.
     */
    final private Vector normal;

    /**
     * Constructs a plane with the given point and normal vector.
     *
     * @param q      A point on the plane.
     * @param normal The normal vector to the plane.
     */
    public Plane(Point q, Vector normal) {
        this.q = q;
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
        this.normal = null; // Note: The normal vector is not initialized here.
        this.q = point1; // Note: The point q is initialized with the first given point.
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
    public Vector getNormal(Point p) {
        return null; // Note: This method is not fully implemented and returns null.
    }

    /**
     * Returns the normal vector to the plane.
     *
     * @return The normal vector.
     */

    public Vector getNormal() {
        return null; // Note: This method is not fully implemented and returns null.
    }
}