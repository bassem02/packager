package tn.wevioo.service.impl;

import org.springframework.stereotype.Service;

import tn.wevioo.dto.product.ProductInstanceDiagnosticDTO;
import tn.wevioo.entities.ProductInstanceDiagnostic;
import tn.wevioo.service.ProductInstanceDiagnosticService;

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
