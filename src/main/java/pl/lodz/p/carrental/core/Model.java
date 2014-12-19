package pl.lodz.p.carrental.core;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author mkucharek
 */
@XmlRootElement
public class Model {

    private Brand brand;

    private String name;

    public Model() {
    }

    public Model(Brand brand, String name) {
        this.brand = brand;
        this.name = name;
    }

    @XmlElement
    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Model model = (Model) o;

        if (brand != null ? !brand.equals(model.brand) : model.brand != null) {
            return false;
        }
        if (name != null ? !name.equals(model.name) : model.name != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = brand != null ? brand.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
