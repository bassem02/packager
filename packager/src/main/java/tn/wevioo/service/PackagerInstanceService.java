package tn.wevioo.service;

import java.util.List;

import tn.wevioo.dto.PackagerInstanceDTO;
import tn.wevioo.entities.PackagerInstance;

public interface PackagerInstanceService {

	public PackagerInstance saveOrUpdate(PackagerInstance packagerInstance);

	public void delete(PackagerInstance packagerInstance);

	public PackagerInstance findById(int id);

	public PackagerInstance findByRetailerPackagerId(String retailerPackagerId);

	public List<PackagerInstance> findAll();

	public PackagerInstanceDTO convertToDTO(PackagerInstance packagerInstance);

	public Boolean isRetailerPackagerIdFree(String retailerPackagerId);

}
