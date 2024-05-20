package geometries;

import primitives.*;

/**
 * The Geometry interface represents a geometric shape in three-dimensional space.
 * It defines a method for calculating the normal vector to a given point on the surface of the shape.
 * * @author Isca Fitousi and Avital Orenstin
 */
public interface Geometry {

    /**
     * Calculates and returns the normal vector to a given point on the surface of the geometry.
     *
     * @param p The point on the surface of the geometry for which to compute the normal vector.
     * @return The normal vector to the given point on the surface of the geometry.
     */
    public Vector getNormal(Point p);
}