package primitives;

/**
 * The Vector class represents a vector in three-dimensional space.
 * A vector is a directed line segment with magnitude and direction.
 *
 * @author Isca Fitousi and Avital Orenstin.
 */
public class Vector extends Point {

    /**
     * Constructs a vector with the specified coordinates.
     * Throws an IllegalArgumentException if the vector is the zero vector.
     *
     * @param x The x-coordinate of the vector.
     * @param y The y-coordinate of the vector.
     * @param z The z-coordinate of the vector.
     * @throws IllegalArgumentException If the vector is the zero vector.
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (this.xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("This is the zero vector!");
        }
    }

    /**
     * Constructs a vector with the specified coordinates.
     * Throws an IllegalArgumentException if the vector is the zero vector.
     *
     * @param xyz The coordinates of the vector.
     * @throws IllegalArgumentException If the vector is the zero vector.
     */
    public Vector(Double3 xyz) {
        super(xyz);
        if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("This is the zero vector!");
        }
    }

    /**
     * Computes the sum of this vector and the given vector.
     *
     * @param vector The vector to add.
     * @return The sum of this vector and the given vector.
     */
    public Vector add(Vector vector) {
        return new Vector(xyz.add(vector.xyz));
    }

    /**
     * Scales this vector by the given scalar value.
     *
     * @param scalar The scalar value to scale by.
     * @return The scaled vector.
     */
    public Vector scale(double scalar) {
        return new Vector(xyz.scale(scalar));
    }

    /**
     * Computes the dot product of this vector and the given vector.
     *
     * @param vector The vector to compute the dot product with.
     * @return The dot product of this vector and the given vector.
     */
    public double dotProduct(Vector vector) {
        return this.xyz.d1 * vector.xyz.d1 + this.xyz.d2 * vector.xyz.d2 + this.xyz.d3 * vector.xyz.d3;
    }

    /**
     * Computes the cross product of this vector and the given vector.
     *
     * @param vector The vector to compute the cross product with.
     * @return The cross product of this vector and the given vector.
     */
    public Vector crossProduct(Vector vector) {
        return new Vector(
                this.xyz.d2 * vector.xyz.d3 - this.xyz.d3 * vector.xyz.d2,
                this.xyz.d3 * vector.xyz.d1 - this.xyz.d1 * vector.xyz.d3,
                this.xyz.d1 * vector.xyz.d2 - this.xyz.d2 * vector.xyz.d1
        );
    }

    /**
     * Computes the squared length of this vector.
     *
     * @return The squared length of this vector.
     */
    public double lengthSquared() {
        return this.xyz.d1 * this.xyz.d1 + this.xyz.d2 * this.xyz.d2 + this.xyz.d3 * this.xyz.d3;
    }

    /**
     * Computes the length of this vector with the help of the method lengthSquared.
     *
     * @return The length of this vector.
     */
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    /**
     * Returns a new vector in the same direction as this vector but with unit length.
     *
     * @return The normalized vector.
     */
    public Vector normalize() {
        return new Vector(this.xyz.reduce(this.length()));
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public String toString() {
        return "Vector{" +
                "xyz=" + xyz +
                '}';
    }
}