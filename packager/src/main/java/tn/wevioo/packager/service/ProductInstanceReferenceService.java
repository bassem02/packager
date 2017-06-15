package tn.wevioo.packager.service;

import tn.wevioo.packager.dto.product.ProductInstanceReferenceDTO;
import tn.wevioo.packager.entities.ProductInstanceReference;

public interface ProductInstanceReferenceService {

	ProductInstanceReferenceDTO convertToDTO(ProductInstanceReference productInstanceReference);

}
