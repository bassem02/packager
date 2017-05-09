package tn.wevioo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.wevioo.dao.ProductModelProductDriverPortDAO;
import tn.wevioo.entities.ProductModelProductDriverPort;
import tn.wevioo.service.ProductModelProductDriverPortService;

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
	public ProductModelProductDriverPort findById(int id) {

		return productModelProductDriverPortDAO.findOne(id);
	}

	@Override
	public List<ProductModelProductDriverPort> findAll() {

		return productModelProductDriverPortDAO.findAll();
	}

	@Override
	public ProductModelProductDriverPort findByProductModel(String productModel) {

		return productModelProductDriverPortDAO.findByProductModel(productModel);
	}

}
