package tn.wevioo.packager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.implicit.NullException.NullCases;
import nordnet.architecture.exceptions.utils.ErrorCode;
import tn.wevioo.packager.dao.ProductModelProductDriverPortDAO;
import tn.wevioo.packager.entities.ProductModelProductDriverPort;
import tn.wevioo.packager.service.ProductModelProductDriverPortService;

@Service("productModelProductDriverPortService")
public class ProductModelProductDriverPortServiceImpl implements ProductModelProductDriverPortService {

	@Autowired
	public ProductModelProductDriverPortDAO productModelProductDriverPortDAO;

	@Override
	public ProductModelProductDriverPort saveOrUpdate(ProductModelProductDriverPort productModelProductDriverPort) {

		return productModelProductDriverPortDAO.saveAndFlush(productModelProductDriverPort);
	}

	@Override
	public void delete(ProductModelProductDriverPort productModelProductDriverPort) {

		productModelProductDriverPortDAO.delete(productModelProductDriverPort);
	}

	@Override
	public ProductModelProductDriverPort findById(Integer id) throws NotFoundException {

		if (((Integer) id == null)) {
			throw new NullException(NullCases.NULL_EMPTY, "id parameter");
		}

		ProductModelProductDriverPort result = productModelProductDriverPortDAO.findOne(id);

		if (result == null) {
			throw new NotFoundException(new ErrorCode("0.2.1.3.2"),
					new Object[] { "Product Model Product Driver Port", " id", id });
		}
		return result;
	}

	@Override
	public List<ProductModelProductDriverPort> findAll() {

		return productModelProductDriverPortDAO.findAll();
	}

	@Override
	public ProductModelProductDriverPort findByProductModel(String productModel) throws NotFoundException {

		if ((productModel == null) || (productModel.length() == 0)) {
			throw new NullException(NullCases.NULL_EMPTY, "product model parameter");
		}

		ProductModelProductDriverPort result = productModelProductDriverPortDAO.findByProductModel(productModel);

		if (result == null) {
			throw new NotFoundException(new ErrorCode("0.2.1.3.2"),
					new Object[] { "Product Model Product Driver Port", " productModel", productModel });
		}

		return result;

	}

}
