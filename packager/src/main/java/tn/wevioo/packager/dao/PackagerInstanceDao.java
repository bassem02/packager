package tn.wevioo.packager.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tn.wevioo.packager.entities.PackagerInstance;

public interface PackagerInstanceDao extends JpaRepository<PackagerInstance, Integer> {

	public PackagerInstance findByRetailerPackagerId(String retailerPackagerId);

	public Integer countByRetailerPackagerId(String retailerPackagerId);

	@Query("SELECT MAX(idPackagerInstance) FROM PackagerInstance")
	public Long getMaxIdPackagerInstance();

}
