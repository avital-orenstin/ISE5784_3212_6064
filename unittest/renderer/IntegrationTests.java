package renderer;

import geometries.*;

import org.junit.jupiter.api.Test;
import primitives.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * IntegrationTests class contains unit tests for the integration between Camera and various Geometry shapes.
 */
public class IntegrationTests {
    private final Camera.Builder builder = Camera.getBuilder()
            .setLocation(Point.ZERO)
            .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
            .setVpDistance(10);
    private Camera camera = builder.setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVpSize(3, 3)
            .setVpDistance(1)
            .build();

    /**
     * Calculates the sum of intersections between rays cast from a camera and a geometric shape.
     *
     * @param camera the camera
     * @param shape the shape to find intersections with
     * @return the number of intersections
     */
    int intersectionsSum(Camera camera, Geometry shape) {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Ray ray = camera.constructRay(3, 3, i, j);
                List<Point> points = shape.findIntersections(ray);
                if (points != null) {
                    count += points.size();
                }
            }
        }
        return count;
    }

    /**
     * Test cases to verify the intersection sum with a Sphere.
     */
    @Test
    void sphereTest() {
        camera = builder.setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVpSize(3, 3)
                .setVpDistance(1)
                .build();
        Sphere sphere = new Sphere(1, new Point(0, 0, -3));
        // TC01: Sphere is in front of the view plane, 2 intersections
        assertEquals(2, intersectionsSum(camera, sphere), "Sphere is in front of the view plane");

        // TC02: Sphere covers the entire view plane, 18 intersections
        camera = builder.setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVpSize(3, 3)
                .setLocation(new Point(0, 0, 0.5))
                .setVpDistance(1)
                .build();
        sphere = new Sphere(2.5, new Point(0, 0, -2.5));
        assertEquals(18, intersectionsSum(camera, sphere), "Sphere covers the entire view plane");

        // TC03: Sphere partially covers the view plane, 10 intersections
        camera = builder.setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVpSize(3, 3)
                .setLocation(new Point(0, 0, 0.5))
                .setVpDistance(1)
                .build();
        sphere = new Sphere(2, new Point(0, 0, -2));
        assertEquals(10, intersectionsSum(camera, sphere), "Sphere partially covers the view plane");

        // TC04: View plane is inside the sphere, 9 intersections
        camera = builder.setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVpSize(3, 3)
                .setLocation(new Point(0, 0, 1))
                .setVpDistance(1)
                .build();
        sphere = new Sphere(4, Point.ZERO);
        assertEquals(9, intersectionsSum(camera, sphere), "View plane is inside the sphere");

        // TC05: Sphere is behind the camera, 0 intersections
        camera = builder.setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVpSize(3, 3)
                .setLocation(new Point(0, 0, 0.5))
                .setVpDistance(1)
                .build();
        sphere = new Sphere(0.5, new Point(0, 0, 1));
        assertEquals(0, intersectionsSum(camera, sphere), "Sphere is behind the camera");
    }

    /**
     * Test cases to verify the intersection sum with a Plane.
     */
    @Test
    void planeTest() {
        // TC01: Plane is in front of the view plane, 9 intersections
        camera = builder.setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVpSize(3, 3)
                .setVpDistance(1)
                .setLocation(Point.ZERO)
                .build();
        Plane plane = new Plane(new Point(0, 0, -2), new Vector(0, 0, 1));
        assertEquals(9, intersectionsSum(camera, plane), "Plane is in front of the view plane");

        // TC02: Plane is diagonally in front of the view plane, 9 intersections
        plane = new Plane(new Point(1, 2, -3.5),
                new Point(1, 3, -3),
                new Point(4, 2, -3.5));
        assertEquals(9, intersectionsSum(camera, plane), "Plane is diagonally in front of the view plane");

        // TC03: Plane is diagonally in front of the view plane, 6 intersections
        plane = new Plane(new Point(1, 2, -3.5),
                new Point(1, 3, -2.5),
                new Point(4, 2, -3.5));
        assertEquals(6, intersectionsSum(camera, plane), "Plane is diagonally in front of the view plane");
    }

    /**
     * Test cases to verify the intersection sum with a Triangle.
     */
    @Test
    void triangleTest() {
        // TC01: Triangle is in front of the view plane, 1 intersection
        camera = builder.setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVpSize(3, 3)
                .setVpDistance(1)
                .setLocation(Point.ZERO)
                .build();
        Triangle triangle = new Triangle(new Point(-1, -1, -2),
                new Point(1, -1, -2),
                new Point(0, 1, -2));
        assertEquals(1, intersectionsSum(camera, triangle), "Triangle is in front of the view plane");

        // TC02: Larger triangle in front of the view plane, 2 intersections
        triangle = new Triangle(new Point(-1, -1, -2),
                new Point(1, -1, -2),
                new Point(0, 20, -2));
        assertEquals(2, intersectionsSum(camera, triangle), "Larger triangle is in front of the view plane");
    }
}