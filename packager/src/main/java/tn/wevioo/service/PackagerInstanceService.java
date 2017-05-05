package tn.wevioo.service;

import java.util.List;

import nordnet.drivers.contract.exceptions.DriverException;
import tn.wevioo.dto.packager.PackagerInstanceDTO;
import tn.wevioo.dto.packager.PackagerInstanceHeaderDTO;
import tn.wevioo.entities.PackagerInstance;

public interface PackagerInstanceService {

	public PackagerInstance saveOrUpdate(PackagerInstance packagerInstance);

	public void delete(PackagerInstance packagerInstance);

	public PackagerInstance findById(int id);

	public PackagerInstance findByRetailerPackagerId(String retailerPackagerId);

	public List<PackagerInstance> findAll();

	public PackagerInstanceDTO convertToDTO(PackagerInstance packagerInstance) throws DriverException;

	public Boolean isRetailerPackagerIdFree(String retailerPackagerId);

	public PackagerInstanceHeaderDTO convertToHeaderDTO(PackagerInstance packagerInstance);

}
