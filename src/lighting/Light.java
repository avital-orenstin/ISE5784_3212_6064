package lighting;

import primitives.Color;

public abstract class Light {
    protected Color intensity;

    /**
     * constructor Light
     * @param intensity
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * get Intensity
     * @return
     */
    public Color getIntensity() {
        return intensity;
    }
}

