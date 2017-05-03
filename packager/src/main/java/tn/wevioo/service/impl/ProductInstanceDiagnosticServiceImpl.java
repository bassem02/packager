package tn.wevioo.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import tn.wevioo.dto.product.ProductInstanceDiagnosticDTO;
import tn.wevioo.entities.ProductInstanceDiagnostic;
import tn.wevioo.service.ProductInstanceDiagnosticService;

@Service("productInstanceDiagnosticService")
public class ProductInstanceDiagnosticServiceImpl implements ProductInstanceDiagnosticService {

	@Override
	public ProductInstanceDiagnosticDTO convertToDTO(ProductInstanceDiagnostic productInstanceDiagnostic) {

		ProductInstanceDiagnosticDTO productInstanceDiagnosticDTO = new ProductInstanceDiagnosticDTO();
		// fProductInstanceDiagnostic.setCreationDate(productInstanceDiagnostic.getCreationDate());
		// fProductInstanceDiagnostic.setDiagnosticKey(productInstanceDiagnostic.getDiagnosticKey());
		// fProductInstanceDiagnostic.setDiagnosticValue(productInstanceDiagnostic.getDiagnosticValue());
		BeanUtils.copyProperties(productInstanceDiagnostic, productInstanceDiagnosticDTO);

		return productInstanceDiagnosticDTO;
	}

}
