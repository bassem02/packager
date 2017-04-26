package tn.wevioo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.wevioo.dao.ProductHistoryDAO;
import tn.wevioo.entities.ProductHistory;
import tn.wevioo.service.ProductHistoryService;

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
	public ProductHistory findById(int id) {
		return productHistoryDAO.findOne(id);
	}

	@Override
	public List<ProductHistory> findAll() {
		return productHistoryDAO.findAll();
	}

}
