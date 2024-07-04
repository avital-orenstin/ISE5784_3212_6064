package lighting;

import primitives.Color;
import primitives.Double3;

public class AmbientLight {
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, 0.0);
    final Color intensity;

    public AmbientLight(Color _iA, Double3 _kA) {
        //Ip = Ka * Ia
        this.intensity = _iA.scale(_kA);
    }

    public AmbientLight(Color _iA, Double _kA) {
        this.intensity = _iA.scale(_kA);
    }

    public Color getIntensity() {
        return intensity;
    }
}

