package tn.wevioo.driverManual.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.wevioo.driverManual.entities.Product;

public interface ProductDAO extends JpaRepository<Product, Integer> {
	Product findById(Long id);

	Product findByProviderProductId(String providerProductId);

	Product findByEndUserId(String endUserId);
}
