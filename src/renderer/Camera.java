
package renderer;

//import java.lang.Cloneable;

import java.util.MissingResourceException;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import primitives.*;

/**
 * The Camera class represents a camera in a 3D space.
 * It defines the camera's position, orientation, and properties of the view plane.
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
    private Vector vRight;
    /**
     * The v_Up represents the "up" direction for the camera.
     */
    private Vector vUp;
    /**
     * The v_To indicates the target point the camera is pointing at.
     */
    private Vector vTo;
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
    private double distance=0;
    /**
     * *********************************************************** display screen.
     */
    private ImageWriter imageWriter;
    /**
     * *********************************************************** display screen.
     */
    private RayTracerBase rayTracer;
    /**
     * *********************************************************** display screen.
     */
    private static final String MISSING_RENDERING_DATA = "missing rendering data";
    /**
     * *********************************************************** display screen.
     */
    private static final String CAMERA_CLASS_NAME = "Camera";

    /**
     * Default constructor for the Camera class.
     * This is a private constructor to prevent direct instantiation.
     */
    private Camera() {
    }
    /**
     * The get functions:
     /**
     * Returns the width of the view plane.
     *
     * @return the width of the view plane
     */
    public double getWidth() {
        return width;
    }

    /*
     * Returns the height of the view plane.
     *
     * @return the height of the view plane
     */

    public double getHeight() { return height;}

    /**
     * Returns the distance from the camera to the view plane.
     *
     * @return the distance from the camera to the view plane
     */
    public double getDistanceToScreen() { return distance;}


    /**
     * Creates a new Builder for constructing Camera instances.
     *
     * @return a new Builder instance
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    /**
     * Constructs a ray through a specific pixel on the view plane.
     *
     * @param nX the number of horizontal pixels in the view plane
     * @param nY the number of vertical pixels in the view plane
     * @param j  the horizontal index of the pixel (0-based)
     * @param i  the vertical index of the pixel (0-based)
     * @return the constructed ray through the specified pixel
     * @throws IllegalArgumentException if nX or nY is zero
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        // Calculate the center point of the specified pixel
        Point pCenterPixel = CalculateCenterPointInPixel(nX, nY, j, i);
        // Construct a ray from the camera location to the center of the pixel
        return new Ray(location, pCenterPixel.subtract(location));
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
        // Initialize the pixel point with the center point
        Point pIJ = location.add(vTo.scale(distance));

        // Calculate the ratio of pixel height and width
        double Ry = height / nY;
        double Rx = width / nX;

        double Yi = -1 * (i - (nY - 1) / 2.0) * Ry;
        double Xj = (j - (nX - 1) / 2.0) * Rx;

        if (!isZero(Xj)) {
            pIJ = pIJ.add(vRight.scale(Xj));
        }

        if (!isZero(Yi)) {
            pIJ = pIJ.add(vUp.scale(Yi));
        }

        return pIJ; // Return the center point of the pixel

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

    /**
     * The Builder class for constructing Camera instances.
     */
    public static class Builder {
        private final Camera camera = new Camera();

        /**
         * Sets the location of the camera.
         *
         * @param location the location of the camera
         * @return this Builder instance
         */
        public Builder setLocation(Point location) {
            camera.location = location;
            return this;
        }

        /**
         * Sets the direction of the camera.
         *
         * @param toVector the vector pointing forward
         * @param upVector the vector pointing upwards
         * @return this Builder instance
         * @throws IllegalArgumentException if the vectors are not perpendicular or not normalized
         */
        public Builder setDirection(Vector toVector, Vector upVector) {
            if (toVector.dotProduct(upVector) != 0) {
                throw new IllegalArgumentException("The vectors must be orthogonal");
            }
            camera.vTo = toVector.normalize();
            camera.vUp = upVector.normalize();
            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();
            return this;
        }

        /**
         * Sets the size of the view plane.
         *
         * @param width  the width of the view plane
         * @param height the height of the view plane
         * @return this Builder instance
         * @throws IllegalArgumentException if width or height is not positive
         */
        public Builder setVpSize(double width, double height) {
            camera.width = width;
            camera.height = height;
            return this;
        }

        /**
         * Sets the distance between the camera and the view plane.
         *
         * @param distance the distance between the camera and the view plane
         * @return this Builder instance
         * @throws IllegalArgumentException if the distance is not positive
         */
        public Builder setVpDistance(double distance) {
            if (distance <= 0) {
                throw new IllegalArgumentException("Distance must be a positive value.");
            }
            camera.distance = distance;
            return this;
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
         * Builds the Camera instance, ensuring all necessary fields are initialized.
         *
         * @return a clone of the Camera object with calculated fields
         * @throws MissingResourceException if any required field is missing
         * @throws IllegalArgumentException if any field has an invalid value
         */
        public Camera build() {
            if (camera.location == null) {
                throw new MissingResourceException(MISSING_RENDERING_DATA, CAMERA_CLASS_NAME, "p0");
            }
            if (camera.vUp == null) {
                throw new MissingResourceException(MISSING_RENDERING_DATA, CAMERA_CLASS_NAME, "vUp");
            }
            if (camera.vTo == null) {
                throw new MissingResourceException(MISSING_RENDERING_DATA, CAMERA_CLASS_NAME, "vTo");
            }
            if (camera.imageWriter == null) {
                throw new MissingResourceException(MISSING_RENDERING_DATA, CAMERA_CLASS_NAME, "imageWriter");
            }
            if (camera.rayTracer == null) {
                throw new MissingResourceException(MISSING_RENDERING_DATA, CAMERA_CLASS_NAME, "rayTracer");
            }
            if (!isZero(camera.vTo.dotProduct(camera.vUp))) {
                throw new MissingResourceException("", CAMERA_CLASS_NAME, "the vTo and vUp are not orthogonal");
            }
            if (alignZero(camera.width) == 0) {
                throw new MissingResourceException(MISSING_RENDERING_DATA, CAMERA_CLASS_NAME, "width");
            }
            if (alignZero(camera.height) == 0) {
                throw new MissingResourceException(MISSING_RENDERING_DATA, CAMERA_CLASS_NAME, "height");
            }
            if (alignZero(camera.distance) == 0) {
                throw new MissingResourceException(MISSING_RENDERING_DATA, CAMERA_CLASS_NAME, "distance");
            }
            if (alignZero(camera.width) < 0) {
                throw new IllegalArgumentException("Width must be greater than 0");
            }
            if (alignZero(camera.height) < 0) {
                throw new IllegalArgumentException("Height must be greater than 0");
            }
            if (alignZero(camera.distance) < 0) {
                throw new IllegalArgumentException("Distance must be greater than 0");
            }

            camera.vUp = camera.vUp.normalize();
            camera.vTo = camera.vTo.normalize();
            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();
            try {
                return (Camera) camera.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
    }



}




