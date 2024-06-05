package primitives;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;
import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Unit tests for primitives.Point class
 * @author Isca Fitousi and Avital Orenshtein.
 */

class PointTest {
    public static final double DELTA=0.0001;
    /** Test method for {@link primitives.Point#subtract(primitives.Point)}. */
    @Test
    void testSubtract() {
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(2, 4, 6);
        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple subtraction
        assertEquals(
                v1,
                p2.subtract(p1),
                "ERROR: (point2 - point1) does not work correctly");

        // =============== Boundary Values Tests ==================
        // TC02: Subtracting the same point
        assertThrows(IllegalArgumentException.class,
                () -> p1.subtract(p1),
                "ERROR: (point - itself) does not throw an exception");
        // TC03: Subtracting vectors
        assertEquals(
                new Vector(-1, -2, -3),
                v1.subtract(p2),
                "ERROR: Vector - Vector does not work correctly");
    }
    /** Test method for {@link primitives.Point#add(primitives.Vector)}. */

    @Test
    void testAdd() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v1Opposite = new Vector(-1, -2, -3);
        Vector v2 = new Vector(-2, -4, -6);

        // =============== Boundary Values Tests ==================
        // TC01: Adding a vector to its opposite
        assertThrows(
                IllegalArgumentException.class,
                () -> v1.add(v1Opposite),
                "ERROR: Vector + -itself does not throw an exception");
        // ============ Equivalence Partitions Tests ==============
        // TC02: Adding vectors
        assertEquals(
                v1Opposite,
                v1.add(v2),
                "ERROR: Vector + Vector does not work correctly");
    }
    /** Test method for {@link primitives.Point#distanceSquared(primitives.Point)}. */

    @Test
    void testDistanceSquared() {
        Point p1 = new Point(1, 2, 3);
        Point p3 = new Point(2, 4, 5);
        // ============ Equivalence Partitions Tests ==============
        // TC01: Distance squared to itself
        assertTrue(
                isZero(p1.distanceSquared(p1)),
                "ERROR: point squared distance to itself is not zero");

        assertTrue(
                isZero(p1.distanceSquared(p1)),
                "ERROR: point squared distance to itself is not zero");


        // TC02: Distance squared between two points
        assertEquals(
                9,
                p1.distanceSquared(p3),
                DELTA,
                "ERROR: squared distance between points is wrong");
        assertTrue(
                isZero(p3.distanceSquared(p1) - 9),
                "ERROR: squared distance between points is wrong");
    }
    /** Test method for {@link primitives.Point#distance(primitives.Point)}. */
    @Test
    void testDistance() {
        Point p1 = new Point(1, 2, 3);
        Point p3 = new Point(2, 4, 5);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Distance to itself
        assertTrue(
                isZero(p1.distance(p1)),
                "ERROR: point distance to itself is not zero");

        // TC02: Distance between two points
        assertTrue(
                isZero(p1.distance(p3) - 3),
                "ERROR: distance between points to itself is wrong");
        assertTrue(
                isZero(p3.distance(p1) - 3),
                "ERROR: distance between points to itself is wrong");
    }
}