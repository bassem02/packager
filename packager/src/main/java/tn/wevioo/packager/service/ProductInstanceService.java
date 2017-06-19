package tn.wevioo.packager.service;

import java.util.List;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import nordnet.drivers.contract.exceptions.DriverException;
import tn.wevioo.packager.dto.product.ProductInstanceDTO;
import tn.wevioo.packager.dto.product.ProductPropertiesDTO;
import tn.wevioo.packager.entities.ProductInstance;
import tn.wevioo.packager.entities.ProductModel;
import tn.wevioo.packager.exceptions.RestTemplateException;

public interface ProductInstanceService extends CrudService<ProductInstance, Integer> {

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
