package tn.wevioo.driverManual.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nordnet.architecture.exceptions.explicit.DataSourceException;
import nordnet.architecture.exceptions.explicit.NotFoundException;
import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import nordnet.architecture.exceptions.explicit.WebServiceException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.implicit.NullException.NullCases;
import nordnet.architecture.exceptions.utils.ErrorCode;
import tn.wevioo.driverManual.dao.ProductDAO;
import tn.wevioo.driverManual.entities.EndUserHistory;
import tn.wevioo.driverManual.entities.Parameters;
import tn.wevioo.driverManual.entities.Product;
import tn.wevioo.driverManual.entities.ProductHistory;
import tn.wevioo.driverManual.entities.type.ActionEnum;
import tn.wevioo.driverManual.entities.type.StateProduct;
import tn.wevioo.driverManual.service.EndUserHistoryService;
import tn.wevioo.driverManual.service.ParametersService;
import tn.wevioo.driverManual.service.ProductHistoryService;
import tn.wevioo.driverManual.service.ProductService;
import tn.wevioo.driverManual.utils.AbstractValidator;
import tn.wevioo.driverManual.utils.Constants;
import tn.wevioo.driverManual.utils.Utils;

@Service("productService")
public class ProductServiceImpl implements ProductService {

	private static final Log LOGGER = LogFactory.getLog(ProductServiceImpl.class);

	@Autowired
	public ProductDAO productDAO;

	@Autowired
	public ParametersService parametersService;

	@Autowired
	public ProductHistoryService productHistoryService;

	@Autowired
	public EndUserHistoryService endUserHistoryService;

	@Override
	public Product saveOrUpdate(Product product) {
		return productDAO.saveAndFlush(product);
	}

	@Override
	public void delete(Product product) {
		productDAO.delete(product);
	}

	@Override
	public Product findById(long id) throws NotFoundException {

		if (((Long) id == null)) {
			throw new NullException(NullCases.NULL_EMPTY, "id parameter");
		}
		Product result = productDAO.findById(id);

		if (result == null) {
			throw new NotFoundException(new ErrorCode("0.2.1.3.2"), new Object[] { "Product history", " id", id });
		}
		return result;
	}

	@Override
	public List<Product> findAll() {
		return productDAO.findAll();
	}

	public StateProduct getCurrentStatus(final String providerProductId)
			throws NotRespectedRulesException, NotFoundException {
		if (AbstractValidator.isNullOrEmpty(providerProductId)) {
			throw new NotRespectedRulesException(new ErrorCode("0.2.1.1.3"), new Object[] { "providerProductId" });
		}
		Product product = productDAO.findByProviderProductId(providerProductId);
		if (product == null) {
			LOGGER.error("No Product has been found with the corresponding PPID [" + providerProductId + "].");
			throw new NotFoundException(new ErrorCode("0.2.1.3.1"), new Object[] { "Product", providerProductId });
		}
		return product.getState();
	}

	@Override
	public Product findByProviderProductId(String providerProductId)
			throws DataSourceException, NotFoundException, NotRespectedRulesException {

		if (AbstractValidator.isNullOrEmpty(providerProductId)) {
			throw new NotRespectedRulesException(new ErrorCode("0.2.1.1.3"), new Object[] { "providerProductId" });
		}
		Product product = productDAO.findByProviderProductId(providerProductId);
		if (product == null) {
			LOGGER.error("No Product has been found with the corresponding PPID [" + providerProductId + "].");
			throw new NotFoundException(new ErrorCode("0.2.1.3.1"), new Object[] { "Product", providerProductId });
		}
		return product;

	}

	@Override
	public void suspendProductThroughPackager(final String providerProductId)
			throws NotRespectedRulesException, DataSourceException, NotFoundException, WebServiceException {

		if (AbstractValidator.isNullOrEmpty(providerProductId)) {
			throw new NotRespectedRulesException(new ErrorCode("0.2.1.1.3"), new Object[] { "providerProductId" });
		}

		Product product = productDAO.findByProviderProductId(providerProductId);
		if (product == null) {
			LOGGER.error("No Product has been found with the corresponding PPID [" + providerProductId + "].");
			throw new NotFoundException(new ErrorCode("0.2.1.3.1"), new Object[] { "Product", providerProductId });
		}
		Parameters parameter = this.parametersService.findByActionAndTypeProduct(ActionEnum.SUSPEND,
				product.getTypeProduct());

		if (parameter == null) {
			throw new NotFoundException(new ErrorCode("0.2.1.3.2"), new Object[] { "parameter", "action/typeProduct",
					ActionEnum.SUSPEND.toString() + "/" + product.getTypeProduct() });
		}
		product.setState(StateProduct.SUSPENDED);
		this.productDAO.saveAndFlush(product);

		ProductHistory productHistory = new ProductHistory();

		productHistory.setDateChangementEtat(new Date());
		productHistory.setIdProduct(product.getId());
		productHistory.setState(StateProduct.SUSPENDED);
		productHistory.setUserChangementEtat(Constants.USER_DRIVER);
		this.productHistoryService.saveOrUpdate(productHistory);

		/*
		 * String mantisTitle = fillParameterValue(parameter.getMantisTitle(),
		 * product); String mantisDescription =
		 * fillParameterValue(parameter.getMantisDesription(), product);
		 * this.mantisWSClient.createMantis(mantisTitle, mantisDescription,
		 * parameter.getMantisProjectId(), parameter.getMantisCategory());
		 */
	}

	public void cancelProductTroughPackager(final String providerProductId)
			throws NotRespectedRulesException, DataSourceException, NotFoundException, WebServiceException {
		if (AbstractValidator.isNullOrEmpty(providerProductId)) {
			LOGGER.error("The { providerProductId } can not be null or empty.");
			throw new NotRespectedRulesException(new ErrorCode("0.2.1.1.3"), new Object[] { "providerProductId" });
		}

		Product product = this.productDAO.findByProviderProductId(providerProductId);
		if (product == null) {
			LOGGER.error("No Product has been found with the corresponding PPID [" + providerProductId + "].");
			throw new NotFoundException(new ErrorCode("0.2.1.3.1"), new Object[] { "Product", providerProductId });
		}

		Parameters parameter = this.parametersService.findByActionAndTypeProduct(ActionEnum.CANCEL,
				product.getTypeProduct());

		if (parameter == null) {
			throw new NotFoundException(new ErrorCode("0.2.1.3.2"), new Object[] { "parameter", "action/typeProduct",
					ActionEnum.CANCEL.toString() + "/" + product.getTypeProduct() });
		}

		product.setState(StateProduct.CANCELED);
		this.productDAO.saveAndFlush(product);

		ProductHistory productHistory = new ProductHistory();

		productHistory.setDateChangementEtat(new Date());
		productHistory.setIdProduct(product.getId());
		productHistory.setState(StateProduct.CANCELED);
		productHistory.setUserChangementEtat(Constants.USER_DRIVER);

		this.productHistoryService.saveOrUpdate(productHistory);

		/*
		 * String mantisTitle = fillParameterValue(parameter.getMantisTitle(),
		 * product); String mantisDescription =
		 * fillParameterValue(parameter.getMantisDesription(), product);
		 * 
		 * this.mantisWSClient.createMantis(mantisTitle, mantisDescription,
		 * parameter.getMantisProjectId(), parameter.getMantisCategory());
		 */

	}

	public void changeProduct(final Product prod)
			throws DataSourceException, NotFoundException, NotRespectedRulesException, WebServiceException {
		if (AbstractValidator.isNullOrEmpty(prod)) {
			throw new NotRespectedRulesException(new ErrorCode("0.2.1.1.3"), new Object[] { "product" });
		}

		Parameters parameter = this.parametersService.findByActionAndTypeProduct(ActionEnum.CHANGEPROPERTIES,
				prod.getTypeProduct());

		if (parameter == null) {
			throw new NotFoundException(new ErrorCode("0.2.1.3.2"), new Object[] { "parameter", "action/typeProduct",
					ActionEnum.CHANGEPROPERTIES.toString() + "/" + prod.getTypeProduct() });
		}
		String oldEndUserId = prod.getEndUserId();
		String newEndUserId = this.getEndUserId();
		prod.setEndUserId(newEndUserId);

		this.productDAO.saveAndFlush(prod);

		EndUserHistory endUserHistory = new EndUserHistory();
		endUserHistory.setCreationDate(new Date());
		endUserHistory.setNewEndUserId(newEndUserId);
		endUserHistory.setOldEndUserId(oldEndUserId);
		endUserHistory.setProviderProductId(prod.getProviderProductId());

		this.endUserHistoryService.saveOrUpdate(endUserHistory);

		/*
		 * String mantisTitle = fillParameterValue(parameter.getMantisTitle(),
		 * prod); String mantisDescription =
		 * fillParameterValue(parameter.getMantisDesription(), prod);
		 * 
		 * this.mantisWSClient.createMantis(mantisTitle, mantisDescription,
		 * parameter.getMantisProjectId(), parameter.getMantisCategory());
		 */

	}

	public String getEndUserId() throws DataSourceException {
		String enduserId = Utils.getIdentifer();

		boolean exist = true;
		do {
			Product product = this.productDAO.findByEndUserId(enduserId.toString());
			if (product != null) {
				enduserId = Utils.getIdentifer();
			} else {
				exist = false;
			}
		} while (exist);
		return enduserId;

	}

	public void reactivateProduct(String providerProductId)
			throws NotFoundException, NotRespectedRulesException, DataSourceException, WebServiceException {
		if (AbstractValidator.isNullOrEmpty(providerProductId)) {
			LOGGER.error("The {providerProductId} can not be null or empty.");
			throw new NotRespectedRulesException(new ErrorCode("0.2.1.1.3"), new Object[] { "providerProductId" });
		}
		Product product = this.productDAO.findByProviderProductId(providerProductId);
		if (product == null) {
			LOGGER.error("No Product has been found with the corresponding PPID [" + providerProductId + "].");
			throw new NotFoundException(new ErrorCode("0.2.1.3.1"), new Object[] { "Product", providerProductId });
		}

		String oldEndUserId = product.getEndUserId();
		String endUserId = this.getEndUserId();
		product.setState(StateProduct.ACTIVE);
		product.setEndUserId(endUserId);
		this.productDAO.saveAndFlush(product);

		EndUserHistory endUserHistory = new EndUserHistory();
		endUserHistory.setCreationDate(new Date());
		endUserHistory.setNewEndUserId(endUserId);
		endUserHistory.setOldEndUserId(oldEndUserId);
		endUserHistory.setProviderProductId(product.getProviderProductId());

		this.endUserHistoryService.saveOrUpdate(endUserHistory);

		ProductHistory productHistory = new ProductHistory();
		productHistory.setDateChangementEtat(new Date());
		productHistory.setIdProduct(product.getId());
		productHistory.setState(StateProduct.ACTIVE);
		productHistory.setUserChangementEtat(Constants.USER_DRIVER);
		this.productHistoryService.saveOrUpdate(productHistory);
		Parameters parameter = this.parametersService.findByActionAndTypeProduct(ActionEnum.REACTIVATE,
				product.getTypeProduct());

		// if (parameter != null) {
		// String mantisTitle = fillParameterValue(parameter.getMantisTitle(),
		// product);
		// String mantisDescription =
		// fillParameterValue(parameter.getMantisDesription(), product);
		// this.mantisWSClient.createMantis(mantisTitle, mantisDescription,
		// parameter.getMantisProjectId(),
		// parameter.getMantisCategory());
		// }

	}

	@Override
	public Product createProduct(Product product)
			throws DataSourceException, NotFoundException, NotRespectedRulesException, WebServiceException {
		if (AbstractValidator.isNullOrEmpty(product)) {
			throw new NotRespectedRulesException(new ErrorCode("0.2.1.1.3"), new Object[] { "product" });
		}

		Parameters parameter = this.parametersService.findByActionAndTypeProduct(ActionEnum.CREATE,
				product.getTypeProduct());

		if (parameter == null) {
			throw new NotFoundException(new ErrorCode("0.2.1.3.2"), new Object[] { "parameter", "action/typeProduct",
					ActionEnum.CREATE.toString() + "/" + product.getTypeProduct() });
		}
		String endUserid = this.getEndUserId();
		product.setCreationDate(new Date());
		product.setState(StateProduct.INPROGRESS);
		product.setEndUserId(endUserid);
		product = this.productDAO.saveAndFlush(product);

		ProductHistory productHistory = new ProductHistory();
		productHistory.setDateChangementEtat(new Date());
		productHistory.setIdProduct(product.getId());
		productHistory.setState(StateProduct.INPROGRESS);
		productHistory.setUserChangementEtat(Constants.USER_DRIVER);

		this.productHistoryService.saveOrUpdate(productHistory);
		// String mantisTitle = fillParameterValue(parameter.getMantisTitle(),
		// product);
		// String mantisDescription =
		// fillParameterValue(parameter.getMantisDesription(), product);
		// this.mantisWSClient.createMantis(mantisTitle, mantisDescription,
		// parameter.getMantisProjectId(),
		// parameter.getMantisCategory());
		return product;
	}

	@Override
	public boolean validateCreation(final String typeProduct)
			throws DataSourceException, NotFoundException, NotRespectedRulesException {
		if (AbstractValidator.isNullOrEmpty(typeProduct)) {
			throw new NotRespectedRulesException(new ErrorCode("0.2.1.1.3"), new Object[] { "typeProduct" });
		}

		Parameters parameter = this.parametersService.findByActionAndTypeProduct(ActionEnum.CREATE, typeProduct);
		if (parameter == null) {
			throw new NotFoundException(new ErrorCode("0.2.1.3.2"), new Object[] { "parameter", "action/typeProduct",
					ActionEnum.CREATE.toString() + "/" + typeProduct });
		}
		return true;
	}

	@Override
	public void activateProductManual(String providerProductId)
			throws NotFoundException, NotRespectedRulesException, DataSourceException {

		if (AbstractValidator.isNullOrEmpty(providerProductId)) {
			LOGGER.error("The {providerProductId} can not be null or empty.");
			throw new NotRespectedRulesException(new ErrorCode("0.2.1.1.3"), new Object[] { "providerProductId" });
		}
		Product product = productDAO.findByProviderProductId(providerProductId);
		if (product == null) {
			LOGGER.error("No Product has been found with the corresponding PPID [" + providerProductId + "].");
			throw new NotFoundException(new ErrorCode("0.2.1.3.1"), new Object[] { "Product", providerProductId });
		}

		product.setState(StateProduct.ACTIVE);
		productDAO.saveAndFlush(product);

		ProductHistory productHistory = new ProductHistory();
		productHistory.setDateChangementEtat(new Date());
		productHistory.setIdProduct(product.getId());
		productHistory.setState(StateProduct.ACTIVE);
		productHistory.setUserChangementEtat(Constants.USER_DRIVER);

		productHistoryService.saveOrUpdate(productHistory);
	}

}
