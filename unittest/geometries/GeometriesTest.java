package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class GeometriesTest {

    @Test
    void testFindIntersections() {
        //build a list of geometries
        Geometries geometries1 = new Geometries(
                new Sphere(
                         new Point(1, 0, 0),4.0),
                new Triangle(
                        new Point(-1, 0, 0),
                        new Point(1, 0, 0),
                        new Point(0, 1, 0)
                ),
                new Plane(
                        new Point(1, 0, 0),
                        new Vector(0, 0, 1)
                )
        );


        // ============ Equivalence Partitions Tests ==============
        // TC01: Some (but not all) geometries are cut (3 points)
        List<Point> result1 = geometries1.findIntersections(
                new Ray(
                        new Point(0, 2, 5),
                        new Vector(0, 0, -1)));
        assertEquals(
                3,
                result1.size(),
                "Some (but not all) geometries are cut");


        // =============== Boundary Values Tests ==================
        // TC02: Empty list of geometries (0 points)
        List<Point> result2 = new Geometries().findIntersections(
                new Ray(
                        new Point(-1, 0, 0),
                        new Vector(3, 1, 2)));
        assertNull(
                new Geometries().findIntersections(
                        new Ray(
                                new Point(-1, 0, 0),
                                new Vector(3, 1, 2))
                ),
                "Empty list of geometries");

        // TC03: No geometry is cut (0 points)
        List<Point> result3 = geometries1.findIntersections(
                new Ray(
                        new Point(-4, 6, 2),
                        new Vector(1, 0, 0)));
        assertNull(
                result3,
                "No geometry is cut");

        // TC04: Only one geometry is cut (2 points)
        List<Point> result4 = geometries1.findIntersections(
                new Ray(
                        new Point(-4, 0, 2),
                        new Vector(1, 0, 0)));
        assertEquals(
                2,
                result4.size(),
                "Only one geometry is cut");

        // TC05: All geometries are cut (4 points)
        List<Point> result5 = geometries1.findIntersections(
                new Ray(
                        new Point(0, 1d / 2, 5),
                        new Vector(0, 0, -1)));
        assertEquals(
                4,
                result5.size(),
                "All geometries are cut");

    }
}