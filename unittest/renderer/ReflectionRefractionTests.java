/**
 *
 */
package renderer;

import static java.awt.Color.*;

import lighting.DirectionalLight;
import lighting.PointLight;
import org.junit.jupiter.api.Test;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import renderer.*;
import scene.Scene;

/** Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 * @author dzilb */
public class ReflectionRefractionTests {
    /**
     * Scene for the tests
     */
    private final Scene scene = new Scene("Test scene");
    /**
     * Camera builder for the tests with triangles
     */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setRayTracer(new SimpleRayTracer(scene));

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheres() {
        scene.geometries.add(
                new Sphere(
                        new Point(0, 0, -50), 50d).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setkT(0.3)),
                new Sphere(
                        new Point(0, 0, -50), 25d).setEmission(new Color(RED))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
        scene.lights.add(
                new SpotLight(
                        new Color(1000, 600, 0),
                        new Point(-100, -100, 500),
                        new Vector(-1, -1, -2))
                        .setKl(0.0004).setKq(0.0000006));

        cameraBuilder.setLocation(
                        new Point(0, 0, 1000)).setVpDistance(1000)
                .setVpSize(150, 150)
                .setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheresOnMirrors() {
        scene.geometries.add(
                new Sphere(
                        new Point(-950, -900, -1000), 400d).setEmission(new Color(0, 50, 100))
                        .setMaterial(
                                new Material().setKd(0.25).setKs(0.25).setShininess(20)
                                        .setkT(new Double3(0.5, 0, 0))),
                new Sphere(
                        new Point(-950, -900, -1000),
                        200d).setEmission(new Color(100, 50, 20))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                new Triangle(
                        new Point(1500, -1500, -1500),
                        new Point(-1500, 1500, -1500),
                        new Point(670, 670, 3000))
                        .setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setkR(1)),
                new Triangle(
                        new Point(1500, -1500, -1500),
                        new Point(-1500, 1500, -1500),
                        new Point(-1500, -1500, -2000))
                        .setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setkR(new Double3(0.5, 0, 0.4))));
        scene.setAmbientLight(
                new AmbientLight(
                        new Color(255, 255, 255),
                        new Double3(0.1, 0.1, 0.1)));
        scene.lights.add(
                new SpotLight(
                        new Color(1020, 400, 400),
                        new Point(-750, -750, -150),
                        new Vector(-1, -1, -4))
                        .setKl(0.00001).setKq(0.000005));

        cameraBuilder.setLocation(new Point(0, 0, 10000)).setVpDistance(10000)
                .setVpSize(2500, 2500)
                .setImageWriter(new ImageWriter("reflectionTwoSpheresMirrored", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
    }

    /**
     * Produce a picture of two triangles lighted by a spot light with a
     * partially
     * transparent Sphere producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() {
        scene.geometries.add(
                new Triangle(
                        new Point(-150, -150, -115),
                        new Point(150, -150, -135),
                        new Point(75, 75, -150))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                new Triangle(
                        new Point(-150, -150, -115),
                        new Point(-70, 70, -140), new Point(75, 75, -150))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                new Sphere(
                        new Point(60, 50, -50), 30d).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setkT(0.6)));
        scene.setAmbientLight(
                new AmbientLight(new Color(WHITE), new Double3(0.15, 0.15, 0.15)));
        scene.lights.add(
                new SpotLight(
                        new Color(700, 400, 400),
                        new Point(60, 50, 0), new
                        Vector(0, 0, -1))
                        .setKl(4E-5).setKq(2E-7));

        cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("refractionShadow", 600, 600))
                .build()
                .renderImage()
                .writeToImage();
    }

    @Test
    public void PR07Image() {
        scene.geometries.add(
                new Triangle(
                        new Point(-150, -150, -200),
                        new Point(150, -150, -200),
                        new Point(150, 150, -200))
                        .setEmission(new Color(30, 15, 45))  // Purple for the background
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                new Triangle(
                        new Point(-150, -150, -200),
                        new Point(150, 150, -200),
                        new Point(-150, 150, -200))
                        .setEmission(new Color(30, 15, 45))  //Purple for the background
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                new Triangle(
                        new Point(-155, -155, -200),
                        new Point(155, -155, -200),
                        new Point(155, 155, -200))
                        .setEmission(new Color(40, 20, 60))  // border color
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                new Triangle(
                        new Point(-155, -155, -200),
                        new Point(155, 155, -200),
                        new Point(-155, 155, -200))
                        .setEmission(new Color(40, 20, 60))  // border color
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                new Sphere(
                        new Point(-50, 0, -100), 30d)
                        .setEmission(new Color(100, 50, 180))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setkT(0.3)),
                new Sphere(
                        new Point(50, 0, -100), 50d)
                        .setEmission(new Color(51, 102, 255))
                        .setMaterial(new Material().setKd(0.1).setKs(0.1).setShininess(10).setkT(0.3)));

        scene.setAmbientLight(
                new AmbientLight(
                        new Color(255, 255, 255),
                        new Double3(0.2, 0.2, 0.2)));

        scene.lights.add(
                new SpotLight(
                        new Color(800, 400, 400),
                        new Point(100, 100, 50),
                        new Vector(-1, -1, -2))
                        .setKl(1E-5).setKq(5E-8));

        cameraBuilder.setLocation(
                        new Point(0, 0, 1000)).setVpDistance(1000)
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("PR07Image", 600, 600))
                .build()
                .renderImage()
                .writeToImage();
    }

    @Test
    public void createHouseScene() {
        // Adding shapes to create the house and trees
        scene.geometries.add(
                // House base
                new Triangle(
                        new Point(-70, -100, -150),
                        new Point(70, -100, -150),
                        new Point(70, -50, -150))
                        .setEmission(new Color(255, 253, 208))  // Cream color base
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                new Triangle(
                        new Point(-70, -100, -150),
                        new Point(70, -50, -150),
                        new Point(-70, -50, -150))
                        .setEmission(new Color(255, 253, 208))  // Cream color base
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),

                // House roof
                new Triangle(
                        new Point(-80, -50, -140),
                        new Point(80, -50, -140),
                        new Point(0, 0, -140))
                        .setEmission(new Color(255, 0, 0))  // Red roof
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),

                // Chimney on the left side of the roof
                new Triangle(
                        new Point(-30, -40, -150),
                        new Point(-20, -40, -150),
                        new Point(-20, -10, -150))
                        .setEmission(new Color(255, 0, 0))  // Red chimney
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                new Triangle(
                        new Point(-30, -40, -150),
                        new Point(-20, -10, -150),
                        new Point(-30, -10, -150))
                        .setEmission(new Color(255, 0, 0))  // Red chimney
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                // Smoke spheres above the roof
                new Sphere(new Point(-25, 0, -110), 10)
                        .setEmission(new Color(30, 30, 30))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setkT(0.4)),  // Set transparency to 0.6

                new Sphere(new Point(-15, 8, -110), 12)
                        .setEmission(new Color(30, 30, 30))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setkT(0.4)),  // Set transparency to 0.6

                new Sphere(new Point(-30, -8, -110), 8)
                        .setEmission(new Color(30, 30, 30))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setkT(0.4)), // Set transparency to 0.6

                // Door
                new Triangle(new Point(-20, -100, -140),
                        new Point(20, -100, -140),
                        new Point(20, -70, -140))
                        .setEmission(new Color(101, 67, 33))  // Wood color door
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                new Triangle(new Point(-20, -100, -140),
                        new Point(20, -70, -140),
                        new Point(-20, -70, -140))
                        .setEmission(new Color(101, 67, 33))  // Wood color door
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),

                // Tree 1 trunk
                new Triangle(new Point(-50, -100, -130),
                        new Point(-40, -100, -130),
                        new Point(-40, -80, -130))
                        .setEmission(new Color(101, 67, 33))  // Wood color trunk
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                new Triangle(new Point(-50, -100, -130),
                        new Point(-40, -80, -130),
                        new Point(-50, -80, -130))
                        .setEmission(new Color(101, 67, 33))  // Wood color trunk
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),

                // Tree 1 leaves
                new Sphere(
                        new Point(-45, -60, -130), 20)
                        .setEmission(new Color(34, 139, 34))  // Green leaves
                        .setMaterial(new Material().setKd(0.3).setKs(0.3).setShininess(30)),

                // Tree 2 trunk
                new Triangle(
                        new Point(50, -100, -130),
                        new Point(60, -100, -130),
                        new Point(60, -80, -130))
                        .setEmission(new Color(101, 67, 33))  // Wood color trunk
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                new Triangle(
                        new Point(50, -100, -130),
                        new Point(60, -80, -130),
                        new Point(50, -80, -130))
                        .setEmission(new Color(101, 67, 33))  // Wood color trunk
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),

                // Tree 2 leaves
                new Sphere(
                        new Point(55, -60, -130), 20)
                        .setEmission(new Color(34, 139, 34))  // Green leaves
                        .setMaterial(new Material().setKd(0.3).setKs(0.3).setShininess(30)),

                // Grass - extends to image edges and bottom
                new Triangle(
                        new Point(-320, -95, -120),
                        new Point(320, -95, -120),
                        new Point(320, -200, -120))
                        .setEmission(new Color(34, 139, 34))  // Green grass
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),

                new Triangle(
                        new Point(-200, -100, -120),
                        new Point(200, -200, -120),
                        new Point(-200, -200, -120))
                        .setEmission(new Color(34, 139, 34))  // Green grass
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),

                // sun
                new Sphere(new Point(-120, 120, -200), 35)
                        .setEmission(new Color(255, 223, 0))  // Yellow sun
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60))


        );

        // Background color
        scene.setBackground(new Color(135, 206, 250));  // Sky blue background

        // Ambient light settings
        scene.setAmbientLight(
                new AmbientLight(
                        new Color(50, 100, 70),
                        new Double3(0.1, 0.1, 0.1))  // Reduced ambient light for a lighter background
        );

        // Adding a directional light as sunlight
        scene.lights.add(
                new DirectionalLight(
                        new Color(255, 255, 240),  // Sunlight color (ivory)
                        new Vector(0.5, -0.5, -0.5))  // Direction of sunlight
        );

        scene.lights.add(
                new SpotLight(
                        new Color(255, 255, 255),   // Spotlight color (white)
                        new Point(-120, 110, -200), // Spotlight position
                        new Vector(0, -1, -1))      // Direction of the spotlight
        );

        // Setting up the camera
        cameraBuilder.setLocation(new Point(0, 0, 500))
                .setVpDistance(500)
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("HouseScene", 600, 600))
                .build()
                .renderImage()
                .writeToImage();
    }

    @Test
    public void housePictureLeft() {
        scene.geometries.add(
                // Grass hill behind the house
                new Sphere(new Point(0, -55, -258), 50)
                        .setEmission(new Color(34, 139, 34)) // Green grass
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setkT(0.0)),

                // Moon
                new Sphere(new Point(-13, 14, -600), 3)
                        .setEmission(new Color(255, 255, 204)) // Light yellow moon glow
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(300).setkT(0.0)),
//front left
                new Triangle(
                        new Point(-4.25, 0, 0),    // Adjusted position to the right by 1 unit
                        new Point(1.75, 0, 0),     // Adjusted position to the right by 1 unit
                        new Point(-4.25, -8, 0)    // Adjusted position to the right by 1 unit
                ).setEmission(new Color(160, 82, 45)) // Light brown color for front
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setkT(0.0)),
//front right
                new Triangle(
                        new Point(1.75, 0, 0),     // Adjusted position to the right by 1 unit
                        new Point(1.75, -8, 0),    // Adjusted position to the right by 1 unit
                        new Point(-4.25, -8, 0)    // Adjusted position to the right by 1 unit
                ).setEmission(new Color(160, 82, 45)) // Light brown color for front
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setkT(0.0)),
 //the problem that need to be fix
                new Triangle(
                        new Point(1.75, 0, 0),    // Reduced by 3 units to the right
                        new Point(1.75, -8, 0),   // Adjusted height by reducing y-coordinate by 2 units
                        new Point(3, -6, -9) // Reduced by 3 units to the right
                ).setEmission(new Color(205, 133, 63)) // Lighter brown color for side
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setkT(0.0)),
//the problem that need to be fix
                new Triangle(
                        new Point(3, 0, -7),    // Reduced by 3 units to the right
                        new Point(1.75, -8, 0),   // Adjusted height by reducing y-coordinate by 2 units
                        new Point(3, -6, -9) // Reduced by 3 units to the right
                ).setEmission(new Color(205, 133, 63)) // Lighter brown color for side
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setkT(0.0)),

// Right corner of the house (3D effect)
                new Triangle(
                        new Point(3, 0, -7),   // נקודת תחתונה קדמית שמאלית
                        new Point(3, -6, -9),  // נקודת עליונה קדמית שמאלית
                        new Point(1, 0, -9)    // נקודת תחתונה אחורית שמאלית
                ).setEmission(new Color(205, 133, 63))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setkT(0.0)),
                new Triangle(
                        new Point(-1, -6, 0),   // Adjusted height by reducing y-coordinate by 2 units
                        new Point(3, 0, -4),    // Reduced by 3 units to the right
                        new Point(3, -6, -4)    // Adjusted height by reducing y-coordinate by 2 units
                ).setEmission(new Color(205, 133, 63)) // Lighter brown color for side
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setkT(0.0)),

//               //  Door (shifted 1 unit to the left)
//                new Triangle(
//                        new Point(-2, -8, 0), // Shifted 1 unit to the left
//                        new Point(-4, -8, 0),
//                        new Point(-4, -5, 0)
//                ).setEmission(new Color(50, 25, 0)) // Dark brown color
//                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setkT(0.0)),
////door
//                new Triangle(
//                        new Point(-2, -8, 0), // Shifted 1 unit to the left
//                        new Point(-2, -5, 0),
//                        new Point(-4, -5, 0)
//                ).setEmission(new Color(50, 25, 0)) // Dark brown color
//                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setkT(0.0)),

                // Roof (without shadow)
                new Triangle(
                        new Point(-5, -1, 5),
                        new Point(-4, 0, 2),
                        new Point(-1, 4, 8)
                ).setEmission(new Color(150, 0, 0)) // Adjust roof color
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setkT(0.0)),

                new Triangle(
                        new Point(2, -1, 5),
                        new Point(3, 0, 3),
                        new Point(-1, 4, 8)
                ).setEmission(new Color(150, 0, 0)) // Adjust roof color
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setkT(0.0)),
//the rooft
                new Triangle(
                        new Point(-5, -1, 5),
                        new Point(2, -1, 5),
                        new Point(-1, 4, 8)
                ).setEmission(new Color(135, 0, 0)) // Adjust roof color
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setkT(0.0))
        );

        // Background color
        scene.setBackground(new Color(0, 0, 40)); // Dark blue background

        // Lights (minimal moonlight)
        scene.lights.add(new DirectionalLight(new Color(30, 30, 30), new Vector(-13, 14, -600))); // Dim moonlight direction

        // Camera setup
        cameraBuilder.setLocation(new Point(0, 0, 1000))
                .setVpDistance(1000)
                .setVpSize(25, 25)
                .setImageWriter(new ImageWriter("DarkHouseSceneLeft", 800, 800))
                .build()
                .renderImage()
                .writeToImage();
    }
}




