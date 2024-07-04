package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the  geometries.Tube class.
 * This class tests the functionality of the getNormal method of the Tube class.
 *
 * @author Isca Fitousi and Avital Orenshtein.
 */

class TubeTest {

    /**
     * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
     * This method tests the getNormal function to ensure that the normal vector returned
     * is calculated correctly for a given point on the surface of the tube.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Tube tube = new Tube(1.0, new Ray(new Point(1, 1, 1), new Vector(1, 0, 0)));
        Point point = new Point(2, 0, 1);

        // Check if the normal vector is calculated correctly.
        assertEquals(
                tube.getNormal(point),
                new Vector(0, -1, 0).normalize(),
                "ERROR: not the correct normal");
    }

}
