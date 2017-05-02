package tn.wevioo.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.wevioo.dao.ProductModelDao;
import tn.wevioo.dto.ProductModelDTO;
import tn.wevioo.entities.ProductModel;
import tn.wevioo.service.ProductModelService;

@Service("productModelService")
public class ProductModelServiceImpl implements ProductModelService {

	@Autowired
	public ProductModelDao productModelDao;

	@Override
	public ProductModel saveOrUpdate(ProductModel productModel) {
		return productModelDao.save(productModel);
	}

	@Override
	public void delete(ProductModel productModel) {
		productModelDao.delete(productModel);
	}

	@Override
	public ProductModel findById(int id) {
		return productModelDao.findOne(id);
	}

	@Override
	public List<ProductModel> findAll() {
		return productModelDao.findAll();
	}

	@Override
	public ProductModel findByRetailerKey(String retailerKey) {
		return productModelDao.findByRetailerKey(retailerKey);
	}

	@Override
	public ProductModelDTO convertToDTO(ProductModel productModel) {
		ProductModelDTO productModelDTO = new ProductModelDTO();
		BeanUtils.copyProperties(productModel, productModelDTO);
		return productModelDTO;

	}
}
