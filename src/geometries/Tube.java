package geometries;

import primitives.*;
import static primitives.Util.isZero;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;

import java.util.List;


/**
 * The Tube class represents a tube in three-dimensional space.
 * A tube is a special type of radial geometry shape with an axis defined by a ray and a radius.
 *@author Isca Fitousi and Avital Orenstin.
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

        Point p0 = axis.head;
        Vector v = axis.direction;
        //t = v (P – P0)
        double t = p.subtract(p0).dotProduct(v);
        // O = P0 + tv
        Point o=null;
        if (!isZero(t)) // if it's close to 0, we'll get ZERO vector exception
            o = p0.add(v.scale(t));
        Vector n = p.subtract(o);
        return n.normalize();
    }


}


