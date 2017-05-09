package tn.wevioo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.wevioo.entities.ProductModelProductDriverPort;

public interface ProductModelProductDriverPortDAO extends JpaRepository<ProductModelProductDriverPort, Integer> {

	public ProductModelProductDriverPort findByProductModel(String productModel);
}
