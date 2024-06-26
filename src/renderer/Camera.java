
package renderer;
import primitives.Color;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;
import java.util.MissingResourceException;
import java.lang.Cloneable;

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
    private double height;
    /**
     * The width of the camera's virtual display screen.
     */
    private double width;
    /**
     * The distance between the camera and the virtual display screen.
     */
    private double distanceToScreen;
    /**
     * *********************************************************** display screen.
     */
    private ImageWriter imageWriter;
    /**
     * *********************************************************** display screen.
     */
    private RayTracerBase rayTracer;

    /**
     * Default constructor.
     */
    private Camera() {
        location = new Point(0,0, 0);
        v_Right = new Vector(0, 0, 1);
        v_Up = new Vector(0, 1, 0);
        v_To = new Vector(1, 0, 0);

        height = 0;
        width = 0;
        distanceToScreen = 0;
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

    public ImageWriter getImageWriter() {
        return imageWriter;
    }

    public Point getlocation() {
        return location;
    }

    public RayTracerBase getRayTracer() {
        return rayTracer;
    }

    // getBuilder
    public static Builder getBuilder() {
        return new Builder();
    }
//    public Ray constructRay(int nX, int nY, int j, int i){
//        return null;
//    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    public static class Builder {

        private final Camera camera; // Private and final Camera field

        public Builder renderImage() {
            if (camera == null || camera.imageWriter == null || camera.rayTracer == null) {
                throw new IllegalStateException("Camera, ImageWriter and RayTracer cannot be null");
            }

            // Check for zero image height and throw an exception
            if (camera.imageWriter.getNy() == 0) {
                throw new IllegalArgumentException("Image height cannot be zero");
            }

            for (int i = 0; i < camera.imageWriter.getNx(); i++) {
                for (int j = 0; j < camera.imageWriter.getNy(); j++) {
                    // Construct a ray passing through the center of the specified pixel
                    Ray ray = camera.constructRay(camera.imageWriter.getNx(), camera.imageWriter.getNy(), i, j);

                    // Trace the ray and get the color
                    Color color = camera.rayTracer.traceRay(ray);

                    // Write the color to the image buffer
                    camera.imageWriter.writePixel(i, j, color);
                }
            }
            return this;
        }

        public Builder printGrid(int interval, Color color) {
            for (int i = 0; i < camera.imageWriter.getNx(); i += interval) {
                for (int j = 0; j < camera.imageWriter.getNy(); j += interval) {
                    camera.imageWriter.writePixel(i, j, color);
                }
            }
            return this;

        }

        public Builder writeToImage() {
            camera.imageWriter.writeToImage();
            return this;
        }

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
        //***************************************************
        public Builder setImageWriter(ImageWriter myimageWriter) {
            camera.imageWriter=myimageWriter;
            return this;
        }
        //***************************************************

        public Builder setRayTracer(RayTracerBase myrayTracer) {
            camera. rayTracer=myrayTracer;
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
            String missingData="missing data for camera ";
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
            if (camera.imageWriter == null)
                throw new MissingResourceException(missingData, Camera.class.getName(),"camera imageWriter ");
            if (camera.rayTracer == null)
                throw new MissingResourceException(missingData, Camera.class.getName(),"camera rayTracer ");

            //-----------Calculation of V_Right-----------
            camera.v_Right= camera.v_To.crossProduct(camera.v_Up).normalize();
            //---------Checks whether all vectors are perpendicular to each other-------
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


    private void castRay(int resolution, int pixel) {
        if (resolution <= 0 || imageWriter.getNy() <= 0) {
            throw new IllegalArgumentException("Invalid resolution or imageWriter.getHeight(): " + resolution + ", " + imageWriter.getNy());
        }

        // Calculate normalized coordinates
        double imageHeight = imageWriter.getNy();
        int pixelX = pixel % resolution;
        int pixelY = pixel / resolution;
        double x = pixelX / ((double) resolution - 1);
        double y = 1.0 - pixelY / (imageHeight - 1);

        // Calculate point on ViewPlane based on normalized coordinates
        Point viewPlanePoint = new Point(x, y, 0);

        // Retrieve camera position (assuming getter exists)
        Point cameraPosition = getlocation();

        // Assume camera orientation or direction is known or set externally
        // For example, if camera always points in a certain direction:
        Vector cameraDirection = new Vector(0, 0, -1); // Example direction (z-axis negative)

        // Construct ray with point and direction vector
        Ray ray = constructRay(viewPlanePoint, cameraPosition, cameraDirection);

        // Trace the ray and get the color
        Color color = rayTracer.traceRay(ray);

        // Write the color to the image buffer
        imageWriter.writePixel(pixelX, pixelY, color);

    }


}




