package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Class representing a point light source.
 * A point light emits light equally in all directions from a specific point in space.
 */
public class PointLight extends Light implements LightSource{

    /**
     * The position of the light
     */
    protected  Point position;
    /**
     * Promises that the light will not be strengthened but will be weakened
     */
    private double KC=1.0;

    /**
     * reduce the attenuation factor of linear dependence
     */
    private double KL=0.0;

    /**
     * Reduce the attenuation factor of quadratic dependence
     */
    private double KQ=0.0;

    @Override
    public double getWidth_light() {
        return this.width_light;
    }

    @Override
    public double getHeight_light() {
        return this.height_light;
    }



    /**
     * Sets the dimensions for soft shadows.
     *
     * @param width_light The width of the light source.
     * @param height_light The height of the light source.
     * @return The current PointLight instance.
     */

    public PointLight setSoftShadow(double width_light, double height_light) {
        this.width_light = width_light;
        this.height_light = height_light;
        return this;
    }

    /**
     * PointLight constructor
     * @param colorIntensity
     * @param position
     */
    public PointLight(Color colorIntensity, Point position) {
        super(colorIntensity);
        this.position = position;
    }

    /**
     * Sets the position of the light source.
     *
     * @param position The new position of the light source.
     */
    public void setPosition(Point position) {
        this.position = position;
    }

    /**
     * Sets the constant attenuation factor.
     *
     * @param KC The constant attenuation factor.
     * @return The current PointLight instance.
     */
    public PointLight setKc(double KC) {
        this.KC = KC;
        return this;
    }
    /**
     * Sets the linear attenuation factor.
     *
     * @param KL The linear attenuation factor.
     * @return The current PointLight instance.
     */
    public PointLight setKl(double KL) {
        this.KL = KL;
        return this;
    }
    /**
     * Sets the quadratic attenuation factor.
     *
     * @param KQ The quadratic attenuation factor.
     * @return The current PointLight instance.
     */
    public PointLight setKq(double KQ) {
        this.KQ = KQ;
        return this;
    }

    /**
     * constructor Light
     *
     * @param intensity
     */
    protected PointLight(Color intensity) {
        super(intensity);
    }

    @Override
    public Color getIntensity(Point p) {
        double dist = position.distance(p);

        double factor = (KC + dist * KL + (dist * dist) * KQ);

        return getIntensity().reduce(factor);
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }
    @Override
    public double getDistance(Point point) {
        return position.distance(point);
    }

    @Override
    public Point getPosition() {
        return this.position;
    }
    @Override
    public boolean isSoftShadow(){
        return (this.width_light > 0) || (this.height_light > 0);
    }
}