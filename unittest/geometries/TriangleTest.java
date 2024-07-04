package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TriangleTest {

    @Test
    void testFindIntersections() {
        Triangle triangle = new Triangle(new Point(0, 0, 1), new Point(1, 0, 0), new Point(-1, 0, 0));
        // ============ Equivalence Partitions Tests ==============

        // TC01: Point inside the triangle (1 point)
        List<Point> result_1 = triangle.findIntersections(new Ray(new Point(0, 1.5, 0), new Vector(-0.15, -1.5, 0.58)));
        assertEquals(1,
                result_1.size(),
                "Wrong number of points");

        // TC02: Point outside against edge (0 points)
        List<Point> result_2 = triangle.findIntersections(new Ray(new Point(1.52, 2, 0.5), new Vector(-0.02, -1.52, 0)));
        assertNull(
                result_2,
                "Point outside against edge");


        // TC03: Point outside against vertex (0 points)
        List<Point> result_3 = triangle.findIntersections(new Ray(new Point(-1.57, -0.5, -0.47), new Vector(0, 1.58, 0)));
        assertNull(
                result_3,
                "Point outside against vertex");

        // =============== Boundary Values Tests ==================
        // **** Group: Ray begins "before" the plane

        // TC04: Point on edge (0 points)
        List<Point> result_4 = triangle.findIntersections(new Ray(new Point(-1.22, -0.86, 0), new Vector(1.72, 0.86, -0.5)));
        assertNull(
                result_4,
                "Point on edge");

        // TC05: Point in vertex (0 points)
        List<Point> result_5 = triangle.findIntersections(new Ray(new Point(-1, 1, 0), new Vector(1, -1, 1)));
        assertNull(
                result_5,
                "Point in vertex");

        // TC06: Point on edge's continuation (0 points)
        List<Point> result_6 = triangle.findIntersections(new Ray(new Point(2, -0.76, -1), new Vector(0, 2.14, 0)));
        assertNull(
                result_6,
                "Point on edge's continuation");

    }
}