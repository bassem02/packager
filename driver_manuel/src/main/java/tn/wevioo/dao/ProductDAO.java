package tn.wevioo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.wevioo.entities.Product;

public interface ProductDAO extends JpaRepository<Product, Integer> {
	Product findById(long id);

	Product findByProviderProductId(String providerProductId);

	Product findByEndUserId(String endUserId);
}
