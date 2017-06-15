package tn.wevioo.service.impl;

import org.springframework.stereotype.Service;

import tn.wevioo.dto.product.ProductInstanceReferenceDTO;
import tn.wevioo.entities.ProductInstanceReference;
import tn.wevioo.service.ProductInstanceReferenceService;

@Service("productInstanceReferenceService")
public class ProductInstanceReferenceServiceImpl implements ProductInstanceReferenceService {

	@Override
	public ProductInstanceReferenceDTO convertToDTO(ProductInstanceReference productInstanceReference) {

		ProductInstanceReferenceDTO productInstanceReferenceDTO = new ProductInstanceReferenceDTO();
		productInstanceReferenceDTO.setCreationDate(productInstanceReference.getCreationDate());
		productInstanceReferenceDTO.setReferenceType(productInstanceReference.getDiscriminatorType());
		productInstanceReferenceDTO.setReferenceValue(productInstanceReference.getDiscriminatorValue());
		return productInstanceReferenceDTO;
	}

}
