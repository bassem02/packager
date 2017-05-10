package tn.wevioo.service;

import java.util.List;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import tn.wevioo.entities.ProductHistory;

public interface ProductHistoryService {

	ProductHistory saveOrUpdate(ProductHistory productHistory);

	void delete(ProductHistory productHistory);

	ProductHistory findById(int id) throws NotFoundException;

	List<ProductHistory> findAll();

}
