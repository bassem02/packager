package tn.wevioo.service;

import java.util.List;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import tn.wevioo.dto.product.ProductModelDTO;
import tn.wevioo.entities.PackagerModelProductModel;
import tn.wevioo.entities.ProductModel;

public interface ProductModelService {

	public ProductModel saveOrUpdate(ProductModel productModel);

	public void delete(ProductModel productModel);

	public ProductModel findById(int id) throws NotFoundException;

	public ProductModel findByRetailerKey(String retailerKey) throws NotFoundException;

	public List<ProductModel> findAll();

	public ProductModelDTO convertToDTO(PackagerModelProductModel config);

}
