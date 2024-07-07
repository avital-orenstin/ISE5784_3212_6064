package primitives;

/**
 * Class represents the material of a Geometry
 */
public class Material {
    // Field represents the
    public Double3 kD = Double3.ZERO;
    // Field represents the
    public Double3 kS = Double3.ZERO;
    // Field represents the
    public Double3 kT = Double3.ZERO;
    // Field represents the
    public Double3 kR = Double3.ZERO;
    // Field represents the shininess of the material
    public int nShininess = 0;
    public Material setkT(Double3 kT) {
        this.kT = kT;
        return this;
    }
    public Material setkR(Double3 kR) {
        this.kR = kR;
        return this;
    }
    public Material setkT(double kT) {
        this.kT = new Double3(kT);
        return this;
    }
    public Material setkR(double kR) {
        this.kR =  new Double3(kR);
        return this;
    }
    /**
     * Builder patterns setter for field kD
     * @param kD parameter for kD
     * @return Material object
     */
    public Material kR(Double3 kD) {
        this.kR = kR;
        return this;
    }

    /**
     * Builder patterns setter for field kD
     * @param value parameter for kD constructor
     * @return Material object
     */
    public Material setKd(double value) {
        this.kD = new Double3(value);
        return this;
    }
    public Material setKd(Double3 value) {
        this.kD = value;
        return this;
    }


    /**
     * Builder patterns setter for field kS
     * @param kS parameter for kS
     * @return Material object
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Builder patterns setter for field kS
     * @param value parameter for kS constructor
     * @return Material object
     */
    public Material setKs(double value) {
        this.kS = new Double3(value);
        return this;
    }

    /**
     * Builder patterns setter for field nShininess
     * @param nShininess parameter for nShininess
     * @return Material object
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
