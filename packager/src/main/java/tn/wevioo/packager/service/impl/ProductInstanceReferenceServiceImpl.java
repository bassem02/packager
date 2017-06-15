package tn.wevioo.packager.service.impl;

import org.springframework.stereotype.Service;

import tn.wevioo.packager.dto.product.ProductInstanceReferenceDTO;
import tn.wevioo.packager.entities.ProductInstanceReference;
import tn.wevioo.packager.service.ProductInstanceReferenceService;

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
