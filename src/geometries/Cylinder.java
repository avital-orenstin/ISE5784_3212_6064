package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;

/**
 * The Cylinder class represents a cylinder in three-dimensional space.
 * A cylinder consists of a tube with a given radius and height.
 * * @author Isca Fitousi and Avital Orenstin
 */


public class Cylinder extends Tube {

    /**
     * The height of the cylinder.
     */
    private final double height;

    /**
     * Constructs a cylinder with the specified radius.
     *
     * @param radius The radius of the cylinder.
     */
    public Cylinder(Ray ray,Double radius,Double height) {
        super(radius,ray);
        this.height = height;
    }

    /**
     * Returns the normal vector to a given point on the surface of the cylinder.
     *
     * @param p The point on the surface of the cylinder for which to compute the normal vector.
     * @return The normal vector to the given point on the surface of the cylinder.
     */
    public Vector getNormal(Point p) {
        return super.getNormal(p);
    }
}