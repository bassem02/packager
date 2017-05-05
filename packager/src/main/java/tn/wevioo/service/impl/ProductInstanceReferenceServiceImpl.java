package tn.wevioo.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import tn.wevioo.dto.product.ProductInstanceReferenceDTO;
import tn.wevioo.entities.ProductInstanceReference;
import tn.wevioo.service.ProductInstanceReferenceService;

@Service("productInstanceReferenceService")
public class ProductInstanceReferenceServiceImpl implements ProductInstanceReferenceService {

	@Override
	public ProductInstanceReferenceDTO convertToDTO(ProductInstanceReference productInstanceReference) {

		ProductInstanceReferenceDTO productInstanceReferenceDTO = new ProductInstanceReferenceDTO();
		// fProductInstanceReference.setCreationDate(productInstanceReference.getCreationDate());
		// fProductInstanceReference.setReferenceType(productInstanceReference.getDiscriminatorType());
		// fProductInstanceReference.setReferenceValue(productInstanceReference.getDiscriminatorValue());
		BeanUtils.copyProperties(productInstanceReference, productInstanceReferenceDTO);
		return productInstanceReferenceDTO;
	}

}
