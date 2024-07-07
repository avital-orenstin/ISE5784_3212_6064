package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource{
    /**
     * Direction of the light
     */
    private  Vector direction;

    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    /**
     * constructor Light
     *
     * @param intensity
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction=direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity();
    }

    @Override
    public Vector getL(Point p) {
        return direction.normalize();
    }

    @Override
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY;
    }
}
