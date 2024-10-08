package renderer;

import geometries.Intersectable;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;

import static primitives.Util.isZero;

/**
 * The Camera class will use the builder design template
 * @author Isca Fitousi and Avital Orenstin.
 */

public class Camera implements Cloneable {

    /**
     * The camera location.
     */
    private Point location;
    /**
     * The v_Right represents the direction of the rig,
     * which is a point in space used as a reference point for the camera.
     */
    private Vector v_Right;
    /**
     * The v_Up represents the "up" direction for the camera.
     */
    private Vector v_Up;
    /**
     * The v_To indicates the target point the camera is pointing at.
     */
    private Vector v_To;
    /**
     * The height of the camera's virtual display screen.
     */
    private double height = 0;
    /**
     * The width of the camera's virtual display screen.
     */
    private double width = 0;
    /**
     * The distance between the camera and the virtual display screen.
     */
    private double distanceToScreen = 0;
    /**
     * The image writer for the actual picture.
     */
    private ImageWriter imageWriter;
    /**
     * The x field for the ray for the antialiasing.
     */
    int x = 1;
    /**
     *  The y field for the ray for the antialiasing.
     */
    int y = 1;
    /**
     * rayTracer gets a ray and returns all intersection points between the ray and geometries in the scene.
     */
    private RayTracerBase rayTracer;

    private static final String MISSING_RENDERING_DATA = "missing rendering data";

    private static final String CAMERA_CLASS_NAME = "Camera";

    /**
     * The field for the Adaptive improvement.
     */
    private int AdaptiveDepth = 0;

    /**
     * Default constructor.
     */
    private Camera() {

    }

    /**
     * The get functions.
     **/
    public Vector getUpDirection() {
        return v_Up;
    }

    public Vector getTargetDirection() {
        return v_To;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public double getDistanceToScreen() {
        return distanceToScreen;
    }

    // getBuilder
    public static Builder getBuilder() {
        return new Builder();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Calculates the center point of a pixel in world coordinates.
     *
     * @param nX the number of pixels in the X direction.
     * @param nY the number of pixels in the Y direction.
     * @param j  the X index of the pixel.
     * @param i  the Y index of the pixel.
     * @return the center point of the pixel in world coordinates.
     */
    private Point CalculateCenterPointInPixel(int nX, int nY, int j, int i) {
        // Calculate the center point of the screen in world coordinates
        Point pixelCenter = location.add(v_To.scale(distanceToScreen));

        // Calculate the ratio of pixel height and width
        double pixelHeightRatio = height / nY;
        double pixelWidthRatio = width / nX;

        // Calculate the y-coordinate of the pixel
        double pixelY = -(i - (nY - 1) / 2d) * pixelHeightRatio;
        // Calculate the x-coordinate of the pixel
        double pixelX = (j - (nX - 1) / 2d) * pixelWidthRatio;

        // Adjust the pixel point along the x-axis if necessary
        if (!isZero(pixelX)) {
            pixelCenter = pixelCenter.add(v_Right.scale(pixelX));
        }
        // Adjust the pixel point along the y-axis if necessary
        if (!isZero(pixelY)) {
            pixelCenter = pixelCenter.add(v_Up.scale(pixelY));
        }

        return pixelCenter; // Return the center point of the pixel
    }


    /**
     * Constructs a ray passing through the center of the specified pixel.
     *
     * @param nX The number of pixels along the x-axis.
     * @param nY The number of pixels along the y-axis.
     * @param j  The index of the pixel along the x-axis.
     * @param i  The index of the pixel along the y-axis.
     * @return The constructed ray passing through the center of the pixel.
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        // Calculate the center point of the specified pixel
        Point pCenterPixel = CalculateCenterPointInPixel(nX, nY, j, i);
        // Construct a ray from the camera location to the center of the pixel
        Vector vIJ = pCenterPixel.subtract(location);
        return new Ray(location, vIJ);
    }

    /**
     * Renders the image by casting rays through each pixel and writing the result to the image.
     *
     * @return this Camera instance
     */
    public Camera renderImage() {
        int nx = imageWriter.getNx();
        int ny = imageWriter.getNy();

        for (int i = 0; i < nx; i++)
            for (int j = 0; j < ny; j++)
                castRay(nx, ny, j, i);
        return this;
    }

    /**
     * Recursively subdivides the plan and sends rays adaptively to improve rendering performance.
     *
     * @param centerPlan    The center point of the current plan.
     * @param w             The width of the current plan.
     * @param h             The height of the current plan.
     * @param points        A list to collect points where rays are sent.
     * @param AdaptiveDepth The current depth of adaptive recursion.
     * @return              A list of points representing the adaptive subdivision.
     */

    private List<Point> Adaptive(Point centerPlan, double w, double h, List<Point> points, int AdaptiveDepth) {
        paln_board devided_pixel = new paln_board(w, h, 2, 2);
        devided_pixel.setCenter_board(centerPlan);
        List<Point> tempPoints = devided_pixel.Grid(this.v_Right, this.v_Up);
        if (AdaptiveDepth == 0) {
            return tempPoints;
        }
        Color midColor = this.rayTracer.traceRay(new Ray(this.location, centerPlan.subtract(this.location)));
        for (int i = 0; i < 4; i++) {
            if (!this.rayTracer.traceRay(new Ray(this.location, tempPoints.get(i).subtract(this.location))).equals(midColor)) {
                points.addAll(Adaptive(tempPoints.get(i), w / 2, h / 2, points, AdaptiveDepth - 1));
            }
        }
        return tempPoints;
    }

    /**
     * Casts a ray through a specific pixel and writes the color to the image.
     *
     * @param nX the number of horizontal pixels in the view plane
     * @param nY the number of vertical pixels in the view plane
     * @param j  the horizontal index of the pixel (0-based)
     * @param i  the vertical index of the pixel (0-based)
     */
    private void castRay(int nX, int nY, int j, int i) {
        Color color = Color.BLACK;
        if (this.x > 1 || this.y > 1) {
            List<Point> points = new ArrayList<>();
            if (this.AdaptiveDepth > 0) {
                points = Adaptive(CalculateCenterPointInPixel(nX, nY, j, i), this.width / nX, this.height / nY,
                        points, this.AdaptiveDepth);
            } else {
                paln_board devided_pixel = new paln_board(this.width / nX, this.height / nY, this.x, this.y);
                devided_pixel.setCenter_board(CalculateCenterPointInPixel(nX, nY, j, i));
                points = devided_pixel.Jittered(this.v_Right, this.v_Up);
            }
            for (int k = 0; k < points.size(); k++) {
                Point point = points.get(k);
                Ray ray = new Ray(this.location, point.subtract(this.location));
                Color theray = rayTracer.traceRay(ray);
                color = color.add(theray.scale(1.0 / points.size()));
            }
        } else {
            Ray ray = constructRay(nX, nY, j, i);
            color = rayTracer.traceRay(ray);
        }
        imageWriter.writePixel(j, i, color);
    }

    /**
     * Prints a grid on the image with the specified interval and color.
     *
     * @param interval the interval between grid lines
     * @param color    the color of the grid lines
     * @return this Camera instance
     * @throws MissingResourceException if the imageWriter is not set
     */
    public Camera printGrid(int interval, Color color) {
        if (imageWriter == null) {
            throw new MissingResourceException(MISSING_RENDERING_DATA, CAMERA_CLASS_NAME, "imageWriter");
        }
        int nx = imageWriter.getNx();
        int ny = imageWriter.getNy();
        for (int x = 0; x < nx; x++) {
            for (int y = 0; y < ny; y++) {
                if (x % interval == 0 || y % interval == 0)
                    imageWriter.writePixel(x, y, color);
            }
        }
        return this;
    }

    /**
     * Writes the image to a file.
     *
     * @throws MissingResourceException if the imageWriter is not set
     */
    public void writeToImage() {
        if (imageWriter == null) {
            throw new MissingResourceException(MISSING_RENDERING_DATA, CAMERA_CLASS_NAME, "imageWriter");
        }
        imageWriter.writeToImage();
    }

    /**
     * The Builder class for constructing Camera instances.
     */

    public static class Builder {

        private final Camera camera; // Private and final Camera field

        /**
         * Default constructor.
         * Creates a new Camera object with default values.
         */
        public Builder() {
            this.camera = new Camera();
        }

        /**
         * Constructor that takes an existing Camera object.
         *
         * @param camera Camera object to copy
         */
        public Builder(Camera camera) {
            try {
                this.camera = (Camera) camera.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * Sets the camera's location.
         *
         * @param location New point representing the camera's location
         * @return This Builder object
         * @throws IllegalArgumentException If the point is null
         */
        public Builder setLocation(Point location) {
            if (location == null) {
                throw new IllegalArgumentException("Camera location cannot be null");
            }
            camera.location = location;
            return this;
        }

        /**
         * Sets the camera's direction.
         *
         * @param myv_to Camera's "forward" vector
         * @param myv_up Camera's "up" vector
         * @return This Builder object
         * @throws IllegalArgumentException If "forward" vector is null, "up" vector is null, or not orthogonal to it
         */
        public Builder setDirection(Vector myv_to, Vector myv_up) {
            if (myv_to == null) {
                throw new IllegalArgumentException("Direction vector cannot be null");
            }
            if (myv_up == null) {
                throw new IllegalArgumentException("Up vector cannot be null");
            }
            if (myv_up.dotProduct(myv_to) != 0) {
                throw new IllegalArgumentException("Up vector is not orthogonal to direction vector");
            }
            camera.v_To = myv_to.normalize();
            camera.v_Up = myv_up.normalize();
            return this;
        }

        /**
         * Sets the viewport size.
         *
         * @param width  Width of the viewport
         * @param height Height of the viewport
         * @return This Builder object
         * @throws IllegalArgumentException If width or height is negative
         */
        public Builder setVpSize(double width, double height) {
            if (width < 0) {
                throw new IllegalArgumentException("Viewport width cannot be negative");
            }
            if (height < 0) {
                throw new IllegalArgumentException("Viewport height cannot be negative");
            }
            camera.width = width;
            camera.height = height;
            return this;
        }

        /**
         * Sets the viewport distance.
         *
         * @param distance Distance between camera and viewport
         * @return This Builder object
         * @throws IllegalArgumentException If the distance is negative
         */
        public Builder setVpDistance(double distance) {
            if (distance < 0) {
                throw new IllegalArgumentException("Distance between camera and viewport cannot be negative");
            }
            camera.distanceToScreen = distance;
            return this;
        }

        /**
         * Builds a new Camera object.
         *
         * @return New Camera object defined by builder values
         * @throws MissingResourceException If a crucial camera value is missing
         */
        public Camera build() {
            //------------Test with zero values----------
            String missingData = "missingData";
            if (camera.location == null) {
                throw new MissingResourceException(missingData, Camera.class.getName(), "camera location");
            }
            if (camera.v_To == null) {
                throw new MissingResourceException(missingData, Camera.class.getName(), "camera v_to");
            }
            if (camera.v_Up == null) {
                throw new MissingResourceException(missingData, Camera.class.getName(), "camera v_Up");
            }
            if (camera.height == 0.0) {
                throw new MissingResourceException(missingData, Camera.class.getName(), "camera height");
            }
            if (camera.width == 0.0) {
                throw new MissingResourceException(missingData, Camera.class.getName(), "camera width");
            }
            if (camera.distanceToScreen <= 0.0) {
                throw new MissingResourceException(missingData, Camera.class.getName(), "camera distanceToScreen");
            }
            //-----------Calculation of V_Right-----------
            camera.v_Right = camera.v_To.crossProduct(camera.v_Up).normalize();
            //---------Checks whether all vectors are perpendicular to each othe-------
            if (camera.v_Right.crossProduct(camera.v_To) == null) {
                throw new IllegalArgumentException("Vector V_To is not perpendicular to vector V_Right");
            }
            if (camera.v_To.crossProduct(camera.v_Up) == null) {
                throw new IllegalArgumentException("Vector V_Up is not perpendicular to vector v_To");
            }
            if (camera.v_Right.crossProduct(camera.v_Up) == null) {
                throw new IllegalArgumentException("Vector V_Up is not perpendicular to vector V_Right");
            }
            //-----------Checking for incorrect values-----------
            if (camera.height < 0.0) {
                throw new IllegalArgumentException("The height cannot be negative");
            }
            if (camera.width < 0.0) {
                throw new IllegalArgumentException("The width cannot be negative");
            }
            if (camera.distanceToScreen < 0.0) {
                throw new IllegalArgumentException();
            }
            //-----------A copy of an object in the camera field-----------
            try {
                return (Camera) camera.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * Sets the ImageWriter for the camera.
         *
         * @param imageWriter the ImageWriter to set
         * @return this Builder instance
         */
        public Builder setImageWriter(ImageWriter imageWriter) {
            camera.imageWriter = imageWriter;
            return this;
        }

        /**
         * Sets the RayTracer for the camera.
         *
         * @param rayTracer the RayTracer to set
         * @return this Builder instance
         */
        public Builder setRayTracer(RayTracerBase rayTracer) {
            camera.rayTracer = rayTracer;
            return this;
        }

        /**
         * Sets the AntiAliasing for the camera.
         *
         * @param x,y to set
         * @return this Builder instance
         */
        public Builder setAntiAliasing(int x, int y) {
            this.camera.x = x;
            this.camera.y = y;
            if (x < 0 || y < 0) {
                throw new IllegalArgumentException("...");
            }
            return this;
        }

        /**
         * Sets the AdaptiveDepth for the camera.
         *
         * @param AdaptiveDepth to set
         * @return this Builder instance
         */
        public Builder setAdaptive(int AdaptiveDepth) {
            if (AdaptiveDepth < 0) {
                throw new IllegalArgumentException("Adaptive depth given is Illegal.");
            }
            this.camera.AdaptiveDepth = AdaptiveDepth;
            return this;
        }


    }
}




