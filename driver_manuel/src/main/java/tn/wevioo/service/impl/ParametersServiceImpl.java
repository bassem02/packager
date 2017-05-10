package tn.wevioo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.implicit.NullException.NullCases;
import nordnet.architecture.exceptions.utils.ErrorCode;
import tn.wevioo.dao.ParametersDAO;
import tn.wevioo.entities.Parameters;
import tn.wevioo.entities.type.ActionEnum;
import tn.wevioo.service.ParametersService;

@Service("parametersService")
public class ParametersServiceImpl implements ParametersService {

	@Autowired
	public ParametersDAO parametersDAO;

	@Override
	public Parameters saveOrUpdate(Parameters parameters) {
		return parametersDAO.saveAndFlush(parameters);
	}

	@Override
	public void delete(Parameters parameters) {
		parametersDAO.delete(parameters);
	}

	@Override
	public Parameters findById(int id) throws NotFoundException {
		if (((Integer) id == null)) {
			throw new NullException(NullCases.NULL_EMPTY, "id parameter");
		}

		Parameters result = parametersDAO.findOne(id);

		if (result == null) {
			throw new NotFoundException(new ErrorCode("0.2.1.3.2"), new Object[] { "Parameters", " id", id });
		}
		return result;
	}

	@Override
	public List<Parameters> findAll() {
		return parametersDAO.findAll();
	}

	@Override
	public Parameters findByActionAndTypeProduct(ActionEnum action, String productType) throws NotFoundException {
		if ((productType == null) || (productType.length() == 0)) {
			throw new NullException(NullCases.NULL_EMPTY, "productType parameter");
		}

		if ((action == null)) {
			throw new NullException(NullCases.NULL_EMPTY, "action parameter");
		}

		Parameters result = parametersDAO.findByActionAndTypeProduct(action, productType);

		if (result == null) {
			throw new NotFoundException(new ErrorCode("0.2.1.3.2"),
					new Object[] { "Parameters", " action and productType", action + " and " + productType });
		}

		return result;
	}

}
