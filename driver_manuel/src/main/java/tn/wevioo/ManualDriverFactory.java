package tn.wevioo;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import nordnet.architecture.exceptions.explicit.DataSourceException;
import nordnet.architecture.exceptions.explicit.MalformedXMLException;
import nordnet.architecture.exceptions.explicit.NotFoundException;
import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import nordnet.architecture.exceptions.explicit.WebServiceException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.implicit.NullException.NullCases;
import nordnet.architecture.exceptions.utils.ErrorCode;
import nordnet.drivers.contract.exceptions.DriverException;
import nordnet.drivers.contract.impl.ProductDriverFactoryImpl;
import nordnet.drivers.contract.types.Action;
import nordnet.drivers.contract.types.FeasibilityTestResult;
import nordnet.tools.idgenerator.algorithms.stringcomputing.UUIDGenerator;
import nordnet.tools.idgenerator.exception.AlgorithmException;
import tn.wevioo.configuration.ManualConfiguration;
import tn.wevioo.configuration.ManualInternalConfiguration;
import tn.wevioo.entities.Product;
import tn.wevioo.properties.ProductProperties;
import tn.wevioo.service.ProductService;
import tn.wevioo.utils.AbstractValidator;

/**
 * This class implements all methods useful to manage instance of the Manual
 * product.
 * 
 * @author kad
 * 
 */
// @RestController
@Component
public class ManualDriverFactory
		extends ProductDriverFactoryImpl<ManualDriver, ManualConfiguration, ManualInternalConfiguration> {

	/**
	 * {@link ManualDriverFactory}'s Logger.
	 */

	private static final Log LOGGER = LogFactory.getLog(ManualDriverFactory.class);

	/**
	 * Injected bean ProductService.
	 */

	@Autowired
	private ProductService productService;

	/**
	 * Default constructor.
	 * 
	 * @throws DriverException
	 *             custom exception.
	 */
	protected ManualDriverFactory() throws DriverException {
		super();
	}

	/**
	 * {@inheritDoc}
	 */

	@Autowired
	ManualDriver createdProdDriver;

	public ManualDriver findProduct(String providerProductId) throws DriverException, NotFoundException {

		if (AbstractValidator.isNullOrEmpty(providerProductId)) {
			throw new NullException(NullCases.NULL_EMPTY, "providerProductId");
		}

		try {
			this.productService.findByProviderProductId(providerProductId);
		} catch (DataSourceException e1) {
			LOGGER.error(e1);
			throw new DriverException(e1);
		} catch (NotRespectedRulesException e1) {
			LOGGER.error(e1);
			throw new DriverException(e1);
		} catch (NotFoundException e) {
			LOGGER.error(e);
			throw new DriverException(e);
		}

		try {
			createdProdDriver.setProviderProductId(providerProductId);
		} catch (NotRespectedRulesException e) {
			LOGGER.error(e);
			throw new DriverException(new ErrorCode("0.2.1.1.1"), e);
		}

		LOGGER.info("Returning result...");
		return createdProdDriver;

	}

	public ProductService getProductService() {
		return this.productService;
	}

	/**
	 * {@inheritDoc}
	 */

	@Autowired
	ManualDriver createdDriver;

	UUIDGenerator idGenerator = new UUIDGenerator();

	@Override
	protected ManualDriver performCreateProduct(Object properties) throws DriverException, NotRespectedRulesException {

		if (!(properties instanceof ProductProperties)) {
			throw new NotRespectedRulesException(new ErrorCode("1.2.1.1.5"), new Object[] { "productProperties",
					ProductProperties.class, properties.getClass().getSimpleName() });
		}
		ProductProperties productProperties = (ProductProperties) properties;
		String ref = null;

		// ref = "ref";
		try {
			ref = idGenerator.generateIdentifier(null);
		} catch (AlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Product product = new Product();
		// try {
		// product =
		// super.getDriverToolFactory().getObjectConverter().convert(productProperties,
		// Product.class);
		product.setCurrentHexacle(productProperties.getHexacle());
		product.setIdClientInterne(productProperties.getIdClient());
		product.setInfoCompl(productProperties.getInfoCompl());
		product.setTypeProduct(productProperties.getTypeProduct());
		// } catch (ConverterException e) {
		// LOGGER.error("The mapping isn't done", e);
		// throw new DriverException(e);
		// }
		LOGGER.info("Provider product id generated " + ref + " ...");
		product.setProviderProductId(ref);

		try {
			this.productService.createProduct(product);
		} catch (DataSourceException e) {
			LOGGER.error(e);
			throw new DriverException(e);
		} catch (NotFoundException e) {
			LOGGER.error(e);
			throw new DriverException(e);
		} catch (WebServiceException e) {
			LOGGER.error(e);
			throw new DriverException(e);
		}

		createdDriver.setProviderProductId(ref);
		LOGGER.info("ManualDriver product created !");

		return createdDriver;

		// createdProdDriver.setProviderProductId(ref);
		// LOGGER.info("ManualDriver product created !");
		//
		// return createdProdDriver;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected FeasibilityTestResult performTestFeasibilityForProductCreation(Object properties) throws DriverException {

		LOGGER.info("Testing if creation of a new Manuel product is possible...");
		FeasibilityTestResult result = new FeasibilityTestResult();

		if (!(properties instanceof ProductProperties)) {
			throw new DriverException(new ErrorCode("1.2.1.1.5"), new Object[] { "productProperties",
					ProductProperties.class, properties.getClass().getSimpleName() });
		}
		ProductProperties productProperties = (ProductProperties) properties;

		try {
			if (this.productService.validateCreation(productProperties.getTypeProduct())) {
				result.setPossible(true);
				result.setMotive(null);
				return result;
			}
		} catch (DataSourceException e) {
			result.setPossible(false);
			result.setMotive("Error occurred while trying to obtain the corresponding parameter with type["
					+ productProperties.getTypeProduct() + "]" + e.getMessage());

			return result;
		} catch (NotFoundException e) {
			result.setPossible(false);
			result.setMotive("Error occurred while trying to obtain the corresponding parameter with type["
					+ productProperties.getTypeProduct() + "] and action [ CREATE]" + e.getMessage());

			return result;
		} catch (NotRespectedRulesException e) {
			result.setPossible(false);
			result.setMotive("Error occurred while trying to obtain the corresponding parameter with type["
					+ productProperties.getTypeProduct() + "] and action [ CREATE]" + e.getMessage());
			return result;
		}

		return result;

	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public ManualDriver createProduct(String properties)
			throws DriverException, MalformedXMLException, NotRespectedRulesException {

		ManualDriver createdProduct = null;
		try {
			// Object parsedProperties = startCreateProduct(properties);
			Object parsedProperties = parse(properties);
			createdProduct = performCreateProduct(parsedProperties);
		} catch (DriverException ex) {
			failCreateProduct(properties, ex);
			throw ex;
		} catch (NotRespectedRulesException ex) {
			failCreateProduct(properties, ex);
			throw ex;
			// } catch (MalformedXMLException ex) {
			// failCreateProduct(properties, ex);
			// throw ex;
		} catch (NullException ex) {
			failCreateProduct(properties, ex);
			throw ex;
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		successCreateProduct(createdProduct, properties);

		return createdProduct;
	}

	static Object parse(String properties) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(properties));

		ProductProperties productProperties = new ProductProperties();

		Document doc = db.parse(is);

		NodeList node = doc.getElementsByTagName("manual:productProperties");
		// NodeList node = doc.getElementsByTagName("productProperties");
		Element element = (Element) node.item(0);

		NodeList hexacleNode = element.getElementsByTagName("manual:hexacle");
		Element hexacle = (Element) hexacleNode.item(0);

		productProperties.setHexacle(getCharacterDataFromElement(hexacle));

		NodeList idClientNode = element.getElementsByTagName("manual:idClient");
		Element idClient = (Element) idClientNode.item(0);

		productProperties.setIdClient(getCharacterDataFromElement(idClient));

		NodeList infoComplNode = element.getElementsByTagName("manual:infoCompl");
		Element infoCompl = (Element) infoComplNode.item(0);

		productProperties.setInfoCompl(getCharacterDataFromElement(infoCompl));

		NodeList typeProductNode = element.getElementsByTagName("manual:typeProduct");
		Element typeProduct = (Element) typeProductNode.item(0);

		productProperties.setTypeProduct(getCharacterDataFromElement(typeProduct));

		return productProperties;
	}

	public static String getCharacterDataFromElement(Element e) {
		Node child = e.getFirstChild();
		if (child instanceof CharacterData) {
			CharacterData cd = (CharacterData) child;
			return cd.getData();
		}
		return "";
	}

	public void verifyXmlPropertiesManual(final Action action, final String properties)
			throws MalformedXMLException, DriverException, SAXException, IOException, ParserConfigurationException {
		this.validateAndParseXmlPropertiesManual(action, properties);
	}

	protected Object validateAndParseXmlPropertiesManual(final Action action, final String properties)
			throws MalformedXMLException, DriverException, SAXException, IOException, ParserConfigurationException {
		if (action == null) {
			throw new NullException(NullCases.NULL, "action");
		}
		switch (action) {
		case CREATE_PRODUCT:
			validateXmlPropertiesParameter(properties,
					getDriverInternalConfiguration().areCreateProductPropertiesRequired());
			break;
		case ACTIVATE:
			validateXmlPropertiesParameter(properties,
					getDriverInternalConfiguration().areActivatePropertiesRequired());
			break;
		case REACTIVATE:
			validateXmlPropertiesParameter(properties,
					getDriverInternalConfiguration().areReactivatePropertiesRequired());
			break;
		case SUSPEND:
			validateXmlPropertiesParameter(properties, getDriverInternalConfiguration().areSuspendPropertiesRequired());
			break;
		case CANCEL:
			validateXmlPropertiesParameter(properties, getDriverInternalConfiguration().areCancelPropertiesRequired());
			break;
		case CHANGE_PROPERTIES:
		case TRANSFORMATION:
			validateXmlPropertiesParameter(properties, true);
			break;
		case DELETE:
			validateXmlPropertiesParameter(properties, getDriverInternalConfiguration().areDeletePropertiesRequired());
			break;
		case RESET:
			validateXmlPropertiesParameter(properties, getDriverInternalConfiguration().areResetPropertiesRequired());
			break;
		case GET_USAGE_PROPERTIES:
			validateXmlPropertiesParameter(properties, false);
			break;
		default:
			throw new UnsupportedOperationException("The action " + action + " is not supported.");
		}

		if (properties != null) {
			return parse(properties);
		}
		return null;
	}

	public ManualInternalConfiguration getDriverInternalConfiguration() throws DriverException {
		return new ManualInternalConfiguration();
	}

	/*
	 * Getter && Setter.
	 */

}
