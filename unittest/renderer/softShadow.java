package renderer;

import lighting.PointLight;
import org.junit.jupiter.api.Test;

import geometries.*;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import scene.Scene;
import static java.awt.Color.BLUE;
import static java.awt.Color.WHITE;

/**
 * @author
 *
 */
public class softShadow {
    private final Scene scene = new Scene("Test Shadowscene");
    /**
     * Camera builder for the tests with triangles
     */
    final Camera.Builder camera = Camera.getBuilder()
            .setVpSize(300, 300)
            .setLocation(new Point(100, 0, 10))
            .setDirection(new Vector(-1, 0, 0), new Vector(0, 0, 1))
            .setVpDistance(1000);
    /** Scene of the tests */
    private final Scene          scene2      = new Scene("Test scene");
    /** Camera builder of the tests */
    private final Camera.Builder camera2     = Camera.getBuilder()
            .setDirection(new Vector(0,0,-1),new Vector(0,1,0))
            .setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
            .setVpSize(200, 200)
            .setRayTracer(new SimpleRayTracer(scene2));
    /** Scene for the tests */
    private final Scene          scene3         = new Scene("Test scene");
    /** Camera builder for the tests with triangles */
    private final Camera.Builder camera3 = Camera.getBuilder()
            .setDirection(new Vector(0,0,-1), new Vector(0,1,0))
            .setRayTracer(new SimpleRayTracer(scene3).setNy_NX_of_light(16,16));
    @Test
    void AntiAliasingTest() {

        final Scene scene = new Scene("Test Anti-Aliasing");
        scene.geometries.add(
                new Sphere(new Point(20, 0, 10),10d)
                        .setEmission(new Color(0,0,250))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20))
        );
        scene.lights.add(new PointLight(new Color(255,255,255), new Point(40, 10, 30)));

        // Low Anti-Aliasing for 2X2 for testing
        camera.setImageWriter(new ImageWriter("AntiAliasingTestImage2X2", 1000,1000))
                .setRayTracer(new SimpleRayTracer(scene))
                //.setAntiAliasing(8, 8)
                .setAdaptive(3)
                .build()
                .renderImage()
                .writeToImage();

//        // High Anti-Aliasing for 9X9 for testing
//        camera.setImageWriter(new ImageWriter("AntiAliasingTestImage9X9", 1000,1000))
//                .setAntiAliasing(9, 9)
//                .build()
//                .renderImage()
//                .writeToImage();
    }
    @Test
    void softShadowsTest() {

        // A Soft-Shadows with a blackboard divided into 2x2 (the Soft-Shadows effect is weak)
        final Scene scene1 = new Scene("Test Soft-Shadows");
        scene1.geometries.add(
                new Plane(Point.ZERO, new Point(0, 0, 1), new Point(0, 1, 0))
                        .setEmission(new Color(129, 133, 137))
                        .setMaterial(new Material().setKd(0.5)),

                new Sphere( new Point(0, 0, 5),5d).setEmission(new Color(137, 148, 153))
                        .setMaterial(new Material().setKs(0.4).setShininess(70))
        );
        scene1.lights.add(
                new SpotLight(new Color(255,230,89), new Point(15, 0, -10), new Vector(-1, 0, 1)).setSoftShadow(10,10).setKl(0.1).setKq(0.002)
        );
        camera
                .setImageWriter(new ImageWriter("SoftShadowsTestImage2X2", 1000,1000))
                .setRayTracer(new SimpleRayTracer(scene1))
                .build()
                .renderImage()
                .writeToImage();


        //A Soft-Shadows with a blackboard divided into 9x9 (the Soft-Shadows effect is strong)
        final Scene scene2 = new Scene("Test Soft-Shadows");
        scene2.geometries.add(
                new Plane(Point.ZERO, new Point(0, 0, 1), new Point(0, 1, 0))
                        .setEmission(new Color(129, 133, 137))
                        .setMaterial(new Material().setKd(0.5)),

                new Sphere(new Point(0, 0, 5),5d).setEmission(new Color(137, 148, 153))
                        .setMaterial(new Material().setKs(0.4).setShininess(70))
        );
        scene2.lights.add(
                new SpotLight(new Color(255,230,89), new Point(15, 0, -10), new Vector(-1, 0, 1)).setSoftShadow(10,10).setKl(0.1).setKq(0.002)
        );

        camera
                .setImageWriter(new ImageWriter("SoftShadowsTestImage9X9", 1000,1000))
                .setRayTracer(new SimpleRayTracer(scene2))
                .build()
                .renderImage()
                .writeToImage();
    }
    @Test
    public void shtrianglesSphere() {
        scene2.geometries.add(
                new Triangle(
                        new Point(-150, -150, -115),
                        new Point(150, -150, -135),
                        new Point(75, 75, -150))
                        .setMaterial(new Material().setKs(0.8).setShininess(60)),
                new Triangle(
                        new Point(-150, -150, -115),
                        new Point(-70, 70, -140),
                        new Point(75, 75, -150))
                        .setMaterial(new Material().setKs(0.8).setShininess(60)),
                new Sphere(new Point(0, 0, -11), 30d)
                        .setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30))
        );
        scene2.setAmbientLight(
                new AmbientLight(
                        new Color(WHITE),
                        new Double3(0.15,0.15,0.15)));
        scene2.lights.add(
                new SpotLight(
                        new Color(700, 400, 400),
                        new Point(40, 40, 115),
                        new Vector(-1, -1, -4)) //
                        .setSoftShadow(10,10) .setKl(4E-4).setKq(2E-5));

        camera2.setImageWriter(new ImageWriter("shadowSphere", 600, 600))
                .build()
                .renderImage()
                .writeToImage();
    }
    @Test
    public void trianglesTransparentSphere() {
        scene3.geometries.add(
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
        scene3.setAmbientLight(
                new AmbientLight(new Color(WHITE), new Double3(0.15, 0.15, 0.15)));
        scene3.lights.add(
                new SpotLight(
                        new Color(700, 400, 400),
                        new Point(60, 50, 0),
                        new Vector(0, 0, -1))
                        .setSoftShadow(10,10).setKl(4E-5).setKq(2E-7));

        camera3.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("sShadow", 600, 600))
                .build()
                .renderImage()
                .writeToImage();
    }

}