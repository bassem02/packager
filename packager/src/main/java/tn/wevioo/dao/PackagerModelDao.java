package tn.wevioo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.wevioo.entities.PackagerModel;

public interface PackagerModelDao extends JpaRepository<PackagerModel, Integer> {
	
public PackagerModel findByRetailerKey(String retailerKey);

}

