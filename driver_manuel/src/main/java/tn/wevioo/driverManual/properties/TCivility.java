package tn.wevioo.driverManual.properties;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for TCivility.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * <p>
 * 
 * <pre>
 * &lt;simpleType name="TCivility">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="M."/>
 *     &lt;enumeration value="Mlle"/>
 *     &lt;enumeration value="Mme"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TCivility", namespace = "http://www.nordnet.com/generic/types")
@XmlEnum
public enum TCivility {

	@XmlEnumValue("M.")
	M("M."), @XmlEnumValue("Mlle")
	MLLE("Mlle"), @XmlEnumValue("Mme")
	MME("Mme");
	private final String value;

	TCivility(String v) {
		value = v;
	}

	public String value() {
		return value;
	}

	public static TCivility fromValue(String v) {
		for (TCivility c : TCivility.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}

}
