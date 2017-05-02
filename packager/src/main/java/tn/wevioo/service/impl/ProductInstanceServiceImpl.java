package tn.wevioo.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.wevioo.dao.ProductInstanceDAO;
import tn.wevioo.dto.ProductInstanceDTO;
import tn.wevioo.entities.ProductInstance;
import tn.wevioo.service.ProductInstanceService;
import tn.wevioo.service.ProductModelService;

@Service("productInstanceService")
public class ProductInstanceServiceImpl implements ProductInstanceService {

	@Autowired
	public ProductInstanceDAO productInstanceDAO;

	@Autowired
	public ProductModelService productModelService;

	@Override
	public ProductInstance saveOrUpdate(ProductInstance productInstance) {
		return productInstanceDAO.saveAndFlush(productInstance);
	}

	@Override
	public void delete(ProductInstance productInstance) {

		productInstanceDAO.delete(productInstance);
	}

	@Override
	public ProductInstance findById(int id) {
		return productInstanceDAO.findOne(id);
	}

	@Override
	public List<ProductInstance> findAll() {
		return productInstanceDAO.findAll();
	}

	@Override
	public ProductInstanceDTO convertToDTO(ProductInstance productInstance) {
		ProductInstanceDTO productInstanceDTO = new ProductInstanceDTO();
		BeanUtils.copyProperties(productInstance, productInstanceDTO);
		productInstanceDTO.setProductModel(productModelService.convertToDTO(productInstance.getProductModel()));
		return productInstanceDTO;
	}

}
