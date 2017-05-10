package tn.wevioo.service;

import java.util.List;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import nordnet.drivers.contract.exceptions.DriverException;
import tn.wevioo.dto.packager.PackagerInstanceDTO;
import tn.wevioo.dto.packager.PackagerInstanceHeaderDTO;
import tn.wevioo.entities.PackagerInstance;
import tn.wevioo.exceptions.RestTemplateException;

public interface PackagerInstanceService {

	public PackagerInstance saveOrUpdate(PackagerInstance packagerInstance);

	public void delete(PackagerInstance packagerInstance);

	public PackagerInstance findById(int id) throws NotFoundException;

	public PackagerInstance findByRetailerPackagerId(String retailerPackagerId) throws NotFoundException;

	public List<PackagerInstance> findAll();

	public PackagerInstanceDTO convertToDTO(PackagerInstance packagerInstance)
			throws DriverException, RestTemplateException, NotFoundException;

	public Boolean isRetailerPackagerIdFree(String retailerPackagerId);

	public PackagerInstanceHeaderDTO convertToHeaderDTO(PackagerInstance packagerInstance);

}
