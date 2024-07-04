package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;


/**
 * The Tube class represents a tube in three-dimensional space.
 * A tube is a special type of radial geometry shape with an axis defined by a ray and a radius.
 *
 * @author Isca Fitousi and Avital Orenstin.
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
     * @param axis   The axis of the tube.
     */
    public Tube(double radius, Ray axis) {
        super(radius);
        this.axis = axis;
    }

    @Override
    public Vector getNormal(Point point) {

        Point p0 = axis.head;
        Vector v = axis.direction;
        //t = v (P – P0)
        double t = point.subtract(p0).dotProduct(v);
        // O = P0 + tv
        Point o = null;
        o = axis.getPoint(t);
        Vector n = point.subtract(o);
        return n.normalize();
    }


    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}


