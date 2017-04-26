package tn.wevioo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public Parameters findById(int id) {
		return parametersDAO.findOne(id);
	}

	@Override
	public List<Parameters> findAll() {
		return parametersDAO.findAll();
	}

	@Override
	public Parameters findByActionAndTypeProduct(ActionEnum action, String productType) {
		return parametersDAO.findByActionAndTypeProduct(action, productType);
	}

}
