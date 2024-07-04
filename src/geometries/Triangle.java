package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * The Triangle class represents a triangle in three-dimensional space.
 * A triangle is a polygon with three vertices.
 *
 * @author Isca Fitousi and Avital Orenstin
 */
public class Triangle extends Polygon {

    /**
     * Constructs a triangle with the specified vertices.
     *
     * @param vertices The vertices of the triangle.
     */
    public Triangle(Point... vertices) {
        super(vertices);
    }

    public List<Point> findIntersections(Ray ray) {
        List<Point> intsersection = plane.findIntersections(ray);
        //v1 = p1 - p0
        Vector v1 = vertices.get(0).subtract(ray.head);
        //v2 = p2 - p0
        Vector v2 = vertices.get(1).subtract(ray.head);
        //v3 = p3 - p0
        Vector v3 = vertices.get(2).subtract(ray.head);
        //N1 = normalize(v1 x v2)
        Vector N1 = v1.crossProduct(v2).normalize();
        //N1 = normalize(v2 x v3)
        Vector N2 = v2.crossProduct(v3).normalize();
        //N1 = normalize(v3 x v1)
        Vector N3 = v3.crossProduct(v1).normalize();
        //v*N1
        double sign1 = ray.direction.dotProduct(N1);
        //v*N2
        double sign2 = ray.direction.dotProduct(N2);
        //v*N3
        double sign3 = ray.direction.dotProduct(N3);
        // if one or more are 0.0 – there are no intersection.
        alignZero(sign1);
        alignZero(sign2);
        alignZero(sign3);
        //The point is inside if all v*Ni have the same sign
        if ((sign1 > 0 && sign2 > 0 && sign3 > 0) || (sign1 < 0 && sign2 < 0 && sign3 < 0)) {
            return intsersection;
        }
        //if there are no intersection.
        return null;
    }
}