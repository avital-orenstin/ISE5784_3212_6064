package geometries;

import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * The `Geometries` class represents a collection of geometric objects in 3D space.
 * It inherits from the `Intersectable` interface and implements its methods to perform ray-tracing intersection calculations on the entire collection of geometries.
 */

public class Geometries extends Intersectable{
    /**
     * A list of `Intersectable` objects representing the individual geometric shapes in the collection.
     */
    List<Intersectable> geometries=new LinkedList<Intersectable>();

    /**
     * Default constructor that creates an empty collection of geometries.
     */

    public Geometries() {
    }
    /**
     * Constructor that initializes the collection of geometries with the provided intersectable objects.
     *
     * @param geometries An array of `Intersectable` objects representing the initial set of geometries in the collection.
     */

    public Geometries(Intersectable ... geometries) {
        add(geometries);

    }
    /**
     * Adds one or more `Intersectable` objects to the collection of geometries.
     *
     * @param geometries An array of `Intersectable` objects to be added to the collection.
     */
    public void add(Intersectable ... geometries)
    {
        Collections.addAll(this.geometries,geometries);
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> allIntersections = new LinkedList<>();
        for (Intersectable geometry : geometries) {
            List<GeoPoint> geometryIntersections = geometry.findGeoIntersectionsHelper(ray);
            if (geometryIntersections != null) {
                allIntersections.addAll(geometryIntersections);
            }
        }
        return allIntersections.isEmpty() ? null : allIntersections;
    }
}

