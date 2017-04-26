package tn.wevioo.service;

import java.util.List;

import tn.wevioo.entities.ProductHistory;

public interface ProductHistoryService {

	ProductHistory saveOrUpdate(ProductHistory productHistory);

	void delete(ProductHistory productHistory);

	ProductHistory findById(int id);

	List<ProductHistory> findAll();

}
