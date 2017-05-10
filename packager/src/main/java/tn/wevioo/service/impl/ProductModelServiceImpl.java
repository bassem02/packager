package tn.wevioo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.implicit.NullException.NullCases;
import nordnet.architecture.exceptions.utils.ErrorCode;
import tn.wevioo.dao.ProductModelDao;
import tn.wevioo.dto.product.ProductModelDTO;
import tn.wevioo.entities.PackagerModelProductModel;
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
	public ProductModel findById(int id) throws NotFoundException {
		if (((Integer) id == null)) {
			throw new NullException(NullCases.NULL_EMPTY, "id parameter");
		}
		ProductModel result = productModelDao.findOne(id);

		if (result == null) {
			throw new NotFoundException(new ErrorCode("0.2.1.3.2"), new Object[] { "Product model", " id", id });
		}
		return result;
	}

	@Override
	public List<ProductModel> findAll() {
		return productModelDao.findAll();
	}

	@Override
	public ProductModel findByRetailerKey(String retailerKey) throws NotFoundException {

		if ((retailerKey == null) || (retailerKey.length() == 0)) {
			throw new NullException(NullCases.NULL_EMPTY, "retailerKey parameter");
		}

		ProductModel result = productModelDao.findByRetailerKey(retailerKey);

		if (result == null) {
			throw new NotFoundException(new ErrorCode("0.2.1.3.2"),
					new Object[] { "Product model", " retailerPackagerId", retailerKey });
		}

		return result;
	}

	@Override
	public ProductModelDTO convertToDTO(PackagerModelProductModel config) {

		ProductModelDTO fProductModelDTO = new ProductModelDTO();
		fProductModelDTO.setName(config.getProductModel().getRetailerKey());
		fProductModelDTO.setKey(config.getProductModel().getOldRetailerKey());
		fProductModelDTO.setMaximumInstances(config.getMaximumInstances());
		fProductModelDTO.setMinimumInstances(config.getMinimumInstances());

		return fProductModelDTO;
	}

}
