package tn.wevioo.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import tn.wevioo.entities.ProductInstanceReference;
import tn.wevioo.facade.product.FProductInstanceReference;
import tn.wevioo.service.ProductInstanceReferenceService;

@Service("productInstanceReferenceService")
public class ProductInstanceReferenceServiceImpl implements ProductInstanceReferenceService {

	@Override
	public FProductInstanceReference convertToDTO(ProductInstanceReference productInstanceReference) {

		FProductInstanceReference fProductInstanceReference = new FProductInstanceReference();
		// fProductInstanceReference.setCreationDate(productInstanceReference.getCreationDate());
		// fProductInstanceReference.setReferenceType(productInstanceReference.getDiscriminatorType());
		// fProductInstanceReference.setReferenceValue(productInstanceReference.getDiscriminatorValue());
		BeanUtils.copyProperties(productInstanceReference, fProductInstanceReference);
		return fProductInstanceReference;
	}

}
