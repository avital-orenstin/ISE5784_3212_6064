package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

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
    public Sphere(Point center,double radius) {
        super(radius);
        this.center = center;
    }



    @Override
    public Vector getNormal(Point p) {
        Vector n = p.subtract(center);
        return n.normalize();
    }


    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Point p0 = ray.getHead();
        Vector v = ray.getDirection();
        Vector cTOs;
        try {
            //vector from camera to center of sphere
            cTOs = center.subtract(p0);
            //if p0 == _center it is illegal
        } catch (IllegalArgumentException e) {
            return List.of(new GeoPoint(this, (ray.getPoint(radius))));
        }
        double tm = alignZero(v.dotProduct(cTOs));
        double dSquared = (tm == 0) ? cTOs.lengthSquared() : cTOs.lengthSquared() - tm * tm;
        double thSquared = alignZero(radius * radius - dSquared);

        if (thSquared <= 0) return null;

        double th = alignZero(Math.sqrt(thSquared));
        if (th == 0) return null;

        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);
        Point P1 = ray.getPoint(t1);
        Point P2 = ray.getPoint(t2);

        // ray constructed outside sphere
        // two intersection points
        if (t1 > 0 && t2 > 0) {

            return List.of(new GeoPoint(this, P1), new GeoPoint(this, P2));
        }
        // ray constructed inside sphere and intersect in back direction
        if (t1 > 0 && alignZero(t1 - maxDistance) < 0) {
            return List.of(new GeoPoint(this, P1));
        }
        // ray constructed inside sphere and intersect in forward direction
        if (t2 > 0 && alignZero(t2 - maxDistance) < 0) {

            return List.of(new GeoPoint(this, P2));
        }
        // no intersection points found - assurance return
        // code should not be reaching this point
        return null;
    }

}
