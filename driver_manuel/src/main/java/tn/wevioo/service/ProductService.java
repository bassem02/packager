package tn.wevioo.service;

import java.util.List;

import nordnet.architecture.exceptions.explicit.DataSourceException;
import nordnet.architecture.exceptions.explicit.NotFoundException;
import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import nordnet.architecture.exceptions.explicit.WebServiceException;
import tn.wevioo.entities.Product;
import tn.wevioo.entities.type.StateProduct;

public interface ProductService {

	Product saveOrUpdate(Product product);

	void delete(Product product);

	Product findById(long id);

	List<Product> findAll();

	StateProduct getCurrentStatus(String providerProductId) throws NotRespectedRulesException, NotFoundException;

	Product findByProviderProductId(String providerProductId)
			throws DataSourceException, NotFoundException, NotRespectedRulesException;

	void suspendProductThroughPackager(String providerProductId)
			throws NotRespectedRulesException, DataSourceException, NotFoundException, WebServiceException;

	void cancelProductTroughPackager(String providerProductId)
			throws NotRespectedRulesException, DataSourceException, NotFoundException, WebServiceException;

	void changeProduct(Product prod)
			throws DataSourceException, NotFoundException, NotRespectedRulesException, WebServiceException;

	void reactivateProduct(String providerProductId)
			throws NotFoundException, NotRespectedRulesException, DataSourceException, WebServiceException;

	Product createProduct(Product product)
			throws DataSourceException, NotFoundException, NotRespectedRulesException, WebServiceException;

	boolean validateCreation(String typeProduct)
			throws DataSourceException, NotFoundException, NotRespectedRulesException;

}
