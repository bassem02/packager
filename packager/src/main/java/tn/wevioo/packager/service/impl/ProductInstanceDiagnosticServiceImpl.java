package tn.wevioo.packager.service.impl;

import org.springframework.stereotype.Service;

import tn.wevioo.packager.dto.product.ProductInstanceDiagnosticDTO;
import tn.wevioo.packager.entities.ProductInstanceDiagnostic;
import tn.wevioo.packager.service.ProductInstanceDiagnosticService;

@Service("productInstanceDiagnosticService")
public class ProductInstanceDiagnosticServiceImpl implements ProductInstanceDiagnosticService {

	@Override
	public ProductInstanceDiagnosticDTO convertToDTO(ProductInstanceDiagnostic productInstanceDiagnostic) {

		ProductInstanceDiagnosticDTO productInstanceDiagnosticDTO = new ProductInstanceDiagnosticDTO();
		productInstanceDiagnosticDTO.setCreationDate(productInstanceDiagnostic.getCreationDate());
		productInstanceDiagnosticDTO.setDiagnosticKey(productInstanceDiagnostic.getDiagnosticKey());
		productInstanceDiagnosticDTO.setDiagnosticValue(productInstanceDiagnostic.getDiagnosticValue());

		return productInstanceDiagnosticDTO;
	}

}
