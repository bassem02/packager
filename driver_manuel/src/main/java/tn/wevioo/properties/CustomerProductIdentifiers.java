package tn.wevioo.properties;

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
 *       &lt;sequence>
 *         &lt;element name="activationcode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "activationcode" })
@XmlRootElement(name = "customerProductIdentifiers")
public class CustomerProductIdentifiers {

	@XmlElement(required = true)
	protected String activationcode;

	/**
	 * Gets the value of the activationcode property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getActivationcode() {
		return activationcode;
	}

	/**
	 * Sets the value of the activationcode property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setActivationcode(String value) {
		this.activationcode = value;
	}

}
