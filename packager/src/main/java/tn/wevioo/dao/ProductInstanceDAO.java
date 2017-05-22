package tn.wevioo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tn.wevioo.entities.ProductInstance;
import tn.wevioo.entities.ProductModel;

public interface ProductInstanceDAO extends JpaRepository<ProductInstance, Integer> {

	public ProductInstance findByProviderProductIdAndProductModel(String providerProductId, ProductModel productModel);

	@Query("SELECT MAX(idProductInstance) FROM ProductInstance")
	public long getMaxIdProductInstance();

}
