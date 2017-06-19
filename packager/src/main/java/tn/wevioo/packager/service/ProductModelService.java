package tn.wevioo.packager.service;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import tn.wevioo.packager.dto.product.ProductModelDTO;
import tn.wevioo.packager.entities.PackagerModelProductModel;
import tn.wevioo.packager.entities.ProductModel;

public interface ProductModelService extends CrudService<ProductModel, Integer> {

	public ProductModel findByRetailerKey(String retailerKey) throws NotFoundException;

	public ProductModelDTO convertToDTO(PackagerModelProductModel config);

}
