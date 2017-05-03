package tn.wevioo.service;

import tn.wevioo.entities.ProductInstanceReference;
import tn.wevioo.facade.product.FProductInstanceReference;

public interface ProductInstanceReferenceService {

	FProductInstanceReference convertToDTO(ProductInstanceReference productInstanceReference);

}
