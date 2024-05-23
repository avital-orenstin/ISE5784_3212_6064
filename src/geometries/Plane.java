package geometries;

import primitives.*;

import java.util.List;

/**
 * The Plane class represents a plane in three-dimensional space.
 * A plane is defined by a point on the plane and its normal vector.
 * @author Avital Orenshtein and Isca Fitousi
 */
public class Plane implements Geometry {

    /**
     * A point on the plane.
     */
    private final Point q;

    /**
     * The normal vector to the plane.
     */
    private final Vector normal;

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
        Vector myVec1 = point1.subtract(point2);
        Vector myVec2 = point1.subtract(point3);
        this.q = new Point(point1.xyz);
        this.normal =myVec1.crossProduct(myVec2).normalize();
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


}