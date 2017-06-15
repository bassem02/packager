package tn.wevioo.packager.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.wevioo.packager.entities.ProductModel;

public interface ProductModelDao extends JpaRepository<ProductModel, Integer> {
	
	public ProductModel findByRetailerKey(String retailerKey);
	
}
