package geometries;

import primitives.*;

/**
 * The Geometry class represents a geometric shape in three-dimensional space.
 * It defines a method for calculating the normal vector to a given point on the surface of the shape,
 * and provides methods for setting and getting the emission color of the geometry.
 *
 * @author Isca Fitousi and Avital Orenstin
 */
public abstract class Geometry extends Intersectable {

    /**
     * The emission color of the geometry.
     */
    protected Color emission = Color.BLACK;
    /**
     * material of a geometry object
     */
    private Material material = new Material();

    /**
     * Calculates and returns the normal vector to a given point on the surface of the geometry.
     *
     * @param point The point on the surface of the geometry for which to compute the normal vector.
     * @return The normal vector to the given point on the surface of the geometry.
     */
    public abstract Vector getNormal(Point point);

    /**
     * Gets the emission color of the geometry.
     *
     * @return The emission color.
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * Sets the emission color of the geometry.
     *
     * @param emission The new emission color.
     * @return The updated geometry object.
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }
    /**
     * builder pattern setter for material field
     *
     * @param material material value
     * @return Geometry object
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }
    /**
     * getter for the material
     *
     * @return material
     */
    public Material getMaterial() {
        return material;
    }
}
