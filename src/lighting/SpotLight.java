package lighting;

import primitives.*;

/**
 * Class represents a light from a point with direction
 * Extends class PointLight
 */
public class SpotLight extends PointLight {
    // Field represents the direction of the light
    private final Vector direction;

    /**
     * Constructor
     *
     * @param intensity parameter for field intensity in super
     * @param direction parameter for field direction
     */
    public SpotLight(Color intensity, Vector direction) {
        super(intensity, direction);
        this.direction = direction.normalize();
    }

    /**
     * SpotLight constructor
     * @param Intensity
     * @param position
     * @param direction
     */

    public SpotLight(Color Intensity, Point position, Vector direction) {
        super(Intensity, position);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        double proj = direction.dotProduct(getL(p)); //direction*(psition-p) , projection of light on point
        //if the light source doesn't hit the point return color black
        if (Util.isZero(proj))
            return Color.BLACK;

        double factor = Math.max(0, proj);
        Color i0 = super.getIntensity(p);

        // i0*(max(0,direction*(position-p))/(kC+d*kL+ds*kQ)
        return i0.scale(factor);
    }

    @Override
    public Vector getL(Point p) {
        if (p.equals(position)) {
            return null;
        }
        return p.subtract(position).normalize();
    }
}