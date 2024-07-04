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
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }



    @Override
    public Vector getNormal(Point p) {
        Vector n = p.subtract(center);
        return n.normalize();
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> intersections = null;

        // if the ray starts at the center add epsilon
        if (center.equals(ray.head)) {
            intersections = List.of(new GeoPoint(this, ray.getPoint(radius)));
            return intersections;
        }

        // Calculate the vector from the ray's starting point to the sphere's center
        Vector u = center.subtract(ray.head);

        // Calculate the projection of u on the ray direction vector v
        double tm = ray.direction.dotProduct(u);

        // Calculate d^2, the squared distance from the sphere's center to the ray
        double d = alignZero(u.lengthSquared() - tm * tm);
        d = alignZero(Math.sqrt(d));

        // If d^2 is greater than the radius squared, there's no intersection
        if (d >= radius * radius) {
            return null;
        }

        // Calculate th the distance from the projection to the intersection points
        double th = Math.sqrt((radius * radius) - (d * d));

        // Calculate the distances to the intersection points
        double t1 = tm + th;
        double t2 = tm - th;

        // Check for negative distances (no intersection behind the ray)
        if (alignZero(t2) <= 0 && alignZero(t1) <= 0) {
            return null;
        }

        // If there's only one intersection point
        if (alignZero(t2) <= 0) {
            intersections = List.of(new GeoPoint(this, ray.getPoint(t1)));
        } else {
            intersections = List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
        }

        return intersections;
    }


}
