package renderer;
import primitives.*;
import scene.Scene;


/**
 * The {@code RayTracerBase} class serves as an abstract base class for ray tracing in a given scene.
 * Subclasses must implement the {@code traceRay} method to define how a ray is traced in the scene.
 */
public abstract class RayTracerBase {
    /**
     * The scene in which ray tracing is performed.
     */
    protected Scene scene;

    /**
     * Constructs a {@code RayTracerBase} with the specified scene.
     *
     * @param scene the scene to be used for ray tracing
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Traces the specified ray in the scene and returns the color computed for that ray.
     * Subclasses must provide an implementation for this method.
     *
     * @param ray the ray to be traced
     * @return the color resulting from tracing the ray
     */
    public abstract Color traceRay(Ray ray);
}
