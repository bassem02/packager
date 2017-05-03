package tn.wevioo.service;

import tn.wevioo.entities.ProductInstanceDiagnostic;
import tn.wevioo.facade.product.FProductInstanceDiagnostic;

public interface ProductInstanceDiagnosticService {

	FProductInstanceDiagnostic convertToDTO(ProductInstanceDiagnostic productInstanceDiagnostic);

}
