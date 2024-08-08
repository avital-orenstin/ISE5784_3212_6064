package primitives;

/**
 * Class represents the material of a Geometry.
 */
public class Material {

    /**
     * Diffuse attenuation factor.
     */
    public Double3 kD = Double3.ZERO;

    /**
     * Specular attenuation factor.
     */
    public Double3 kS = Double3.ZERO;

    /**
     * Transparency coefficient.
     */
    public Double3 kT = Double3.ZERO;

    /**
     * Reflection coefficient.
     */
    public Double3 kR = Double3.ZERO;

    /**
     * Shininess factor of the material.
     */
    public int nShininess = 0;

    /**
     * Getter for diffuse attenuation factor.
     *
     * @return the diffuse attenuation factor.
     */
    public Double3 getkD() {
        return kD;
    }

    /**
     * Getter for specular attenuation factor.
     *
     * @return the specular attenuation factor.
     */
    public Double3 getkS() {
        return kS;
    }

    /**
     * Getter for transparency coefficient.
     *
     * @return the transparency coefficient.
     */
    public Double3 getkT() {
        return kT;
    }

    /**
     * Getter for shininess factor.
     *
     * @return the shininess factor.
     */
    public int getnShininess() {
        return nShininess;
    }

    /**
     * Getter for reflection coefficient.
     *
     * @return the reflection coefficient.
     */
    public Double3 getkR() {
        return kR;
    }

    /**
     * Setter for transparency coefficient with Double3.
     *
     * @param kT the transparency coefficient.
     * @return the material itself for chaining.
     */
    public Material setkT(Double3 kT) {
        this.kT = kT;
        return this;
    }

    /**
     * Setter for reflection coefficient with Double3.
     *
     * @param kR the reflection coefficient.
     * @return the material itself for chaining.
     */
    public Material setkR(Double3 kR) {
        this.kR = kR;
        return this;
    }

    /**
     * Setter for transparency coefficient with double.
     *
     * @param kT the transparency coefficient.
     * @return the material itself for chaining.
     */
    public Material setkT(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    /**
     * Setter for reflection coefficient with double.
     *
     * @param kR the reflection coefficient.
     * @return the material itself for chaining.
     */
    public Material setkR(double kR) {
        this.kR = new Double3(kR);
        return this;
    }

    /**
     * Setter for diffuse attenuation factor with Double3.
     *
     * @param kD the diffuse attenuation factor.
     * @return the material itself for chaining.
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Setter for diffuse attenuation factor with double.
     *
     * @param value the diffuse attenuation factor.
     * @return the material itself for chaining.
     */
    public Material setKd(double value) {
        this.kD = new Double3(value);
        return this;
    }

    /**
     * Setter for specular attenuation factor with Double3.
     *
     * @param kS the specular attenuation factor.
     * @return the material itself for chaining.
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Setter for specular attenuation factor with double.
     *
     * @param value the specular attenuation factor.
     * @return the material itself for chaining.
     */
    public Material setKs(double value) {
        this.kS = new Double3(value);
        return this;
    }

    /**
     * Setter for shininess factor.
     *
     * @param nShininess the shininess factor.
     * @return the material itself for chaining.
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}