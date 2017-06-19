package tn.wevioo.driverManual.utils.properties;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="refExterne" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dateInstallation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlRootElement(name = "usageProperties")
public class UsageProperties {

	protected String refExterne;
	protected String dateInstallation;

	/**
	 * Gets the value of the refExterne property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getRefExterne() {
		return refExterne;
	}

	/**
	 * Sets the value of the refExterne property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setRefExterne(String value) {
		this.refExterne = value;
	}

	/**
	 * Gets the value of the dateInstallation property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDateInstallation() {
		return dateInstallation;
	}

	/**
	 * Sets the value of the dateInstallation property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDateInstallation(String value) {
		this.dateInstallation = value;
	}

}
