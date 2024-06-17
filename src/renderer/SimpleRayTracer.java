package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

public class SimpleRayTracer extends RayTracerBase {

    /**
     * בנאי פשוט של מעקב קרניים.
     *
     * @param scene הסצנה שבה רוצים לבצע עקיבה אחר קרני אור
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        return null;
    }
}
