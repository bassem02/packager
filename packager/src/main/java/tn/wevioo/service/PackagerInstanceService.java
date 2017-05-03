package tn.wevioo.service;

import java.util.List;

import nordnet.drivers.contract.exceptions.DriverException;
import tn.wevioo.entities.PackagerInstance;
import tn.wevioo.facade.packager.FPackagerInstance;
import tn.wevioo.facade.packager.FPackagerInstanceHeader;

public interface PackagerInstanceService {

	public PackagerInstance saveOrUpdate(PackagerInstance packagerInstance);

	public void delete(PackagerInstance packagerInstance);

	public PackagerInstance findById(int id);

	public PackagerInstance findByRetailerPackagerId(String retailerPackagerId);

	public List<PackagerInstance> findAll();

	public FPackagerInstance convertToDTO(PackagerInstance packagerInstance) throws DriverException;

	public Boolean isRetailerPackagerIdFree(String retailerPackagerId);

	public FPackagerInstanceHeader convertToHeaderDTO(PackagerInstance packagerInstance);

}
