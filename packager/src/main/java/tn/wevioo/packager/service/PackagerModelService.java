package tn.wevioo.packager.service;

import java.util.List;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import tn.wevioo.packager.dto.packager.PackagerModelDTO;
import tn.wevioo.packager.entities.PackagerModel;

public interface PackagerModelService {

	public PackagerModel saveOrUpdate(PackagerModel packagerModel);

	public void delete(PackagerModel packagerModel);

	public PackagerModel findById(int id) throws NotFoundException;

	public PackagerModel findByRetailerKey(String retailerKey) throws NotFoundException;

	public List<PackagerModel> findAll() throws NotFoundException;

	public PackagerModelDTO convertToDTO(PackagerModel packagerModel);

	public List<PackagerModel> findAllActive();

}
