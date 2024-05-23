package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * The Sphere class represents a sphere in three-dimensional space.
 * A sphere is a special type of radial geometry shape with a center point and a radius.
 * @author Isca Fitousi and Avital Orenstin.
 */
public class Sphere extends RadialGeometry {

    /**
     * The center point of the sphere.
     */
    private final Point center;

    /**
     * Constructs a sphere with the specified radius.
     *
     * @param radius -the radius of the sphere.
     * @param center -the center point of the sphere.
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }



    @Override
    public Vector getNormal(Point p) {
        Vector n = p.subtract(center);
        return n.normalize();
    }


}
