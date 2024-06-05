package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.isZero;

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
    public List<Point> findIntersections(Ray ray) {
        Point p0;
        //if the ray starts at the center add epsilon
        if(center.equals(ray.head))
            p0 = new Point(ray.head.xyz.d1 + 0.1111111115, ray.head.xyz.d2, ray.head.xyz.d3);
        else
            p0 = new Point(ray.head.xyz);
        Ray myray = new Ray(p0,ray.direction);
        // Calculate the vector from the ray's starting point to the sphere's center
        Vector u = center.subtract(p0);

        // Calculate the projection of u on the ray direction vector v
        double tm = myray.direction.dotProduct(u);

        // Calculate d^2, the squared distance from the sphere's center to the ray
        double d = u.lengthSquared() - tm * tm;
        d=Math.sqrt(d);
        // If d^2 is greater than the radius squared, there's no intersection
        if (d >= radius) {
            return null;
        }

        // Calculate th the distance from the projection to the intersection points
        double th = Math.sqrt((radius*radius) - (d*d));

        // Calculate the distances to the intersection points
        double t1 = tm + th;
        double t2 = tm - th;

        if(t1 == t2)
            t2 = -1; //that`s for that it will not return the same point twice
         //Check if the points are valid (i.e., t > 0) and return the appropriate list
        if (t1 > 0 && t2 > 0) {
            return List.of(p0.add(myray.direction.scale(t1)), p0.add(myray.direction.scale(t2)));
        } else if (t1 > 0) {
            return List.of(myray.getPoint(t1));
        } else if (t2 > 0) {
            return List.of(p0.add(myray.direction.scale(t2)));
        } else {
          return null;
        }

    }
}
//myray.getpoint(t1)