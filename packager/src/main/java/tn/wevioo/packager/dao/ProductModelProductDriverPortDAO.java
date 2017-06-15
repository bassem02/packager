package tn.wevioo.packager.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.wevioo.packager.entities.ProductModelProductDriverPort;

public interface ProductModelProductDriverPortDAO extends JpaRepository<ProductModelProductDriverPort, Integer> {

	public ProductModelProductDriverPort findByProductModel(String productModel);
}
