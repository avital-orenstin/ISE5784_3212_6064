package renderer;
import primitives.Color;
import scene.Scene;
import primitives.Ray;

public abstract class RayTracerBase {

    // שדה סצנה בהרשאה protected
    protected Scene scene;

    // בנאי המקבל בפרמטר אובייקט של סצנה
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    // מתודה אבסטרקטית ציבורית traceRay
    /**
     * שיטה זו עוקבת אחר קרן אור בסצנה ומחזירה את הצבע שבו היא פוגעת.
     *
     * @param ray קרן האור שעבורה רוצים לחשב את הצבע
     * @return צבע האובייקט הראשון שהקרן פוגעת בו, או צבע הרקע אם הקרן לא פוגעת באף אובייקט
     */
    public abstract Color traceRay(Ray ray);
}
