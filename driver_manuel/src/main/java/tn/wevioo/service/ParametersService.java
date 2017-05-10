package tn.wevioo.service;

import java.util.List;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import tn.wevioo.entities.Parameters;
import tn.wevioo.entities.type.ActionEnum;

public interface ParametersService {

	Parameters saveOrUpdate(Parameters parameters);

	void delete(Parameters parameters);

	Parameters findById(int id) throws NotFoundException;

	List<Parameters> findAll();

	Parameters findByActionAndTypeProduct(ActionEnum action, String productType) throws NotFoundException;

}
