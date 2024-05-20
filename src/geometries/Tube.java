package geometries;

import primitives.*;
import static primitives.Util.isZero;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;


/**
 * The Tube class represents a tube in three-dimensional space.
 * A tube is a special type of radial geometry shape with an axis defined by a ray and a radius.
 * * @author Isca Fitousi and Avital Orenstin
 */
public class Tube extends RadialGeometry {

    /**
     * The axis of the tube defined by a ray.
     */
    protected final Ray axis;

    /**
     * Constructs a tube with the specified radius.
     *
     * @param radius The radius of the tube.
     * @param axis The axis of the tube.
     */
    public Tube(double radius, Ray axis) {
        super(radius);
        this.axis = axis;
    }

    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}