package tn.wevioo.service;

import java.util.List;

import tn.wevioo.entities.ProductModelProductDriverPort;

public interface ProductModelProductDriverPortService {

	public ProductModelProductDriverPort saveOrUpdate(ProductModelProductDriverPort productModelProductDriverPort);

	public void delete(ProductModelProductDriverPort productModelProductDriverPort);

	public ProductModelProductDriverPort findById(int id);

	public List<ProductModelProductDriverPort> findAll();

	public ProductModelProductDriverPort findByProductModel(String productModel);

}
