package tn.wevioo.service;

import java.util.List;

import tn.wevioo.dto.RetailerDTO;
import tn.wevioo.entities.Retailer;

public interface RetailerService {

	public Retailer saveOrUpdate(Retailer retailer);

	public void delete(Retailer retailer);

	public Retailer findById(int id);

	public List<Retailer> findAll();

	public RetailerDTO convertToDTO(Retailer retailer);
}
