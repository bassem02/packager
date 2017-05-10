package tn.wevioo.service;

import java.util.List;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import tn.wevioo.entities.Retailer;

public interface RetailerService {

	public Retailer saveOrUpdate(Retailer retailer);

	public void delete(Retailer retailer);

	public Retailer findById(int id) throws NotFoundException;

	public List<Retailer> findAll();

}
