package tn.wevioo.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import tn.wevioo.entities.ProductInstanceDiagnostic;
import tn.wevioo.facade.product.FProductInstanceDiagnostic;
import tn.wevioo.service.ProductInstanceDiagnosticService;

@Service("productInstanceDiagnosticService")
public class ProductInstanceDiagnosticServiceImpl implements ProductInstanceDiagnosticService {

	@Override
	public FProductInstanceDiagnostic convertToDTO(ProductInstanceDiagnostic productInstanceDiagnostic) {

		FProductInstanceDiagnostic fProductInstanceDiagnostic = new FProductInstanceDiagnostic();
		// fProductInstanceDiagnostic.setCreationDate(productInstanceDiagnostic.getCreationDate());
		// fProductInstanceDiagnostic.setDiagnosticKey(productInstanceDiagnostic.getDiagnosticKey());
		// fProductInstanceDiagnostic.setDiagnosticValue(productInstanceDiagnostic.getDiagnosticValue());
		BeanUtils.copyProperties(productInstanceDiagnostic, fProductInstanceDiagnostic);

		return fProductInstanceDiagnostic;
	}

}
