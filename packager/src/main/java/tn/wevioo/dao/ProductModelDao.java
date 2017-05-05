package tn.wevioo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.wevioo.entities.ProductModel;

public interface ProductModelDao extends JpaRepository<ProductModel, Integer> {
	
	public ProductModel findByRetailerKey(String retailerKey);
	
}
