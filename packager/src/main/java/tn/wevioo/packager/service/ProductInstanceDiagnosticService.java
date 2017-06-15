package tn.wevioo.packager.service;

import tn.wevioo.packager.dto.product.ProductInstanceDiagnosticDTO;
import tn.wevioo.packager.entities.ProductInstanceDiagnostic;

public interface ProductInstanceDiagnosticService {

	ProductInstanceDiagnosticDTO convertToDTO(ProductInstanceDiagnostic productInstanceDiagnostic);

}
