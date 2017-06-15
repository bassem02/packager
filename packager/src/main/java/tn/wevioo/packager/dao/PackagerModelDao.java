package tn.wevioo.packager.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.wevioo.packager.entities.PackagerModel;

public interface PackagerModelDao extends JpaRepository<PackagerModel, Integer> {
	
public PackagerModel findByRetailerKey(String retailerKey);

}

