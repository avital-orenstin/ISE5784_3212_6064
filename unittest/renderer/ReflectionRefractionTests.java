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
    /** Scene for the tests */
    private final Scene          scene         = new Scene("Test scene");
    /** Camera builder for the tests with triangles */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setDirection(new Vector(0,0,-1), new Vector(0,1,0))
            .setRayTracer(new SimpleRayTracer(scene));
    final Camera.Builder camera1 = Camera.getBuilder()
            .setVpSize(300, 300)
            .setLocation(new Point(100, 0, 10))
            .setDirection(new Vector(-1, 0, 0), new Vector(0, 0, 1))
            .setVpDistance(1000);
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
                        new Point(60, 50, 0),
                        new Vector(0, 0, -1))
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
                .setAdaptive(3)
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
    // create square from 2 triangles function.
    private void addSquareFromTriangles(Scene scene, Point p1, Point p2, Point p3,
                                        Color color,double kd,double ks,int Shininess) {
        double dx = p3.xyz.d1 - p1.xyz.d1;
        double dy = p3.xyz.d2 - p1.xyz.d2;
        double dz = p3.xyz.d3 - p1.xyz.d3;
        Point p4 = new Point(p2.xyz.d1 + dx, p2.xyz.d2 + dy, p2.xyz.d3 + dz);

        //add the triangles to the scene.

        scene.geometries.add(
                new Triangle(p1, p2, p3)
                        .setEmission(color)
                        .setMaterial(new Material().setKd(kd).setKs(ks).setShininess(Shininess)),
                new Triangle(p2, p4, p3)
                        .setEmission(color)
                        .setMaterial(new Material().setKd(kd).setKs(ks).setShininess(Shininess))
        );
    }
    @Test
    public void HouseWithoutImprovements() {
        //building
        addSquareFromTriangles(
                scene,
                new Point(-4.25, 0, 0),
                new Point(1.75, 0, 0),
                new Point(-4.25, -8, 0),
                new Color(160, 82, 45),
                0.4, 0.3, 100
        );

        // Side building
        addSquareFromTriangles(
                scene,
                new Point(1.75, 0, 0),
                new Point(1.75, -8, 0),
                new Point(3, -6, -9),
                new Color(205, 133, 63), 0.4, 0.3, 100
        );

        scene.geometries.add(
                // Grass hill behind the house
                new Sphere(
                        new Point(0, -95, -50), 100)
                        .setEmission(new Color(34, 139, 34)) // Green grass
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(10)),

                // Moon with slight transparency
                new Sphere(
                        new Point(8.5, 9, -20), 2)
                        .setEmission(new Color(246, 241, 213))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setkT(0.7)),

                //corner for the 3D
                new Triangle(
                        new Point(3, 0, -7),
                        new Point(1.75, -8, 0),
                        new Point(3, -6, -9))
                        .setEmission(new Color(205, 133, 63))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),


                new Triangle(
                        new Point(3, 0, -7),
                        new Point(3, -6, -9),
                        new Point(1, 0, -9))
                        .setEmission(new Color(205, 133, 63))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),

                // Door
                new Triangle(
                        new Point(-0.2, -6.45, 200),
                        new Point(-1.8, -6.45, 200),
                        new Point(-1.8, -3.45, 200))
                        .setEmission(new Color(50, 25, 0)) // Dark brown color
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),

                new Triangle(
                        new Point(-0.2, -6.45, 200),
                        new Point(-0.2, -3.45, 200),
                        new Point(-1.8, -3.45, 200))
                        .setEmission(new Color(50, 25, 0)) // Dark brown color
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),


                // Roof
                new Triangle(
                        new Point(-5, -1, 5),
                        new Point(-4, 0, 2),
                        new Point(-1, 4, 8))
                        .setEmission(new Color(150, 0, 0)) // Adjust roof color
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),

                new Triangle(
                        new Point(2, -1, 5),
                        new Point(3, 0, 3),
                        new Point(-1, 4, 8))
                        .setEmission(new Color(150, 0, 0)) // Adjust roof color
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),

                new Triangle(
                        new Point(-5, -1, 5),
                        new Point(2, -1, 5),
                        new Point(-1, 4, 8))
                        .setEmission(new Color(135, 0, 0)) // Adjust roof color
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100))
        );


        int numStars = (int) (Math.random() * 70) + 80;

        for (int i = 0; i < numStars; i++) {
            // Generate random position within the upper part of the scene
            double x = Math.random() * 180-60; // Adjusted range for a wider spread
            double y = Math.random() * 7+10; // Adjusted range to spread across the upper half of the scene
            double z = Math.random() * -20 - 50; // Varying depths within the scene

            // Create a small white sphere for the star with random position
            scene.geometries.add(
                    new Sphere(new Point(x, y, z), 0.09) // Small white star
                            .setEmission(new Color(255, 255, 255)) // White color
                            .setMaterial(new Material().setKd(0.2).setKs(0.5).setShininess(50).setkT(0.8)) // Adjusted material properties
            );

            // Create a reflection sphere with kR to enable reflection
            scene.geometries.add(
                    new Sphere(new Point(x - 5, -y + 17, z), 0.09) // Small white star for reflection
                            .setEmission(new Color(255, 255, 255)) // White color
                            .setMaterial(new Material().setKd(0.2).setKs(0.5).setShininess(50).setkR(0.9)) // Adjusted material properties, set kR for reflection
            );

        }

        // Background color
        scene.setBackground(new Color(0, 0, 40)); // Dark blue background

        // Point light to illuminate the moon
        scene.lights.add(
                new PointLight(
                        new Color(255, 255, 180), // Warm yellow light
                        new Point(8.5, 9, -20) // Positioned at the center of the moon
                ));

        // Camera setup
        cameraBuilder.setLocation(new Point(0, 0, 1000))
                .setVpDistance(1000)
                .setVpSize(25, 25)
                .setImageWriter(new ImageWriter("HouseWithoutImprovements", 800, 800))
                .setAdaptive(3)
                .build()
                .renderImage()
                .writeToImage();
    }
    @Test
    public void HouseWithImprovements() {
        //building
        addSquareFromTriangles(
                scene,
                new Point(-4.25, 0, 0),
                new Point(1.75, 0, 0),
                new Point(-4.25, -8, 0),
                new Color(160, 82, 45),
                0.4, 0.3, 100
        );

        // Side building
        addSquareFromTriangles(
                scene,
                new Point(1.75, 0, 0),
                new Point(1.75, -8, 0),
                new Point(3, -6, -9),
                new Color(205, 133, 63), 0.4, 0.3, 100
        );

        scene.geometries.add(
                // Grass hill behind the house
                new Sphere(
                        new Point(0, -95, -50), 100)
                        .setEmission(new Color(34, 139, 34)) // Green grass
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(10)),

                // Moon with slight transparency
                new Sphere(
                        new Point(8.5, 9, -20), 2)
                        .setEmission(new Color(246, 241, 213))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setkT(0.7)),

                //corner for the 3D
                new Triangle(
                        new Point(3, 0, -7),
                        new Point(1.75, -8, 0),
                        new Point(3, -6, -9))
                        .setEmission(new Color(205, 133, 63))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),


                new Triangle(
                        new Point(3, 0, -7),
                        new Point(3, -6, -9),
                        new Point(1, 0, -9))
                        .setEmission(new Color(205, 133, 63))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),

                // Door
                new Triangle(
                        new Point(-0.2, -6.45, 200),
                        new Point(-1.8, -6.45, 200),
                        new Point(-1.8, -3.45, 200))
                        .setEmission(new Color(50, 25, 0)) // Dark brown color
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),

                new Triangle(
                        new Point(-0.2, -6.45, 200),
                        new Point(-0.2, -3.45, 200),
                        new Point(-1.8, -3.45, 200))
                        .setEmission(new Color(50, 25, 0)) // Dark brown color
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),


                // Roof
                new Triangle(
                        new Point(-5, -1, 5),
                        new Point(-4, 0, 2),
                        new Point(-1, 4, 8))
                        .setEmission(new Color(150, 0, 0)) // Adjust roof color
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),

                new Triangle(
                        new Point(2, -1, 5),
                        new Point(3, 0, 3),
                        new Point(-1, 4, 8))
                        .setEmission(new Color(150, 0, 0)) // Adjust roof color
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),

                new Triangle(
                        new Point(-5, -1, 5),
                        new Point(2, -1, 5),
                        new Point(-1, 4, 8))
                        .setEmission(new Color(135, 0, 0)) // Adjust roof color
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100))
        );


        int numStars = (int) (Math.random() * 70) + 80;

        for (int i = 0; i < numStars; i++) {
            // Generate random position within the upper part of the scene
            double x = Math.random() * 180-60; // Adjusted range for a wider spread
            double y = Math.random() * 7+10; // Adjusted range to spread across the upper half of the scene
            double z = Math.random() * -20 - 50; // Varying depths within the scene

            // Create a small white sphere for the star with random position
            scene.geometries.add(
                    new Sphere(new Point(x, y, z), 0.09) // Small white star
                            .setEmission(new Color(255, 255, 255)) // White color
                            .setMaterial(new Material().setKd(0.2).setKs(0.5).setShininess(50).setkT(0.8)) // Adjusted material properties
            );

            // Create a reflection sphere with kR to enable reflection
            scene.geometries.add(
                    new Sphere(new Point(x - 5, -y + 17, z), 0.09) // Small white star for reflection
                            .setEmission(new Color(255, 255, 255)) // White color
                            .setMaterial(new Material().setKd(0.2).setKs(0.5).setShininess(50).setkR(0.9)) // Adjusted material properties, set kR for reflection
            );

        }

        // Background color
        scene.setBackground(new Color(0, 0, 40)); // Dark blue background

        // Point light to illuminate the moon
        scene.lights.add(
                new PointLight(
                        new Color(255, 255, 180), // Warm yellow light
                        new Point(8.5, 9, -20) // Positioned at the center of the moon
                ).setSoftShadow(20,20));

        // Camera setup
        cameraBuilder.setLocation(new Point(0, 0, 1000))
                .setVpDistance(1000)
                .setVpSize(25, 25)
                .setImageWriter(new ImageWriter("HouseWithImprovements", 800, 800))
                .setAntiAliasing(9,9)
                .setAdaptive(3)
                .build()
                .renderImage()
                .writeToImage();
    }

    @Test
    void AntiAliasing_And_SoftShadowTest() {
        final Scene scene = new Scene("Test Anti-Aliasing and Soft-Shadows");
        scene.geometries.add(

                // The part for the house
                new Triangle(new Point(0, 40, 0), new Point(-20, 50, 0), new Point(0, 40, 20))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(5))
                        .setEmission(new Color(128,128,128)),

                new Triangle(new Point(-20, 50, 20), new Point(-20, 50, 0), new Point(0, 40, 20))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(5))
                        .setEmission(new Color(128,128,128)),

                new Triangle(new Point(-10, 20, 0), new Point(-30, 30, 0), new Point(-10, 20, 20))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(5))
                        .setEmission(new Color(128,128,128)),

                new Triangle(new Point(-30, 30, 0), new Point(-10, 20, 20), new Point(-30, 30, 20))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(5))
                        .setEmission(new Color(128,128,128)),

                new Triangle(new Point(-20, 50, 0), new Point(-30, 30, 0), new Point(-30, 30, 20))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(5))
                        .setEmission(new Color(128,128,128)),

                new Triangle(new Point(-20, 50, 20), new Point(-30, 30, 20), new Point(-20, 50, 0))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(5))
                        .setEmission(new Color(128,128,128)),

                new Triangle(new Point(-20, 50, 20), new Point(-15, 35, 35), new Point(0, 40, 20))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(10))
                        .setEmission(new Color(255,0,0)),

                new Triangle(new Point(0, 40, 20), new Point(-15, 35, 35), new Point(-10, 20, 20))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(10))
                        .setEmission(new Color(255,0,0)),

                new Triangle(new Point(-10, 20, 20), new Point(-30, 30, 20), new Point(-15, 35, 35))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(10))
                        .setEmission(new Color(255,0,0)),

                new Triangle(new Point(-30, 30, 20), new Point(-15, 35, 35), new Point(-20, 50, 20))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(10))
                        .setEmission(new Color(255,0,0)),

                new Sphere( new Point(-15, 35, 43),8d)
                        .setEmission(new Color(0, 50, 100))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),

                // The two triangular to the ground
                new Triangle(new Point(-30, 110, 0.1), new Point(12, -40, 0.1), new Point(77, 55, 0.1))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20))
                        .setEmission(new Color(1, 54, 32)),

                new Triangle(new Point(-30, 110, 0.1), new Point(12, -40, 0.1), new Point(-90, 0, 0.1))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20))
                        .setEmission(new Color(1, 54, 32)),


                // The parts for the man behind the house
                new Sphere( new Point(-50, 20, 7.9),8d)
                        .setEmission(new Color(135, 103, 90))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),

                new Sphere( new Point(-50, 20, 20),5d)
                        .setEmission(new Color(135, 103, 90))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),

                new Sphere( new Point(-55, 10, 12),4d)
                        .setEmission(new Color(135, 103, 90))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),

                new Sphere( new Point(-45, 30, 10),4d)
                        .setEmission(new Color(135, 103, 90))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),

                // The parts for the second man next to the house
                new Sphere( new Point(15, 60, 7.9),8d)
                        .setEmission(new Color(135, 103, 90))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),

                new Sphere( new Point(15, 60, 20),5d)
                        .setEmission(new Color(135, 103, 90))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),

                new Sphere( new Point(18, 69, 11),4d)
                        .setEmission(new Color(135, 103, 90))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),

                new Sphere( new Point(18, 52, 15),4d)
                        .setEmission(new Color(135, 103, 90))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),

                // The ball in the house
                new Sphere( new Point(-2, 30, 4.9),5d)
                        .setEmission(new Color(135, 103, 90))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20))
        );

        scene.setAmbientLight(new AmbientLight(new Color(BLACK), new Double3(0.5))).setBackground(new Color(135, 206, 235));

        scene.lights.add(
                new DirectionalLight(new Color(253, 184, 19), new Vector(0.5, -1, -0.9))
        );

        scene.lights.add(
                new PointLight(new Color(253, 184, 19), new Point(-15, 35, 30))
                        .setKl(8E-10).setKq(4E-14)
        );

        scene.lights.add(
                new SpotLight(new Color(253, 184, 19), new Point(-10, 60, 50), new Vector(1, 0, -1))
                        .setKl(8E-10).setKq(4E-14).setSoftShadow(10,10)
        );

        camera1
                .setLocation(new Point(300, 30, 75))
                .setDirection(new Vector(-1, 0, -0.2), new Vector(-0.2, 0, 1))
                .setVpSize(400, 400)
                .setImageWriter(new ImageWriter("AntiAliasing+SoftShadowsTestImage", 1000, 1000))
                .setRayTracer(new SimpleRayTracer(scene).setNy_NX_of_light(9,9))
                .setAntiAliasing(8,8)
                // .setNumThreads(6)
                .setAdaptive(3)
                .build()
                .renderImage()
                .writeToImage();
    }

}