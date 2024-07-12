package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * The Triangle class represents a triangle in three-dimensional space.
 * A triangle is a polygon with three vertices.
 *@author Isca Fitousi and Avital Orenstin
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

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {

        // Step 1: Check for intersection with the plane containing the triangle

        List<GeoPoint> planeIntersections = plane.findGeoIntersections(ray, maxDistance);
        if (planeIntersections == null) {
            return null; // No intersection with plane - no intersection with triangle
        }
        // Get the first intersection point (may need to adjust if multiple)

        // Vectors representing the triangle's edges
        Vector v1 = vertices.get(0).subtract(ray.head);
        Vector v2 = vertices.get(1).subtract(ray.head);
        Vector v3 = vertices.get(2).subtract(ray.head);

        // Normal vectors to the triangle's edges
        Vector N1 = v1.crossProduct(v2).normalize();
        Vector N2 = v2.crossProduct(v3).normalize();
        Vector N3 = v3.crossProduct(v1).normalize();

        // Calculate dot product between ray direction and normal vectors
        double sign1 = ray.direction.dotProduct(N1);
        double sign2 = ray.direction.dotProduct(N2);
        double sign3 = ray.direction.dotProduct(N3);

        // Check if all signs are the same (point inside triangle)
        if ((sign1 > 0 && sign2 > 0 && sign3 > 0) || (sign1 < 0 && sign2 < 0 && sign3 < 0)) {
            // Intersection point is inside the triangle
            List<GeoPoint> geoPoints = new ArrayList<>();
            Point point1 = planeIntersections.getFirst().point;
            geoPoints.add(new GeoPoint(this, point1)); // Add only the first valid point
            return geoPoints;
        }

        return null; // No intersection with triangle
    }

}

