package tn.wevioo.packager.service;

import java.util.List;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import tn.wevioo.packager.dto.packager.PackagerModelDTO;
import tn.wevioo.packager.entities.PackagerModel;

public interface PackagerModelService extends CrudService<PackagerModel, Integer> {

	public PackagerModel findByRetailerKey(String retailerKey) throws NotFoundException;

	public PackagerModelDTO convertToDTO(PackagerModel packagerModel);

	public List<PackagerModel> findAllActive();

}
