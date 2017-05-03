package tn.wevioo.service;

import tn.wevioo.dto.product.ProductInstanceReferenceDTO;
import tn.wevioo.entities.ProductInstanceReference;

public interface ProductInstanceReferenceService {

	ProductInstanceReferenceDTO convertToDTO(ProductInstanceReference productInstanceReference);

}
