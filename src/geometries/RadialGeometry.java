package geometries;

import primitives.Ray;

import java.util.List;

/**
 * The RadialGeometry abstract class represents a radial geometry shape in three-dimensional space.
 * Radial geometry shapes are defined by a radius.
 * * @author Isca Fitousi and Avital Orenstin.
 */
public abstract class RadialGeometry extends Geometry {

    /**
     * The radius of the radial geometry shape.
     */
    protected final double radius;

    /**
     * Constructs a radial geometry shape with the specified radius.
     *
     * @param radius -the radius of the radial geometry shape.
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }

    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);
}