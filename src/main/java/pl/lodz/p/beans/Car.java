package pl.lodz.p.beans;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author mkucharek
 */
@XmlRootElement
public class Car {

    private Integer id;

    private String brandName;

    private String modelName;

    // This is extremely important! Without it, JAXB will fail to perform XML <-> Object transformation
    public Car() {
    }

    public Car(Integer id, String brandName, String modelName) {
        this.id = id;
        this.brandName = brandName;
        this.modelName = modelName;
    }

    @XmlAttribute
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlElement
    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    @XmlElement
    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brandName='" + brandName + '\'' +
                ", modelName='" + modelName + '\'' +
                '}';
    }

}
