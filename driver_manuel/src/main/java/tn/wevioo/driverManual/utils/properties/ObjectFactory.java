package tn.wevioo.driverManual.properties;

import javax.xml.bind.annotation.XmlRegistry;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the nordnet.drivers.manual.properties package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the
 * Java representation for XML content. The Java representation of XML content
 * can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory
 * methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

	/**
	 * Create a new ObjectFactory that can be used to create new instances of
	 * schema derived classes for package: nordnet.drivers.manual.properties
	 * 
	 */
	public ObjectFactory() {
	}

	/**
	 * Create an instance of {@link CustomerProductIdentifiers }
	 * 
	 */
	public CustomerProductIdentifiers createCustomerProductIdentifiers() {
		return new CustomerProductIdentifiers();
	}

	/**
	 * Create an instance of {@link ProductProperties }
	 * 
	 */
	public ProductProperties createProductProperties() {
		return new ProductProperties();
	}

	/**
	 * Create an instance of {@link UsageProperties }
	 * 
	 */
	public UsageProperties createUsageProperties() {
		return new UsageProperties();
	}

}
