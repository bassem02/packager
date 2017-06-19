package tn.wevioo.driverManual.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.implicit.NullException.NullCases;
import nordnet.architecture.exceptions.utils.ErrorCode;
import tn.wevioo.driverManual.dao.ProductHistoryDAO;
import tn.wevioo.driverManual.entities.ProductHistory;
import tn.wevioo.driverManual.service.ProductHistoryService;

@Service("productHistoryService")
public class ProductHistoryServiceImpl implements ProductHistoryService {

	@Autowired
	public ProductHistoryDAO productHistoryDAO;

	@Override
	public ProductHistory saveOrUpdate(ProductHistory productHistory) {
		return productHistoryDAO.saveAndFlush(productHistory);
	}

	@Override
	public void delete(ProductHistory productHistory) {
		productHistoryDAO.delete(productHistory);

	}

	@Override
	public ProductHistory findById(Integer id) throws NotFoundException {
		if (((Integer) id == null)) {
			throw new NullException(NullCases.NULL_EMPTY, "id parameter");
		}
		ProductHistory result = productHistoryDAO.findOne(id);

		if (result == null) {
			throw new NotFoundException(new ErrorCode("0.2.1.3.2"), new Object[] { "Product history", " id", id });
		}
		return result;
	}

	@Override
	public List<ProductHistory> findAll() {
		return productHistoryDAO.findAll();
	}

}
