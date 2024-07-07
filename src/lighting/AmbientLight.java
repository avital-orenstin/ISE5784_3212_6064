package lighting;

import primitives.Color;
import primitives.Double3;
/**
 * The `AmbientLight` class represents an ambient light source in 3D space.
 * Ambient light provides a uniform level of illumination to all parts of a scene, regardless of their orientation or position relative to other light sources.
 */
public class AmbientLight extends Light {
    /**
     * Constructor that creates an ambient light with a given color and intensity factor.
     *
     * @param _iA The base color of the ambient light.
     * @param _kA The ambient light attenuation factor (0.0 to 1.0). Higher values result in brighter ambient light.
     *
     * @throws IllegalArgumentException if the ambient light attenuation factor is negative.
     */

    public AmbientLight(Color _iA, Double3 _kA) {
        //Ip = Ka * Ia
        super(_iA.scale(_kA));
    }

    /**
     * A constant representing an ambient light source with zero intensity (black color).
     */
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, new Double3(0.0));

}

