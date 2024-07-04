package renderer;

import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * The `SimpleRayTracer` class implements a basic ray tracing algorithm.
 * It inherits from the abstract `RayTracerBase` class and overrides the `traceRay` method to perform ray-tracing calculations.
 */

public class SimpleRayTracer extends RayTracerBase {

    /**
     * * Constructor that initializes the `SimpleRayTracer` with a reference to the scene it operates on.
     *
     * @param scene The scene object containing the geometries and lighting information.
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * Calculate the color intensity in a point
     *
     * @param gp the point to calculate the color intensity
     * @return the color intensity in the point
     */
    private Color calcColor(GeoPoint gp) {
        return scene.ambientLight.getIntensity().add(gp.geometry.getEmission());
    }


    @Override
    public Color traceRay(Ray ray) {
        var interactions = scene.geometries.findGeoIntersections(ray);
        return interactions == null ? scene.background : calcColor(ray.findClosestGeoPoint(interactions));

    }

}