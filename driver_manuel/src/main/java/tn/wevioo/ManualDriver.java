package tn.wevioo;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import nordnet.architecture.exceptions.NNException;
import nordnet.architecture.exceptions.NNImplicitException;
import nordnet.architecture.exceptions.explicit.DataSourceException;
import nordnet.architecture.exceptions.explicit.MalformedXMLException;
import nordnet.architecture.exceptions.explicit.NotFoundException;
import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.implicit.NullException.NullCases;
import nordnet.architecture.exceptions.implicit.UnsupportedActionException;
import nordnet.architecture.exceptions.utils.ErrorCode;
import nordnet.drivers.contract.exceptions.DriverException;
import nordnet.drivers.contract.impl.ProductDriverImpl;
import nordnet.drivers.contract.types.FeasibilityTestResult;
import nordnet.drivers.contract.types.State;
import nordnet.drivers.tools.Reference;
import nordnet.drivers.tools.ReferenceKeys;
import tn.wevioo.configuration.ManualConfiguration;
import tn.wevioo.configuration.ManualInternalConfiguration;
import tn.wevioo.entities.Product;
import tn.wevioo.entities.type.StateProduct;
import tn.wevioo.properties.CustomerProductIdentifiers;
import tn.wevioo.properties.ProductProperties;
import tn.wevioo.properties.UsageProperties;
import tn.wevioo.service.ProductService;

/**
 * The ManuelDriver driver class.
 * 
 * @author kad
 * 
 */
@Component
public class ManualDriver extends ProductDriverImpl<ManualConfiguration, ManualInternalConfiguration> {

	static final Log LOGGER = LogFactory.getLog(ManualDriver.class);

	private String providerProductId;

	@Autowired
	private ProductService productService;

	protected ManualDriver() throws DriverException {
		super();
	}

	public void delete(final String properties, String ppid)
			throws DriverException, MalformedXMLException, NotRespectedRulesException, SAXException, IOException,
			ParserConfigurationException, DataSourceException, NotFoundException {

		Object parsedProperties = null;

		try {
			parsedProperties = startDeleteManual(properties, ppid);
			performDeleteManual(parsedProperties, ppid);
		} catch (DriverException ex) {
			failDeleteManual(properties, ex, ppid);
			throw ex;
		} catch (NotRespectedRulesException ex) {
			failDeleteManual(properties, ex, ppid);
			throw ex;
		} catch (MalformedXMLException ex) {
			failDeleteManual(properties, ex, ppid);
			throw ex;
		} catch (NullException ex) {
			failDeleteManual(properties, ex, ppid);
			throw ex;
		}

		successDeleteManual(properties, ppid);
	}

	protected void successDeleteManual(String properties, String ppid) throws DriverException {
		this.getHistoricalReportLogger().info(
				"The product " + this.getClass().getSimpleName() + " [" + ppid + "] has been successfully deleted.");
	}

	protected void performDeleteManual(Object arg0, String ppid)
			throws DriverException, NotRespectedRulesException, DataSourceException, NotFoundException {

		productService.delete(productService.findByProviderProductId(ppid));
	}

	protected void failDeleteManual(final String properties, final Exception cause, String ppid)
			throws DriverException {
		if (cause == null) {
			throw new NullException(NullCases.NULL, "cause");
		}

		String errorCode = "N/A";
		if (cause instanceof NNException) {
			errorCode = ((NNException) cause).getErrorCode().toString();
		} else {
			if (cause instanceof NNImplicitException) {
				errorCode = ((NNImplicitException) cause).getErrorCode().toString();
			}
		}

		this.getHistoricalReportLogger()
				.error("The product " + this.getClass().getSimpleName() + " [" + ppid
						+ "] cannot be deleted because of exception " + cause.getClass().getSimpleName() + "/"
						+ errorCode + ":" + cause.getMessage());
	}

	protected Object startDeleteManual(final String properties, String ppid) throws DriverException,
			MalformedXMLException, NotRespectedRulesException, SAXException, IOException, ParserConfigurationException {
		Object result = null;
		result = ManualDriverFactory.parse(properties);
		if (!this.getCurrentState(ppid).equals(State.CANCELED)) {
			throw new NotRespectedRulesException(new ErrorCode("1.2.2.13"), new Object[] { ppid, State.CANCELED });
		}

		return result;
	}

	public void resetManual(final String properties, String ppid) throws DriverException, MalformedXMLException,
			NotRespectedRulesException, SAXException, IOException, ParserConfigurationException {

		Object parsedProperties = null;

		try {
			parsedProperties = startResetManual(properties, ppid);
			performReset(parsedProperties);
		} catch (DriverException ex) {
			failResetManual(properties, ex, ppid);
			throw ex;
		} catch (NotRespectedRulesException ex) {
			failResetManual(properties, ex, ppid);
			throw ex;
		} catch (MalformedXMLException ex) {
			failResetManual(properties, ex, ppid);
			throw ex;
		} catch (NullException ex) {
			failResetManual(properties, ex, ppid);
			throw ex;
		}

		successReset(properties);
	}

	protected void failResetManual(final String properties, final Exception cause, String ppid) throws DriverException {
		if (cause == null) {
			throw new NullException(NullCases.NULL, "cause");
		}
		String errorCode = "N/A";
		if (cause instanceof NNException) {
			errorCode = ((NNException) cause).getErrorCode().toString();
		} else {
			if (cause instanceof NNImplicitException) {
				errorCode = ((NNImplicitException) cause).getErrorCode().toString();
			}
		}

		this.getHistoricalReportLogger()
				.error("The product " + this.getClass().getSimpleName() + " [" + ppid
						+ "] cannot be reset because of exception " + cause.getClass().getSimpleName() + "/" + errorCode
						+ ":" + cause.getMessage());
	}

	protected Object startResetManual(final String properties, String ppid) throws DriverException,
			MalformedXMLException, NotRespectedRulesException, SAXException, IOException, ParserConfigurationException {
		Object result = null;
		result = ManualDriverFactory.parse(properties);
		if (!this.getCurrentState(ppid).equals(State.ACTIVE)) {
			throw new NotRespectedRulesException(new ErrorCode("1.2.2.13"), new Object[] { ppid, State.ACTIVE });
		}

		return result;
	}

	public void cancelManual(final String properties, String ppid) throws DriverException, MalformedXMLException,
			NotRespectedRulesException, SAXException, IOException, ParserConfigurationException {

		Object parsedProperties = null;

		try {
			parsedProperties = startCancelManual(properties, ppid);
			performCancelManual(parsedProperties, ppid);
		} catch (DriverException ex) {
			failCancelManual(properties, ex, ppid);
			throw ex;
		} catch (NotRespectedRulesException ex) {
			failCancelManual(properties, ex, ppid);
			throw ex;
		} catch (MalformedXMLException ex) {
			failCancelManual(properties, ex, ppid);
			throw ex;
		} catch (NullException ex) {
			failCancelManual(properties, ex, ppid);
			throw ex;
		}

		successCancelManual(properties, ppid);
	}

	protected void successCancelManual(final String properties, String ppid) throws DriverException {
		this.getHistoricalReportLogger().info(
				"The product " + this.getClass().getSimpleName() + " [" + ppid + "] has been successfully canceled.");
	}

	protected void failCancelManual(final String properties, final Exception cause, String ppid)
			throws DriverException {
		if (cause == null) {
			throw new NullException(NullCases.NULL, "cause");
		}

		String errorCode = "N/A";
		if (cause instanceof NNException) {
			errorCode = ((NNException) cause).getErrorCode().toString();
		} else {
			if (cause instanceof NNImplicitException) {
				errorCode = ((NNImplicitException) cause).getErrorCode().toString();
			}
		}

		this.getHistoricalReportLogger()
				.error("The product " + this.getClass().getSimpleName() + " [" + ppid
						+ "] cannot be canceled because of exception " + cause.getClass().getSimpleName() + "/"
						+ errorCode + ":" + cause.getMessage());
	}

	protected Object startCancelManual(final String properties, String ppid) throws DriverException,
			MalformedXMLException, NotRespectedRulesException, SAXException, IOException, ParserConfigurationException {
		Object result = null;
		result = ManualDriverFactory.parse(properties);
		if (this.getCurrentState(ppid).equals(State.CANCELED)) {
			throw new NotRespectedRulesException(new ErrorCode("1.2.2.14"), new Object[] { ppid, State.CANCELED });
		}

		return result;
	}

	public void reactivateManual(final String properties, String ppid) throws DriverException, MalformedXMLException,
			NotRespectedRulesException, SAXException, IOException, ParserConfigurationException {

		Object parsedProperties = null;

		try {
			parsedProperties = startReactivateManual(properties, ppid);
			performReactivateManual(parsedProperties, ppid);
		} catch (DriverException ex) {
			failReactivateManual(properties, ex, ppid);
			throw ex;
		} catch (NotRespectedRulesException ex) {
			failReactivateManual(properties, ex, ppid);
			throw ex;
		} catch (MalformedXMLException ex) {
			failReactivateManual(properties, ex, ppid);
			throw ex;
		} catch (NullException ex) {
			failReactivateManual(properties, ex, ppid);
			throw ex;
		}

		successReactivateManual(properties, ppid);
	}

	protected void successReactivateManual(String properties, String ppid) throws DriverException {
		this.getHistoricalReportLogger().info("The product " + this.getClass().getSimpleName() + " [" + ppid
				+ "] has been successfully reactivated (new state=[" + this.getCurrentState(ppid) + "]).");
	}

	protected void failReactivateManual(final String properties, final Exception cause, String ppid)
			throws DriverException {
		if (cause == null) {
			throw new NullException(NullCases.NULL, "cause");
		}

		String errorCode = "N/A";
		if (cause instanceof NNException) {
			errorCode = ((NNException) cause).getErrorCode().toString();
		} else {
			if (cause instanceof NNImplicitException) {
				errorCode = ((NNImplicitException) cause).getErrorCode().toString();
			}
		}

		this.getHistoricalReportLogger()
				.error("The product " + this.getClass().getSimpleName() + " [" + ppid
						+ "] cannot be reactivated because of exception " + cause.getClass().getSimpleName() + "/"
						+ errorCode + ":" + cause.getMessage());
	}

	protected Object startReactivateManual(final String properties, String ppid) throws MalformedXMLException,
			DriverException, NotRespectedRulesException, SAXException, IOException, ParserConfigurationException {

		Object result = null;
		result = ManualDriverFactory.parse(properties);

		if (!this.getCurrentState(ppid).equals(State.SUSPENDED)) {
			throw new NotRespectedRulesException(new ErrorCode("1.2.2.13"), new Object[] { ppid, State.SUSPENDED });
		}

		return result;
	}

	public void activateManual(final String properties, String ppid) throws DriverException, MalformedXMLException,
			NotRespectedRulesException, SAXException, IOException, ParserConfigurationException {
		Object parsedProperties = null;

		try {
			parsedProperties = startActivateManual(properties, ppid);
			performActivateManual(parsedProperties, ppid);
		} catch (DriverException ex) {
			failActivateManual(properties, ex, ppid);
			throw ex;
		} catch (NotRespectedRulesException ex) {
			failActivateManual(properties, ex, ppid);
			throw ex;
		} catch (MalformedXMLException ex) {
			failActivateManual(properties, ex, ppid);
			throw ex;
		} catch (NullException ex) {
			failActivateManual(properties, ex, ppid);
			throw ex;
		}

		successActivateManual(properties, ppid);
	}

	protected void successActivateManual(final String properties, String ppid) throws DriverException {
		this.getHistoricalReportLogger().info(
				"The product " + this.getClass().getSimpleName() + " [" + ppid + "] has been successfully activated.");
	}

	protected void failActivateManual(final String properties, final Exception cause, String ppid)
			throws DriverException {
		if (cause == null) {
			throw new NullException(NullCases.NULL, "cause");
		}
		String errorCode = "N/A";
		if (cause instanceof NNException) {
			errorCode = ((NNException) cause).getErrorCode().toString();
		} else {
			if (cause instanceof NNImplicitException) {
				errorCode = ((NNImplicitException) cause).getErrorCode().toString();
			}
		}

		this.getHistoricalReportLogger()
				.error("The product " + this.getClass().getSimpleName() + " [" + ppid
						+ "] cannot be activated because of exception " + cause.getClass().getSimpleName() + "/"
						+ errorCode + ":" + cause.getMessage());
	}

	protected Object startActivateManual(final String properties, String ppid) throws DriverException,
			MalformedXMLException, NotRespectedRulesException, SAXException, IOException, ParserConfigurationException {

		Object result = null;
		result = ManualDriverFactory.parse(properties);

		if (!this.getCurrentState(ppid).equals(State.ACTIVABLE)) {
			throw new NotRespectedRulesException(new ErrorCode("1.2.2.13"),
					new Object[] { this.getProviderProductId(), State.ACTIVABLE });
		}

		return result;

	}

	public void suspendManual(final String properties, String ppid) throws DriverException, MalformedXMLException,
			NotRespectedRulesException, SAXException, IOException, ParserConfigurationException {

		Object parsedProperties = null;

		try {
			parsedProperties = startSuspendManual(properties, ppid);
			performSuspendManual(parsedProperties, ppid);
		} catch (DriverException ex) {
			failSuspendManual(properties, ex, ppid);
			throw ex;
		} catch (NotRespectedRulesException ex) {
			failSuspendManual(properties, ex, ppid);
			throw ex;
		} catch (MalformedXMLException ex) {
			failSuspendManual(properties, ex, ppid);
			throw ex;
		} catch (NullException ex) {
			failSuspendManual(properties, ex, ppid);
			throw ex;
		}

		successSuspendManual(properties, ppid);
	}

	protected void failSuspendManual(final String properties, final Exception cause, String ppid)
			throws DriverException {
		if (cause == null) {
			throw new NullException(NullCases.NULL, "cause");
		}

		String errorCode = "N/A";
		if (cause instanceof NNException) {
			errorCode = ((NNException) cause).getErrorCode().toString();
		} else {
			if (cause instanceof NNImplicitException) {
				errorCode = ((NNImplicitException) cause).getErrorCode().toString();
			}
		}

		this.getHistoricalReportLogger()
				.error("The product " + this.getClass().getSimpleName() + " [" + ppid
						+ "] cannot be suspended because of exception " + cause.getClass().getSimpleName() + "/"
						+ errorCode + ":" + cause.getMessage());
	}

	protected void successSuspendManual(final String properties, String ppid) throws DriverException {
		this.getHistoricalReportLogger().info(
				"The product " + this.getClass().getSimpleName() + " [" + ppid + "] has been successfully suspended.");
	}

	protected Object startSuspendManual(final String properties, String ppid) throws DriverException,
			MalformedXMLException, NotRespectedRulesException, SAXException, IOException, ParserConfigurationException {
		Object result = null;
		result = ManualDriverFactory.parse(properties);

		State productState = this.getCurrentState(ppid);
		if (!productState.equals(State.ACTIVE) && !productState.equals(State.ACTIVABLE)
				&& !productState.equals(State.INPROGRESS) && !productState.equals(State.DELIVERED)) {
			throw new NotRespectedRulesException(new ErrorCode("1.2.2.12"), new Object[] { ppid, productState });
		}

		return result;
	}

	public State getCurrentState() throws DriverException {
		LOGGER.info("Get current state of the product: " + this.getProviderProductId());
		StateProduct status = null;
		try {
			status = this.productService.getCurrentStatus(this.getProviderProductId());
		} catch (NNException e1) {
			LOGGER.error(e1);
			throw new DriverException(e1);
		}
		return State.valueOf(status.toString());
	}

	public State getCurrentState(String ref) throws DriverException {
		LOGGER.info("Get current state of the product: " + ref);
		StateProduct status = null;
		try {
			status = this.productService.getCurrentStatus(ref);
		} catch (NNException e1) {
			LOGGER.error(e1);
			throw new DriverException(e1);
		}
		return State.valueOf(status.toString());

	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public String getCustomerProductIdentifiers() throws DriverException {
		LOGGER.info("Get customer product identifiers for the product: " + this.getProviderProductId());
		CustomerProductIdentifiers identifiers = new CustomerProductIdentifiers();
		Product product;
		try {
			product = this.productService.findByProviderProductId(this.getProviderProductId());
		} catch (NNException e1) {
			LOGGER.error(e1);
			throw new DriverException(e1);
		}
		identifiers.setActivationcode(product.getProviderProductId());

		String result = null;
		try {
			result = super.generateXmlProperties(identifiers);
		} catch (NotRespectedRulesException e) {
			LOGGER.error("The generation of XML isn't done", e);
			throw new DriverException(new ErrorCode("0.1.1.12.2"));
		}
		LOGGER.info("Customer product identifiers returned successfully !");
		return result;

	}

	public String getProductProperties() throws DriverException {
		LOGGER.info("Get productProperties  identifiers for the product: " + this.getProviderProductId());

		Product product;
		try {
			product = this.productService.findByProviderProductId(this.getProviderProductId());
		} catch (NNException e1) {
			LOGGER.error(e1);
			throw new DriverException(e1);
		}
		ProductProperties productProperties = new ProductProperties();
		productProperties.setHexacle(product.getCurrentHexacle());
		productProperties.setIdClient(product.getIdClientInterne());
		productProperties.setInfoCompl(product.getInfoCompl());
		productProperties.setTypeProduct(product.getTypeProduct());
		String xml = null;
		try {
			xml = generateXmlProductProperties(productProperties);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return xml;
	}

	public String getProductProperties(String ppid) throws DriverException {
		LOGGER.info("Get productProperties  identifiers for the product: " + ppid);

		Product product;
		try {
			product = this.productService.findByProviderProductId(ppid);
		} catch (NNException e1) {
			LOGGER.error(e1);
			throw new DriverException(e1);
		}
		ProductProperties productProperties = new ProductProperties();
		productProperties.setHexacle(product.getCurrentHexacle());
		productProperties.setIdClient(product.getIdClientInterne());
		productProperties.setInfoCompl(product.getInfoCompl());
		productProperties.setTypeProduct(product.getTypeProduct());
		String xml = null;
		try {
			xml = generateXmlProductProperties(productProperties);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return xml;
	}

	@Transient
	public ProductService getProductService() {
		return this.productService;
	}

	public List<Reference> getReferences() throws DriverException {
		LOGGER.info("Get product references for the product: " + this.getProviderProductId());

		try {
			this.productService.findByProviderProductId(this.getProviderProductId());
		} catch (NNException e1) {
			LOGGER.error(e1);
			throw new DriverException(e1);
		}
		List<Reference> references = new ArrayList<Reference>();
		references.add(new Reference(ReferenceKeys.PROVIDER_PRODUCT_ID, this.getProviderProductId()));
		references.add(new Reference(ReferenceKeys.DISCRIMINATOR, this.getProviderProductId()));
		LOGGER.info("Product references returned successfully !");

		return references;
	}

	public List<tn.wevioo.tools.Reference> getReference(String ppid) throws DriverException {
		LOGGER.info("Get product references for the product: " + ppid);

		try {
			this.productService.findByProviderProductId(ppid);
		} catch (NNException e1) {
			LOGGER.error(e1);
			throw new DriverException(e1);
		}
		List<tn.wevioo.tools.Reference> references = new ArrayList<tn.wevioo.tools.Reference>();
		references.add(new tn.wevioo.tools.Reference(ReferenceKeys.PROVIDER_PRODUCT_ID, ppid));
		references.add(new tn.wevioo.tools.Reference(ReferenceKeys.DISCRIMINATOR, ppid));
		LOGGER.info("Product references returned successfully !");

		return references;
	}

	@Override
	protected void performActivate(Object properties) throws DriverException, NotRespectedRulesException {
		LOGGER.info("Activate the product: " + this.getProviderProductId());
		return;
	}

	protected void performActivateManual(Object properties, String ppid)
			throws DriverException, NotRespectedRulesException {
		LOGGER.info("Activate the product: " + ppid);
		try {
			this.productService.activateProductManual(ppid);
		} catch (NNException e) {
			LOGGER.error(e);
			throw new DriverException(e);
		}
		LOGGER.info("The Activate is done");
	}

	@Override
	protected void performCancel(Object properties) throws DriverException, NotRespectedRulesException {
		LOGGER.info("Cancel the product: " + this.getProviderProductId());
		try {
			this.productService.cancelProductTroughPackager(this.getProviderProductId());
		} catch (NNException e) {
			LOGGER.error(e);
			throw new DriverException(e);
		}
		LOGGER.info("The cancellation is done");

	}

	protected void performCancelManual(Object properties, String ppid)
			throws DriverException, NotRespectedRulesException {
		LOGGER.info("Cancel the product: " + ppid);
		try {
			this.productService.cancelProductTroughPackager(ppid);
		} catch (NNException e) {
			LOGGER.error(e);
			throw new DriverException(e);
		}
		LOGGER.info("The cancellation is done");

	}

	@Override
	protected void performChangeProperties(Object properties) throws DriverException, NotRespectedRulesException {
		LOGGER.info("Change properties of the product: " + this.getProviderProductId());
		if (!(properties instanceof ProductProperties)) {
			throw new NotRespectedRulesException(new ErrorCode("1.2.1.1.5"), new Object[] { "productProperties",
					ProductProperties.class, properties.getClass().getSimpleName() });
		}
		Product product = null;
		try {
			product = this.productService.findByProviderProductId(this.getProviderProductId());
		} catch (NNException e1) {
			LOGGER.error(e1);
			throw new DriverException(e1);
		}
		ProductProperties productProperties = (ProductProperties) properties;

		if (productProperties.getHexacle() != null) {
			if (product.getCurrentHexacle() == null) {
				product.setCurrentHexacle(productProperties.getHexacle());
			} else {
				product.setPreviousHexacle(product.getCurrentHexacle());
				product.setCurrentHexacle(productProperties.getHexacle());
			}

			try {
				if (!product.getCurrentHexacle().equals(product.getPreviousHexacle())) {
					this.productService.changeProduct(product);
				}
			} catch (NNException e) {
				LOGGER.error(e);
				throw new DriverException(e);
			}
		}
		LOGGER.info("Properties changed successfully !");
	}

	@Override
	protected void performDelete(Object arg0) throws DriverException, NotRespectedRulesException {
		throw new UnsupportedActionException("performReset action not supported for Manual driver.");
	}

	@Override
	protected String performGetUsageProperties(Object arg0) throws DriverException {
		LOGGER.info("Get usage  properties of the product: " + this.getProviderProductId());
		UsageProperties usageProperties = new UsageProperties();
		Product product;
		try {
			product = this.productService.findByProviderProductId(this.getProviderProductId());
		} catch (NNException e1) {
			LOGGER.error(e1);
			throw new DriverException(e1);
		}
		usageProperties.setDateInstallation(product.getCreationDate().toString());
		usageProperties.setRefExterne(product.getRefExterne());
		String result = null;
		try {
			result = super.generateXmlProperties(usageProperties);
			LOGGER.info("Generation of XML is done");
		} catch (NotRespectedRulesException e) {
			LOGGER.error("The generation of XML isn't done", e);
			throw new DriverException(new ErrorCode("0.1.1.12.2"));
		}
		return result;
	}

	public void setProviderProductId(final String providerProductId)
			throws DriverException, NotRespectedRulesException {
		if ((providerProductId == null) || (providerProductId.trim().length() == 0)) {
			throw new NullException(NullCases.NULL_EMPTY, "providerProductId");
		}
		this.providerProductId = providerProductId;

	}

	public String getProviderProductId() {
		if ((this.providerProductId == null) || (this.providerProductId.trim().length() == 0)) {
			throw new NullException(NullCases.NULL_EMPTY, "providerProductId");
		}
		return this.providerProductId;
	}

	public String performGetUsageProperties(String ppid) throws DriverException, JAXBException {
		LOGGER.info("Get usage  properties of the product: " + ppid);
		UsageProperties usageProperties = new UsageProperties();
		Product product;
		try {
			product = this.productService.findByProviderProductId(ppid);
		} catch (NNException e1) {
			LOGGER.error(e1);
			throw new DriverException(e1);
		}
		usageProperties.setDateInstallation(product.getCreationDate().toString());
		usageProperties.setRefExterne(product.getRefExterne());
		String result = null;
		try {
			result = generateXmlUsageProperties(usageProperties);
			LOGGER.info("Generation of XML is done");
		} catch (JAXBException e) {
			LOGGER.error("The generation of XML isn't done", e);
			throw new DriverException(new ErrorCode("0.1.1.12.2"));
		}
		return result;
	}

	private String generateXmlUsageProperties(UsageProperties usageProperties) throws JAXBException {

		JAXBContext jaxbContext = JAXBContext.newInstance(UsageProperties.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		StringWriter sw = new StringWriter();
		jaxbMarshaller.marshal(usageProperties, sw);
		String xmlString = sw.toString();
		return xmlString;

	}

	private String generateXmlProductProperties(ProductProperties productProperties) throws JAXBException {

		JAXBContext jaxbContext = JAXBContext.newInstance(ProductProperties.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		StringWriter sw = new StringWriter();
		jaxbMarshaller.marshal(productProperties, sw);
		String xmlString = sw.toString();
		return xmlString;

	}

	@Override
	protected void performReactivate(Object arg0) throws DriverException, NotRespectedRulesException {
		LOGGER.info("Reactivate the product: " + this.getProviderProductId());
		try {
			this.productService.reactivateProduct(this.getProviderProductId());
		} catch (NNException e) {
			LOGGER.error(e);
			throw new DriverException(e);
		}
		LOGGER.info("The Reactivate is done");
	}

	protected void performReactivateManual(Object arg0, String ppid)
			throws DriverException, NotRespectedRulesException {
		LOGGER.info("Reactivate the product: " + ppid);
		try {
			this.productService.reactivateProduct(ppid);
		} catch (NNException e) {
			LOGGER.error(e);
			throw new DriverException(e);
		}
		LOGGER.info("The Reactivate is done");
	}

	@Override
	protected void performReset(Object arg0) throws NotRespectedRulesException, DriverException {
		throw new UnsupportedActionException("performReset action not supported for Manual driver.");
	}

	/*
	 * Getter && Setter.
	 */

	@Override
	protected void performSuspend(Object properties) throws DriverException, NotRespectedRulesException {
		LOGGER.info("Suspend the product: " + this.getProviderProductId());
		try {
			this.productService.suspendProductThroughPackager(this.getProviderProductId());
		} catch (NNException e) {
			LOGGER.error(e);
			throw new DriverException(e);
		}
		LOGGER.info("The suspend is done");
	}

	protected void performSuspendManual(Object properties, String ppid)
			throws DriverException, NotRespectedRulesException {
		LOGGER.info("Suspend the product: " + ppid);
		try {
			this.productService.suspendProductThroughPackager(ppid);
		} catch (NNException e) {
			LOGGER.error(e);
			throw new DriverException(e);
		}
		LOGGER.info("The suspend is done");
	}

	public Map<String, String> getSelfDiagnostics() throws DriverException {
		try {
			if (this.getDriverConfiguration() != null) {
				throw new DriverException(new ErrorCode("0.1.1.7.6"),
						new Object[] { this.getDriverConfiguration().getDriverKey() });
			}
		} catch (NotFoundException e) {
		}
		throw new DriverException(new ErrorCode("0.1.1.7.6"));
	}

	public FeasibilityTestResult isPropertiesChangePossible(String properties)
			throws DriverException, MalformedXMLException {

		Object parsedProperties = null;

		try {
			parsedProperties = ManualDriverFactory.parse(properties);
			return performIsPropertiesChangePossible(parsedProperties);
		} catch (Exception e) {
			FeasibilityTestResult result = new FeasibilityTestResult();
			result.setPossible(false);
			result.setMotive(e.getMessage());
			result.setExceptionCause(e);

			return result;
		}

	}

	public void changePropertiesManual(final String properties, String ppid) throws DriverException,
			MalformedXMLException, NotRespectedRulesException, SAXException, IOException, ParserConfigurationException {

		Object parsedProperties = null;

		try {
			parsedProperties = startChangePropertiesManual(properties, ppid);
			performChangePropertiesManual(parsedProperties, ppid);
		} catch (DriverException ex) {
			failChangePropertiesManual(properties, ex, ppid);
			throw ex;
		} catch (NotRespectedRulesException ex) {
			failChangePropertiesManual(properties, ex, ppid);
			throw ex;
		} catch (MalformedXMLException ex) {
			failChangePropertiesManual(properties, ex, ppid);
			throw ex;
		} catch (NullException ex) {
			failChangePropertiesManual(properties, ex, ppid);
			throw ex;
		}

		successChangePropertiesManual(properties, ppid);
	}

	protected void failChangePropertiesManual(final String properties, Exception cause, String ppid)
			throws DriverException {
		if (cause == null) {
			throw new NullException(NullCases.NULL, "cause");
		}
		String errorCode = "N/A";
		if (cause instanceof NNException) {
			errorCode = ((NNException) cause).getErrorCode().toString();
		} else {
			if (cause instanceof NNImplicitException) {
				errorCode = ((NNImplicitException) cause).getErrorCode().toString();
			}
		}

		this.getHistoricalReportLogger()
				.error("The properties of product " + this.getClass().getSimpleName() + " [" + ppid
						+ "] cannot be changed because of exception " + cause.getClass().getSimpleName() + "/"
						+ errorCode + ":" + cause.getMessage());
	}

	protected void successChangePropertiesManual(String properties, String ppid) throws DriverException {
		this.getHistoricalReportLogger().info("The properties of the product " + this.getClass().getSimpleName() + " ["
				+ ppid + "] have been successfully changed (new state=[" + this.getCurrentState(ppid) + "]).");
	}

	protected void performChangePropertiesManual(Object properties, String ppid)
			throws DriverException, NotRespectedRulesException {
		LOGGER.info("Change properties of the product: " + ppid);
		if (!(properties instanceof ProductProperties)) {
			throw new NotRespectedRulesException(new ErrorCode("1.2.1.1.5"), new Object[] { "productProperties",
					ProductProperties.class, properties.getClass().getSimpleName() });
		}
		Product product = null;
		try {
			product = this.productService.findByProviderProductId(ppid);
		} catch (NNException e1) {
			LOGGER.error(e1);
			throw new DriverException(e1);
		}
		ProductProperties productProperties = (ProductProperties) properties;

		if (productProperties.getHexacle() != null) {
			if (product.getCurrentHexacle() == null) {
				product.setCurrentHexacle(productProperties.getHexacle());
			} else {
				product.setPreviousHexacle(product.getCurrentHexacle());
				product.setCurrentHexacle(productProperties.getHexacle());
			}

			try {
				if (!product.getCurrentHexacle().equals(product.getPreviousHexacle())) {
					this.productService.changeProduct(product);
				}
			} catch (NNException e) {
				LOGGER.error(e);
				throw new DriverException(e);
			}
		}
		LOGGER.info("Properties changed successfully !");
	}

	protected Object startChangePropertiesManual(final String properties, String ppid) throws DriverException,
			MalformedXMLException, NotRespectedRulesException, SAXException, IOException, ParserConfigurationException {
		Object result = ManualDriverFactory.parse(properties);
		if (this.getCurrentState(ppid).equals(State.CANCELED)) {
			throw new NotRespectedRulesException(new ErrorCode("1.2.2.12"), new Object[] { ppid, State.CANCELED });
		}

		return result;
	}
}
