package primitives;

import java.util.Objects;

/**
 * The Point class represents a point in three-dimensional space.
 *
 * @author Isca Fitousi and Avital Orenstin.
 */

public class Point {

    /**
     * The zero point (0, 0, 0).
     */
    public static final Point ZERO = new Point(Double3.ZERO);

    /**
     * The coordinates of the point.
     */
    public final Double3 xyz;

    /**
     * Constructs a point with the specified coordinates.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     * @param z The z-coordinate of the point.
     */
    public Point(double x, double y, double z) {
        xyz = new Double3(x, y, z);
    }

    /**
     * Constructs a point with the specified coordinates.
     *
     * @param xyz The coordinates of the point.
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * Computes the vector from this point to the given point.
     *
     * @param point The point to subtract.
     * @return The vector from this point to the given point.
     */
    public Vector subtract(Point point) {
        return new Vector(xyz.subtract(point.xyz));
    }

    /**
     * Computes the point resulting from adding the given vector to this point.
     *
     * @param vector The vector to add.
     * @return The point resulting from adding the given vector to this point.
     */
    public Point add(Vector vector) {
        return new Point(xyz.add(vector.xyz));
    }

    /**
     * Computes the squared distance between this point and the given point.
     *
     * @param point The point to compute the distance to.
     * @return The squared distance between this point and the given point.
     */
    public double distanceSquared(Point point) {
        Point NewPoint = new Point(this.xyz.subtract(point.xyz));
        return NewPoint.xyz.d1 * NewPoint.xyz.d1 + NewPoint.xyz.d2 * NewPoint.xyz.d2 + NewPoint.xyz.d3 * NewPoint.xyz.d3;
    }

    /**
     * Calculates the distance between this point and the given point using a function distanceSquared
     * which calculates the squared distance.
     *
     * @param point the point to which the distance should be calculated.
     * @return The distance between this point and the given point.
     */
    public double distance(Point point) {
        return Math.sqrt(distanceSquared(point));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point point)) return false;
        return Objects.equals(xyz, point.xyz);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(xyz);
    }

    @Override
    public String toString() {
        return "Point{" +
                "xyz=" + xyz +
                '}';
    }
}