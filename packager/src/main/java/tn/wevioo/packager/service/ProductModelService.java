package tn.wevioo.packager.service;

import java.util.List;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import tn.wevioo.packager.dto.product.ProductModelDTO;
import tn.wevioo.packager.entities.PackagerModelProductModel;
import tn.wevioo.packager.entities.ProductModel;

public interface ProductModelService {

	public ProductModel saveOrUpdate(ProductModel productModel);

	public void delete(ProductModel productModel);

	public ProductModel findById(int id) throws NotFoundException;

	public ProductModel findByRetailerKey(String retailerKey) throws NotFoundException;

	public List<ProductModel> findAll();

	public ProductModelDTO convertToDTO(PackagerModelProductModel config);

}
