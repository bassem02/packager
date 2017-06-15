package tn.wevioo.packager.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product_model_product_driver_port", catalog = "nn_packager_management_recette")
public class ProductModelProductDriverPort implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	Integer id;
	String productModel;
	int productDriverPort;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProductModel() {
		return productModel;
	}

	public void setProductModel(String productModel) {
		this.productModel = productModel;
	}

	public int getProductDriverPort() {
		return productDriverPort;
	}

	public void setProductDriverPort(int productDriverPort) {
		this.productDriverPort = productDriverPort;
	}

	public ProductModelProductDriverPort() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + productDriverPort;
		result = prime * result + ((productModel == null) ? 0 : productModel.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductModelProductDriverPort other = (ProductModelProductDriverPort) obj;
		if (productDriverPort != other.productDriverPort)
			return false;
		if (productModel == null) {
			if (other.productModel != null)
				return false;
		} else if (!productModel.equals(other.productModel))
			return false;
		return true;
	}

}
