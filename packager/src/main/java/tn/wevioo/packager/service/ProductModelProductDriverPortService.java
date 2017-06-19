package tn.wevioo.packager.service;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import tn.wevioo.packager.entities.ProductModelProductDriverPort;

public interface ProductModelProductDriverPortService extends CrudService<ProductModelProductDriverPort, Integer> {

	public ProductModelProductDriverPort findByProductModel(String productModel) throws NotFoundException;

}
