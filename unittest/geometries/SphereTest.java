package geometries;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import primitives.*;

import java.util.Arrays;
import java.util.List;

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
        Sphere sphere = new Sphere(new Point(2, 3, 4),1);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Check if the normal vector is calculated correctly
        assertEquals(
                new Vector(1, 1, 1).normalize(),
                sphere.getNormal(new Point(3, 4, 5)),
                "Normal vector is not calculated correctly");

        // TC02: Check if the length of the normal vector is 1
        assertEquals(
                1,
                sphere.getNormal(new Point(3, 4, 5)).length(),
                DELTA,
                "Normal vector should have length 1");
    }


    @Test
    void testFindIntersections() {
        Sphere sphere = new Sphere(new Point(1, 0, 0),1d);

        final Point gp1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        final Point gp2 = new Point(1.53484692283495, 0.844948974278318, 0);
        final var exp = List.of(gp1, gp2);
        final Vector v310 = new Vector(3, 1, 0);
        final Vector v110 = new Vector(1, 1, 0);
        final Point p01 = new Point(-1, 0, 0);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        Ray ray = new Ray(new Point(4, 0, 0), new Vector(1, 0, 0));
        List<Point> result = sphere.findIntersections(ray);
        assertNull(
                sphere.findIntersections(ray),
                "Ray's line out of sphere");
//
//        // TC02: Ray starts before and crosses the sphere (2 points)
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Point> result_2 = sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(3, 1, 0)));
        assertEquals(
                2,
                result_2.size(),
                "Wrong number of points");
        if (result_2.get(0).xyz.d1> result_2.get(1).xyz.d2)
            result_2 = Arrays.asList(result_2.get(1), result_2.get(0));
        assertEquals(
                Arrays.asList(p1, p2),
                Arrays.asList(result_2.get(0), result_2.get(1)),
                "Ray crosses sphere");


        // TC03: Ray starts inside the sphere (1 point)
        Point p3 = new Point(1.8867496997597595, 0.4622498999199199, 0);
        List<Point> result_3 = sphere.findIntersections(new Ray(new Point(1d/2, 0, 0), new Vector(3, 1, 0)));
        assertEquals(
                1,
                result_3.size(),
                "Wrong number of points");
        assertEquals(
                p3,
                result_3.get(0),
                "Ray inside sphere");

        // TC04: Ray starts after the sphere (0 points)
       List<Point> result_4 = sphere.findIntersections(
               new Ray(new Point(2.5, 0, 0),
                       new Vector(3, 1, 0)));
        assertNull(
                result_4,
                "Ray after sphere");

       // =============== Boundary Values Tests ==================
      // **** Group: Ray's line crosses the sphere (but not through the center)

       // TC05: Ray starts at sphere and goes inside (1 point)
        Point p4 = new Point(1.7999999999999998, 0.6, 0);
        List<Point> result_5 = sphere.findIntersections(
                new Ray(new Point(0, 0, 0),
                        new Vector(3, 1, 0)));
        assertEquals(
                1,
                result_5.size(),
                "Wrong number of points");
        assertEquals(
                p4,
                result_5.getFirst(),
                "Ray starts at sphere and goes inside");

        ///TC06: Ray starts at sphere and goes outside (0 points)
        List<Point> result_6 = sphere.findIntersections(
                new Ray(new Point(0, 0, 0),
                        new Vector(-3, 1, 0)));
        assertNull(
                result_6,
                "Ray starts at sphere and goes outside");

         //**** Group: Ray's line goes through the center
        // TC07: Ray starts before the sphere (2 points)
        Point p5 = new Point(0, 0, 0);
        Point p6 = new Point(2, 0, 0);
        List<Point> result_7 = sphere.findIntersections(
                new Ray(new Point(-2, 0, 0),
                        new Vector(3, 0, 0)));
        assertEquals(
                2,
                result_7.size(),
                "Wrong number of points");
        if (result_7.get(0).xyz.d1 > result_7.get(1).xyz.d3)
            result_7 = Arrays.asList(result_7.get(1), result_7.get(0));
        assertEquals(
                Arrays.asList(p5, p6),
                Arrays.asList(result_7.get(0), result_7.get(1)),
                "Ray starts before sphere");


        // TC08: Ray starts at sphere and goes inside (1 point)
        Point p7 = new Point(2, 0, 0);
        List<Point> result_8 = sphere.findIntersections(
                new Ray(new Point(0, 0, 0),
                        new Vector(3, 0, 0)));
        assertEquals(
                1,
                result_8.size(),
                "Wrong number of points");
        assertEquals(
                p7,
                result_8.getFirst(),
                "Ray starts at sphere and goes inside");

        // TC09: Ray starts inside the sphere (1 point)
        Point p8 = new Point(2, 0, 0);
        List<Point> result_9 = sphere.findIntersections(
                new Ray(new Point(1, 0, 0),
                        new Vector(3, 0, 0)));
        assertEquals(
                1,
                result_9.size(),
                "Wrong number of points");
        assertEquals(
                p8,
                result_9.getFirst(),
                "Ray starts inside");

         //TC10: Ray starts at the center (1 point)
        Point p9 = new Point(2, 0, 0);
        List<Point> result_10 = sphere.findIntersections(
                new Ray(new Point(1, 0, 0),
                        new Vector(3, 0, 0)));
        assertEquals(
                1,
                result_10.size(),
                "Wrong number of points");
        assertEquals(
                p9,
                result_10.getFirst(),
                "Ray starts at the center");

         //TC11: Ray starts at sphere and goes outside (0 points)
        List<Point> result_11 = sphere.findIntersections(
                new Ray(new Point(2, 0, 0),
                        new Vector(3, 0, 0)));
        assertNull(
                result_11,
                "Ray starts at sphere and goes outside");

        // TC12: Ray starts after the sphere (0 points)
        List<Point> result_12 = sphere.findIntersections(
                new Ray(new Point(3, 0, 0),
                        new Vector(7, 0, 0)));
        assertNull(
                result_12,
                "Ray starts after sphere");

        // **** Group: Ray's line is tangent to the sphere

        // TC13: Ray starts before the tangent point(0 points)
        Point p12 = new Point(0, 0, 0);
        List<Point> result_13 = sphere.findIntersections(
                new Ray(new Point(0, -4, 0),
                        new Vector(0, 3, 0)));
        assertNull(
                result_13,
                "Wrong number of points");

        // TC14: Ray starts at the tangent point(0 points)
        List<Point> result_14 = sphere.findIntersections(
                new Ray(new Point(0, 0, 0),
                        new Vector(0, 3, 0)));
        assertNull(
                result_14,
                "Ray starts at the tangent point");

        // TC15: Ray starts after the tangent point(0 points)
        List<Point> result_15 = sphere.findIntersections(
                new Ray(new Point(0, 1, 0),
                        new Vector(0, 3, 0)));
        assertNull(
                result_15,
                "Ray starts after the tangent point");

        // **** Group: Ray is perpendicular to the radius line from the center

        // TC16: Ray starts inside the sphere and is perpendicular to the radius line (1 point)
        List<Point> result_16 = sphere.findIntersections(
                new Ray(new Point(0.5, 0, 0),
                        new Vector(-0.01, 0, 0.86)));
        assertEquals(
                1,
                result_16.size(),
                "Ray's line is outside, ray is orthogonal to ray start to sphere's center line");

        // TC17: Ray's line is outside, ray is orthogonal to ray start to sphere's center line(0 points)
        List<Point> result_17 = sphere.findIntersections(
                new Ray(new Point(-1, 0, 0),
                        new Vector(0, 3, 0)));
        assertNull(
                result_17,
                "Ray's line is outside, ray is orthogonal to ray start to sphere's center line");




    }
}
