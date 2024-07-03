package primitives;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static primitives.Util.isZero;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
class VectorTest {

    public static final double DELTA = 0.0001;

    /** Test method for {@link primitives.Vector#Vector(double, double, double)} (constructor). */
    @Test
    public void testConstructor() {
        // =============== Boundary Values Tests ==================
        // TC01: Constructing a zero vector
        assertThrows(
                IllegalArgumentException.class,
                () -> new Vector(0, 0, 0),
                "ERROR: zero vector does not throw an exception");
    }

    /** Test method for {@link primitives.Point#add(primitives.Vector)}. */
    @Test
    void testAdd() {
        Point  p1         = new Point(1, 2, 3);
        Point  p2         = new Point(2, 4, 6);
        Vector v1         = new Vector(1, 2, 3);
        Vector v1Opposite = new Vector(-1, -2, -3);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Adding a vector to a point
        assertEquals(
                p2,
                p1.add(v1),
                "ERROR: (point + vector) = other point does not work correctly");

        // =============== Boundary Values Tests ==================
        // TC02: Adding a vector to its opposite
        assertEquals(
                Point.ZERO,
                p1.add(v1Opposite),
                "ERROR: (point + vector) = center of coordinates does not work correctly");
    }

    /** Test method for {@link primitives.Vector#scale(double)}. */
    @Test
    void testScale() {
        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Scaling a vector by a positive scalar
        assertEquals(
                new Vector(2, 4, 6),
                v1.scale(2),
                "ERROR: By multiplying by a scalar");
    }

    /** Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}. */
    @Test
    void testDotProduct() {
        Vector v1         = new Vector(1, 2, 3);
        Vector v2         = new Vector(-2, -4, -6);
        Vector v3         = new Vector(0, 3, -2);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Dot product of orthogonal vectors

        assertTrue(
                isZero(v1.dotProduct(v3)),
                "ERROR: dotProduct() for orthogonal vectors is not zero");

        assertEquals(
                -28,
                v1.dotProduct(v2),
                DELTA,
                "ERROR: dotProduct() wrong value");
    }

    /** Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}. */
    @Test
    void testCrossProduct() {
        Vector v1         = new Vector(1, 2, 3);
        Vector v2         = new Vector(-2, -4, -6);
        Vector v3         = new Vector(0, 3, -2);

        // =============== Boundary Values Tests ==================
        // TC01: Cross product of parallel vectors
        assertThrows(
                IllegalArgumentException.class,
                () -> v1.crossProduct(v2),
                "crossProduct() for parallel vectors should throw an exception");

        // ============ Equivalence Partitions Tests ==============
        // TC02: Test cross product result length
        Vector vr = v1.crossProduct(v3);
        assertEquals(
                v1.length() * v3.length(),
                vr.length(),
                DELTA,
                "ERROR: crossProduct() wrong result length");

        // TC03: Test orthogonality of cross product result
        assertTrue(
                isZero(vr.dotProduct(v1)) || isZero(vr.dotProduct(v3)),
                "ERROR: crossProduct() result is not orthogonal to its operands");
    }

    /** Test method for {@link primitives.Vector#lengthSquared()}. */
    @Test
    void testLengthSquared() {
        Vector v4 = new Vector(1, 2, 2);
        // ============ Equivalence Partitions Tests ==============
        // TC01: Length squared of a vector
        assertEquals(
                9,
                v4.lengthSquared(),
                "ERROR: lengthSquared() wrong value");
    }

    /** Test method for {@link primitives.Vector#length()}. */
    @Test
    void testLength() {
        Vector v4 = new Vector(1, 2, 2);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Length of a vector
        assertEquals(
                3,
                v4.length(),
                "ERROR: length() wrong value");
    }

    /** Test method for {@link primitives.Vector#normalize()}. */
    @Test
    void testNormalize() {
        Vector v = new Vector(1, 2, 3);
        v.normalize();

        // ============ Equivalence Partitions Tests ==============
        // TC01: Normalizing a vector.
        assertNotEquals(
                isZero(v.length() - 1),
                "ERROR: the normalized vector is not a unit vector");

    }
}



