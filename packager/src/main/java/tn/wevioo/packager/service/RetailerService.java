package tn.wevioo.packager.service;

import java.util.List;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import tn.wevioo.packager.entities.Retailer;

public interface RetailerService {

	public Retailer saveOrUpdate(Retailer retailer);

	public void delete(Retailer retailer);

	public Retailer findById(int id) throws NotFoundException;

	public List<Retailer> findAll();

}
