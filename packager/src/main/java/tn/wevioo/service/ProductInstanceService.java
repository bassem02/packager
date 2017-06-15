package tn.wevioo.service;

import java.util.List;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import nordnet.drivers.contract.exceptions.DriverException;
import tn.wevioo.dto.product.ProductInstanceDTO;
import tn.wevioo.dto.product.ProductPropertiesDTO;
import tn.wevioo.entities.ProductInstance;
import tn.wevioo.entities.ProductModel;
import tn.wevioo.exceptions.RestTemplateException;

public interface ProductInstanceService {

	public ProductInstance saveOrUpdate(ProductInstance productInstance);

	public void delete(ProductInstance productInstance);

	public ProductInstance findById(int id) throws NotFoundException;

	public List<ProductInstance> findAll();

	public ProductInstanceDTO convertToDTO(ProductInstance findById)
			throws DriverException, RestTemplateException, NotFoundException;

	ProductPropertiesDTO convertToPropertiesDTO(ProductInstance productInstance)
			throws DriverException, RestTemplateException, NotFoundException;

	public List<ProductInstance> findByProviderProductIds(List<String> subList, ProductModel productModel);

	public ProductInstance findByProviderProductId(String providerProductId, ProductModel productModel)
			throws NotFoundException;

	public long getMaxIdProductInstance();

	public ProductPropertiesDTO convertToProductPropertiesDTO(ProductInstance productInstance)
			throws DriverException, NotFoundException, RestTemplateException;

}
