package lighting;
import primitives.Color;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;
/**
 * Interface representing a light source.
 * Provides methods to get light properties and calculate light effects at a given point.
 */
public interface LightSource {
    /**
     * Returns light intensity at given point
     * @param p the point
     * @return Color object
     */
    public Color getIntensity(Point p);
    /**
     * Returns the direction of the light at given point
     * @param p the point
     * @return Vector object
     */
    public Vector getL(Point p);
    /**
     * Returns the distance between a light source and a point
     * @param point the point
     * @return distance in double
     */
    public double getDistance(Point point);
    /**
     * Returns the position of the light source.
     *
     * @return The position of the light source as a Point object.
     */
    public Point getPosition(); // return the position of the light
    /**
     * Returns the width of the light source.
     *
     * @return The width of the light source.
     */
    public double getWidth_light();
    /**
     * Returns the height of the light source.
     *
     * @return The height of the light source.
     */
    public double getHeight_light();
    /**
     * Indicates whether the light source supports soft shadows.
     *
     * @return true if the light source supports soft shadows, false otherwise.
     */
    boolean isSoftShadow(); // Add this method to the interface
}