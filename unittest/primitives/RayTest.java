package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    @Test
    void testGetPoint() {
        Ray ray = new Ray(new Point(0, 0, 0), new Vector(1, 0, 0));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Cross product of parallel vectors

        double distance = 10;
        Point expectedPoint = new Point(10, 0, 0);
        Point actualPoint = ray.getPoint(distance);
        assertEquals(
                expectedPoint,
                actualPoint,
                "the positive distance not calculated correctly."
                );


        // TC02: Cross product of parallel vectors
        distance = -5;
        expectedPoint = new Point(-5, 0, 0);
        actualPoint = ray.getPoint(distance);
        assertEquals(
                expectedPoint,
                actualPoint,
                "the negative distance not calculated correctly.");

        // =============== Boundary Values Tests ==================
        // TC03: Cross product of parallel vectors
        distance = 0;
        expectedPoint = new Point(0, 0, 0);
        actualPoint = ray.getPoint(distance);
        assertEquals(
                expectedPoint,
                actualPoint,
                "the distance should be zero.");
    }
}