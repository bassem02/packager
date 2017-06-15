package tn.wevioo.driverManual.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.wevioo.driverManual.entities.Parameters;
import tn.wevioo.driverManual.entities.type.ActionEnum;

public interface ParametersDAO extends JpaRepository<Parameters, Integer> {

	public Parameters findByActionAndTypeProduct(ActionEnum action, String productType);
}
