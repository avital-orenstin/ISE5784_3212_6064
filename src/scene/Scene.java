package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;

public class Scene {
    public String name;
    public Color background;
    public AmbientLight ambientLight = AmbientLight.NONE;
    public Geometries geometries = new Geometries();

    // בנאי
    public Scene(String name) {
        this.name = name;
    }

    // סטרים לצבע רקע
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    // סטרים לתאורה סביבתית
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    // סטרים למודל תלת-ממד
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    public Scene build() {
        // Since all configuration happens during object creation with
        // setter-like methods, we simply return 'this' here.
        return this;
    }
}
