package scene;

import geometries.Geometries;
import lighting.*;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;



/**
 * The Scene class represents a 3D scene containing geometries, lighting, and background settings.
 */
public class Scene {
    public String name;
    public Color background=Color.BLACK;
    public AmbientLight ambientLight = AmbientLight.NONE;
    public Geometries geometries = new Geometries();
    public List<LightSource>lights= new LinkedList<>();
    /**
     * Constructs a Scene with the given name.
     *
     * @param name the name of the scene
     */
    public Scene(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public Color getBackground() {
        return background;
    }

    public AmbientLight getAmbientLight() {
        return ambientLight;
    }
    public Geometries getGeometries() {
        return geometries;
    }
    public List<LightSource> getLights() {
        return lights;
    }

    /**
     * Sets the background color of the scene.
     *
     * @param background the background color
     */
    public void setBackground(Color background) {
        this.background = background;
    }

    /**
     * Sets the ambient light of the scene.
     *
     * @param ambientLight the ambient light
     * @return the current Scene instance for method chaining
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Sets the geometries of the scene.
     *
     * @param geometries the geometries
     * @return the current Scene instance for method chaining
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }
    /**
     * Builds the scene. Since all configuration happens during object creation with
     * setter-like methods, we simply return 'this' here.
     *
     * @return the current Scene instance
     */
    public Scene build() {
        return this;
    }
}
