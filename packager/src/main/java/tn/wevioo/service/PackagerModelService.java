package tn.wevioo.service;

import java.util.List;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import tn.wevioo.entities.PackagerModel;
import tn.wevioo.facade.packager.FPackagerModel;

public interface PackagerModelService {

	public PackagerModel saveOrUpdate(PackagerModel packagerModel);

	public void delete(PackagerModel packagerModel);

	public PackagerModel findById(int id);

	public PackagerModel findByRetailerKey(String retailerKey) throws NotFoundException;

	public List<PackagerModel> findAll();

	public FPackagerModel convertToDTO(PackagerModel packagerModel);

	public List<PackagerModel> findAllActive();

}
