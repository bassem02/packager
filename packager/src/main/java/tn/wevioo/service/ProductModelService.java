package tn.wevioo.service;

import java.util.List;

import tn.wevioo.dto.product.ProductModelDTO;
import tn.wevioo.entities.PackagerModelProductModel;
import tn.wevioo.entities.ProductModel;

public interface ProductModelService {

	public ProductModel saveOrUpdate(ProductModel productModel);

	public void delete(ProductModel productModel);

	public ProductModel findById(int id);

	public ProductModel findByRetailerKey(String retailerKey);

	public List<ProductModel> findAll();

	public ProductModelDTO convertToDTO(PackagerModelProductModel config);

}
