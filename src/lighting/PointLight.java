package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

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

    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }




    public void setPosition(Point position) {
        this.position = position;
    }

    public PointLight setKc(double KC) {
        this.KC = KC;
        return this;
    }

    public PointLight setKl(double KL) {
        this.KL = KL;
        return this;
    }

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

    /**
     * Calculates the intensity
     * @param p The position of the object
     * @return
     */
    @Override
    public Color getIntensity(Point p) {
        double dist = position.distance(p);

//        if(dist <= 0){
//            return getIntensity();
//        }

        double factor = (KC + dist * KL + (dist * dist) * KQ);

        return getIntensity().reduce(factor);
    }

    /**
     * Returns the vector-direction between the point and the light source
     * @param p calculate the intensity in the point
     * @return
     */
    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }
    @Override
    public double getDistance(Point point) {
        return position.distance(point);
    }
}
