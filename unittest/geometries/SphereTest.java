package geometries;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import primitives.Point;
import primitives.Vector;

/**
 * Unit tests for the geometries.Sphere class.
 * @author Isca Fitousi and Avital Orenshtein.
 */

class SphereTest {

    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in assertEquals.
     */

    private final double DELTA = 0.000001;

    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
     * This method tests the getNormal function to ensure that the normal vector returned
     * is calculated correctly and is a unit vector.
     */

    @Test
    void testGetNormal() {
        Sphere sphere = new Sphere(1, new Point(2, 3, 4));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Check if the normal vector is calculated correctly
        assertEquals(new Vector(1, 1, 1).normalize(),
                sphere.getNormal(new Point(3, 4, 5)),
                "Normal vector is not calculated correctly");

        // TC02: Check if the length of the normal vector is 1
        assertEquals(
                1,
                sphere.getNormal(new Point(3, 4, 5)).length(),
                DELTA,
                "Normal vector should have length 1");
    }


}
