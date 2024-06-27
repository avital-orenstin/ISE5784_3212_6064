package renderer;
import primitives.*;
import java.util.MissingResourceException;
import java.lang.Cloneable;
import renderer.*;

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
    private double height=0;
    /**
     * The width of the camera's virtual display screen.
     */
    private double width=0;
    /**
     * The distance between the camera and the virtual display screen.
     */
    private double distanceToScreen=0;
    /**
     * *********************************************************** display screen.
     */
    private ImageWriter imageWriter;
    /**
     * *********************************************************** display screen.
     */
    private RayTracerBase rayTracer;
    /**
     *
     *********************************************************** display screen.
     */
    private static final String MISSING_RENDERING_DATA = "missing rendering data";
    /**
     * *********************************************************** display screen.
     */
    private static final String CAMERA_CLASS_NAME = "Camera";
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
     * Calculates the center point of a pixel in screen coordinates.
     * @param nX The number of pixels along the x-axis.
     * @param nY The number of pixels along the y-axis.
     * @param j The index of the pixel along the x-axis.
     * @param i The index of the pixel along the y-axis.
     * @return The center point of the specified pixel.
     */
    private Point CalculateCenterPointInPixel(int nX, int nY, int j, int i) {
        // Calculate the center point of the screen in world coordinates
        Point pC = location.add(v_To.scale(distanceToScreen));
        // Initialize the pixel point with the center point
        Point pIJ = pC;

        // Calculate the ratio of pixel height and width
        double rY = height / nY;
        double rX = width / nX;

        // Calculate the y-coordinate of the pixel
        double yI = -(i - (nY - 1) / 2d) * rY;
        // Calculate the x-coordinate of the pixel
        double xJ = (j - (nX - 1) / 2d) * rX;

        // Adjust the pixel point along the x-axis if necessary
        if (!isZero(xJ)) {
            pIJ = pIJ.add(v_Right.scale(xJ));
        }
        // Adjust the pixel point along the y-axis if necessary
        if (!isZero(yI)) {
            pIJ = pIJ.add(v_Up.scale(yI));
        }

        return pIJ; // Return the center point of the pixel
    }

    /**
     * Constructs a ray passing through the center of the specified pixel.
     * @param nX The number of pixels along the x-axis.
     * @param nY The number of pixels along the y-axis.
     * @param j The index of the pixel along the x-axis.
     * @param i The index of the pixel along the y-axis.
     * @return The constructed ray passing through the center of the pixel.
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        // Calculate the center point of the specified pixel
        Point pCenterPixel = CalculateCenterPointInPixel(nX, nY, j, i);
        // Construct a ray from the camera location to the center of the pixel
        return new Ray(location, pCenterPixel.subtract(location));
    }
    /**
     * Renders the image by casting rays through each pixel and writing the result to the image.
     *
     * @return this Camera instance
     */
    public Camera renderImage() {
        int nx = imageWriter.getNx();
        int ny = imageWriter.getNy();

        for (int i = 0; i < nx; ++i)
            for (int j = 0; j < ny; ++j)
                castRay(nx, ny, j, i);
        return this;
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
        Ray ray = constructRay(nX, nY, j, i);
        Color color = rayTracer.traceRay(ray);
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
                this.camera =(Camera)camera.clone();
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
            if (myv_up.dotProduct(myv_to)!=0) {
                throw new IllegalArgumentException("Up vector is not orthogonal to direction vector");
            }
            camera.v_To = myv_to.normalize();
            camera.v_Up = myv_up.normalize();
            return this;
        }

        /**
         * Sets the viewport size.
         *
         * @param width Width of the viewport
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
            String missingData="missingData";
            if (camera.location == null) {
                throw new MissingResourceException(missingData , Camera.class.getName(), "camera location");
            }
            if (camera.v_To == null) {
                throw new MissingResourceException(missingData, Camera.class.getName(), "camera v_to");
            }
            if (camera.v_Up== null) {
                throw new MissingResourceException(missingData, Camera.class.getName(), "camera v_Up");
            }
            if (camera.height ==0.0) {
                throw new MissingResourceException(missingData, Camera.class.getName(), "camera height");
            }
            if (camera.width ==0.0) {
                throw new MissingResourceException(missingData, Camera.class.getName(), "camera width");
            }
            if (camera.distanceToScreen <=0.0) {
                throw new MissingResourceException(missingData, Camera.class.getName(), "camera distanceToScreen");
            }
            //-----------Calculation of V_Right-----------
            camera.v_Right= camera.v_To.crossProduct(camera.v_Up).normalize();
            //---------Checks whether all vectors are perpendicular to each othe-------
            if (camera.v_Right.crossProduct(camera.v_To) == null) {
                throw new IllegalArgumentException("Vector V_To is not perpendicular to vector V_Right");
            }
            if (camera.v_To.crossProduct(camera.v_Up) == null) {
                throw new IllegalArgumentException("Vector V_Up is not perpendicular to vector v_To");
            }
            if (camera.v_Right.crossProduct(camera.v_Up)== null) {
                throw new IllegalArgumentException("Vector V_Up is not perpendicular to vector V_Right");
            }
            //-----------Checking for incorrect values-----------
            if (camera.height <0.0) {
                throw new IllegalArgumentException("The height cannot be negative");
            }
            if (camera.width <0.0) {
                throw new IllegalArgumentException("The width cannot be negative");
            }
            if (camera.distanceToScreen <0.0) {
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



    }


}




