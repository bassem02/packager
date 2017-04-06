package tn.wevioo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.wevioo.entities.PackagerInstance;

public interface PackagerInstanceDao extends JpaRepository<PackagerInstance, Integer> {

	public PackagerInstance findByRetailerPackagerId(String retailerPackagerId);
}
