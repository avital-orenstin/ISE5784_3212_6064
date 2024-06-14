/**
 * Integration tests for ray tracing functionality.
 */
package renderer;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.*;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for ray tracing functionality.
 */
class RayIntegrationTest {

    /**
     * Sends rays through each pixel of the camera viewport and counts the intersections with the geometry.
     * @param camera The camera used for rendering.
     * @param theGeometry The geometry to intersect with.
     * @return The total number of intersections.
     */
    int sending_rays(Camera camera, Geometry theGeometry) {
        List<Point> theIntersects = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Ray ray = camera.constructRay(3, 3, i, j);
                List<Point> points = theGeometry.findIntersections(ray);
                if (points != null) {
                    theIntersects.addAll(points);
                }
            }
        }
        return theIntersects.size();
    }

    /**
     * Tests the intersection calculation with a plane.
     */
    @Test
    void testPlan() {
        Camera camera = Camera.getBuilder()
                .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setLocation(new Point(0, 0, 0))
                .setVpSize(3, 3)
                .setVpDistance(1)
                .build();
        Point point = new Point(0, 0, -1);
        Vector vector = new Vector(0, 0, 2);
        Plane plane = new Plane(point, vector);
        assertEquals(9, sending_rays(camera, plane), "There should be 9 cutoff points");
        point = new Point(0, 0, -1);
        vector = new Vector(0, 2, 4);
        plane = new Plane(point, vector);
        assertEquals(9, sending_rays(camera, plane), "There should be 9 cutoff points");
        point = new Point(0, 0, -1);
        vector = new Vector(0, 2, 2);
        plane = new Plane(point, vector);
        assertEquals(6, sending_rays(camera, plane), "There should be 6 cutoff points");
    }

    /**
     * Tests the intersection calculation with a triangle.
     */
    @Test
    void testTriangle() {
        Camera camera = Camera.getBuilder()
                .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setLocation(new Point(0, 0, 0))
                .setVpSize(3, 3)
                .setVpDistance(1)
                .build();
        Triangle triangle = new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1, -1, -2));
        assertEquals(1, sending_rays(camera, triangle),"There should be 1 cutoff point.");
        triangle = new Triangle(new Point(0, 20, -2), new Point(1, -1, -2), new Point(-1, -1, -2));
        assertEquals(2, sending_rays(camera, triangle), "There should be 2 cutoff points.");
    }

    /**
     * Tests the intersection calculation with a sphere.
     */
    @Test
    void testSphere() {
        Camera camera = Camera.getBuilder()
                .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setLocation(new Point(0, 0, 0))
                .setVpSize(3, 3)
                .setVpDistance(1)
                .build();
        Sphere sphere = new Sphere(1, new Point(0, 0, -3));
        assertEquals(2, sending_rays(camera, sphere), "There should be 2 cutoff points.");
        camera = Camera.getBuilder()
                .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setLocation(new Point(0, 0, 0.5))
                .setVpSize(3, 3)
                .setVpDistance(1)
                .build();
        sphere = new Sphere(2.5, new Point(0, 0, -2.5));
        assertEquals(18, sending_rays(camera, sphere), "The test failed - there should be 18 cutoff points.");
        sphere = new Sphere(2, new Point(0, 0, -2));
        assertEquals(10, sending_rays(camera, sphere), "The test failed - there should be 10 cutoff points.");
        camera = Camera.getBuilder()
                .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setLocation(new Point(0, 0, 0))
                .setVpSize(3, 3)
                .setVpDistance(1)
                .build();
        sphere = new Sphere(4, new Point(0, 0, -2));
        assertEquals(9, sending_rays(camera, sphere), "The test failed - there should be 9 cutoff points.");
        sphere = new Sphere(0.5, new Point(0, 0, 1));
        assertEquals(0, sending_rays(camera, sphere), "The test failed - there should be no cutoff points.");
    }
}