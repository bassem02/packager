package tn.wevioo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nordnet.architecture.exceptions.NNException;
import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import nordnet.architecture.exceptions.implicit.UnsupportedActionException;
import nordnet.architecture.exceptions.utils.ErrorCode;
import nordnet.drivers.contract.exceptions.DriverException;
import nordnet.drivers.contract.impl.ProductDriverImpl;
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

	@Autowired
	private ProductService productService;

	protected ManualDriver() throws DriverException {
		super();
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

		// try {
		//
		// return
		// super.getDriverToolFactory().getObjectConverter().convert(status.toString(),
		// State.class);
		return State.valueOf(status.toString());
		// } catch (ConverterException e) {
		// LOGGER.error("The mapping isn't done", e);
		// throw new DriverException(e);
		// }
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
		// try {
		// productProperties =
		// super.getDriverToolFactory().getObjectConverter().convert(product,
		// ProductProperties.class);
		productProperties.setHexacle(product.getCurrentHexacle());
		productProperties.setIdClient(product.getIdClientInterne());
		productProperties.setInfoCompl(product.getInfoCompl());
		productProperties.setTypeProduct(product.getTypeProduct());
		// } catch (ConverterException e) {
		// LOGGER.error("The mapping isn't done", e);
		// throw new DriverException(e);
		// }
		String xml = null;
		try {
			xml = super.generateXmlProperties(productProperties);
		} catch (NotRespectedRulesException e) {
			throw new DriverException(e);
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

	public List<tn.wevioo.tools.Reference> getReference() throws DriverException {
		LOGGER.info("Get product references for the product: " + this.getProviderProductId());

		try {
			this.productService.findByProviderProductId(this.getProviderProductId());
		} catch (NNException e1) {
			LOGGER.error(e1);
			throw new DriverException(e1);
		}
		List<tn.wevioo.tools.Reference> references = new ArrayList<tn.wevioo.tools.Reference>();
		references.add(new tn.wevioo.tools.Reference(ReferenceKeys.PROVIDER_PRODUCT_ID, this.getProviderProductId()));
		references.add(new tn.wevioo.tools.Reference(ReferenceKeys.DISCRIMINATOR, this.getProviderProductId()));
		LOGGER.info("Product references returned successfully !");

		return references;
	}

	@Override
	protected void performActivate(Object properties) throws DriverException, NotRespectedRulesException {
		LOGGER.info("Activate the product: " + this.getProviderProductId());
		return;
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

		// try {
		// usageProperties =
		// super.getDriverToolFactory().getObjectConverter().convert(product,
		// UsageProperties.class);
		usageProperties.setDateInstallation(product.getCreationDate().toString());
		usageProperties.setRefExterne(product.getRefExterne());
		// } catch (ConverterException e) {
		// LOGGER.error("The mapping isn't done", e);
		// throw new DriverException(e);
		// }

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

}
