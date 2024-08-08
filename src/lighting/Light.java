package lighting;

import primitives.Color;
/**
 * Abstract class representing a light source.
 * This class provides a base for different types of light sources with common properties like intensity.
 */
public abstract class Light {
    // The intensity of the light source.
    protected Color intensity;
    // The width of the light source (for area lights).
    protected double width_light;
    // The height of the light source (for area lights).
    protected double height_light;
    /**
     * Constructs a light source with a given intensity.
     *
     * @param _intensity The intensity of the light source.
     */
    public Light(Color _intensity) {
        this.intensity = _intensity;
    }
    /**
     * get Intensity
     * @return Intensity
     */
    public Color getIntensity() {
        return intensity;
    }
}