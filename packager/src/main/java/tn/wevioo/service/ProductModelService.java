package tn.wevioo.service;

import java.util.List;

import tn.wevioo.entities.PackagerModelProductModel;
import tn.wevioo.entities.ProductModel;
import tn.wevioo.facade.product.FProductModel;

public interface ProductModelService {

	public ProductModel saveOrUpdate(ProductModel productModel);

	public void delete(ProductModel productModel);

	public ProductModel findById(int id);

	public ProductModel findByRetailerKey(String retailerKey);

	public List<ProductModel> findAll();

	public FProductModel convertToDTO(PackagerModelProductModel config);

}
