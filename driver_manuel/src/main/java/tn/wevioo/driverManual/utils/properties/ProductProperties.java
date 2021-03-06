package tn.wevioo.driverManual.utils.properties;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="idClient" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="hexacle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="typeProduct" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="infoCompl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "productProperties")
public class ProductProperties {

	@XmlElement(required = true)
	protected String idClient;
	protected String hexacle;
	@XmlElement(required = true)
	protected String typeProduct;
	protected String infoCompl;

	/**
	 * Gets the value of the idClient property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getIdClient() {
		return idClient;
	}

	/**
	 * Sets the value of the idClient property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setIdClient(String value) {
		this.idClient = value;
	}

	/**
	 * Gets the value of the hexacle property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getHexacle() {
		return hexacle;
	}

	/**
	 * Sets the value of the hexacle property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setHexacle(String value) {
		this.hexacle = value;
	}

	/**
	 * Gets the value of the typeProduct property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTypeProduct() {
		return typeProduct;
	}

	/**
	 * Sets the value of the typeProduct property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setTypeProduct(String value) {
		this.typeProduct = value;
	}

	/**
	 * Gets the value of the infoCompl property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getInfoCompl() {
		return infoCompl;
	}

	/**
	 * Sets the value of the infoCompl property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setInfoCompl(String value) {
		this.infoCompl = value;
	}

}
