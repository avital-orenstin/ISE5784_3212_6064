package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Planes.
 * Unit tests for the geometries.Plane class.
 * This class tests the functionality of the Plane class, including its constructor and getNormal method.
 *
 * @author Isca Fitousi and Avital Orenshtein.
 */
class PlaneTest {

    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in assertEquals.
     */
    private final double DELTA = 0.000001;

    /**
     * Test method for {@link geometries.Plane#Plane(primitives.Point, primitives.Point, primitives.Point)}.
     * Ensures that the constructor throws an IllegalArgumentException
     * when two identical points are provided or when all points are collinear.
     */
    @Test
    public void testConstructor() {
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(2, 4, 6);
        Point p3 = new Point(4, 8, 12);

        // =============== Boundary Values Tests =================

        // TC01: Test with two identical points
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(p1, p1, p3),
                "ERROR: There are 2 identical points.");

        // TC02: Test with all points on the same line
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(p1, p2, p3),
                "ERROR: All points are on the same line.");
    }

    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
     * Ensures that the normal vector of the plane is calculated correctly and has a length of 1.
     */
    @Test
    void testGetNormal() {

        Plane plane = new Plane(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Test if the normal vector is calculated correctly
        assertEquals(
                new Vector(1, 1, 1).normalize(),
                plane.getNormal(),
                "Normal vector is not calculated correctly"
        );

        // TC02: Test if the normal vector has a length of 1
        assertEquals(
                1,
                plane.getNormal().length(),
                DELTA,
                "Normal vector should have length 1"
        );
    }

    /**
     * Duplicate test method for {@link Plane#getNormal(Point)}.
     * This method is identical to the previous test and checks the same functionality.
     */
    @Test
    void testTestGetNormal() {
        Plane plane = new Plane(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));


        // ============ Equivalence Partitions Tests ==============

        // TC01: Test if the normal vector is calculated correctly
        assertEquals(
                new Vector(1, 1, 1).normalize(),
                plane.getNormal(),
                "Normal vector is not calculated correctly"
        );

        // TC02: Test if the normal vector has a length of 1
        assertEquals(
                1,
                plane.getNormal().length(),
                DELTA,
                "Normal vector should have length 1"
        );
    }


    @Test
    void testFindIntersections() {
        Plane plane = new Plane(new Point(0, 0, 1), new Vector(1, 1, 1));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray into plane
        assertEquals(
                List.of(new Point(1, 0, 0)),
                plane.findIntersections(new Ray(new Point(0.5, 0, 0),
                        new Vector(1, 0, 0))),
                "Bad plane intersection");

        // TC02: Ray out of plane
        assertNull(
                plane.findIntersections(new Ray(new Point(2, 0, 0),
                        new Vector(1, 0, 0))),
                "Must not be plane intersection");

        // =============== Boundary Values Tests ==================
        // TC03: Ray parallel to plane
        assertNull(
                plane.findIntersections(new Ray(new Point(1, 1, 1),
                        new Vector(0, 1, -1))),
                "Must not be plane intersection");
        // TC04:The ray is parallel from the plane above or below
        assertNull(
                plane.findIntersections(new Ray(new Point(1, 1, -1),
                        new Vector(1, 1, -1))),
                "Must not be plane intersection");
        // TC05: Orthogonal ray into plane
        assertEquals(
                List.of(new Point(1d / 3, 1d / 3, 1d / 3)),
                plane.findIntersections(new Ray(new Point(1, 1, 1),
                        new Vector(-1, -1, -1))),
                "Bad plane intersection");

        // TC06: Orthogonal ray out of plane
        assertNull(
                plane.findIntersections(new Ray(new Point(1, 1, 1),
                        new Vector(1, 1, 1))),
                "Must not be plane intersection");

        // TC07: Orthogonal ray from plane
        assertNull(
                plane.findIntersections(new Ray(new Point(0, 0.5, 0.5),
                        new Vector(1, 1, 1))),
                "Must not be plane intersection");

        // TC08: Ray from plane
        assertNull(
                plane.findIntersections(new Ray(new Point(0, 0.5, 0.5),
                        new Vector(1, 1, 0))),
                "Must not be plane intersection");
        // TC09: Ray start==start plane point
        assertNull(
                plane.findIntersections(new Ray(new Point(0, 0, 1),
                        new Vector(1, 1, 0))),
                "Must not be plane intersection");
    }
}
