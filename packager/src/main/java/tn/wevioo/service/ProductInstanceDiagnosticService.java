package tn.wevioo.service;

import tn.wevioo.dto.product.ProductInstanceDiagnosticDTO;
import tn.wevioo.entities.ProductInstanceDiagnostic;

public interface ProductInstanceDiagnosticService {

	ProductInstanceDiagnosticDTO convertToDTO(ProductInstanceDiagnostic productInstanceDiagnostic);

}
