package tn.wevioo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.wevioo.entities.Parameters;
import tn.wevioo.entities.type.ActionEnum;

public interface ParametersDAO extends JpaRepository<Parameters, Integer> {

	public Parameters findByActionAndTypeProduct(ActionEnum action, String productType);
}
