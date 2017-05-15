package tn.wevioo.dto.product.action;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import tn.wevioo.dto.product.ProductInstanceDTO;

/**
 * The class ProductActionHistory allows saving in database one history over an
 * action which has been performed on a product instance. This history should be
 * kept as long as possible in the database, even if the corresponding target
 * products are deleted. This class maps the database table
 * 'product_action_history'.
 * 
 * @author vberezan
 * @since 2.5.2
 */
public class ProductActionHistoryDTO {

	/**
	 * The date when current action history is saved into database.
	 */

	@JsonFormat(pattern = "yyyy-MM-dd KK:mm:ss")
	private Date creationDate;

	/**
	 * The attribute action contains the type of the action which is performed
	 * over the source product.
	 */
	private ProductInstanceActionDTO action;

	/**
	 * The attribute properties contains the Xml properties which have been used
	 * to perform the action. This attribute can be null if no property has been
	 * used. This attribute is saved into database only in an historical way,
	 * and should not be used for any other action.
	 */
	private String properties;

	/**
	 * The user which has asked current action to be performed. This normally
	 * should correspond to the user which is currently authenticated when the
	 * action is performed.
	 */
	private String username;

	/**
	 * Destination product identifier.
	 */
	private Long destProductId;

	/**
	 * Destination product instance.
	 */
	private ProductInstanceDTO destProduct;

	/**
	 * Destination product model key.
	 */
	private String destProductModel;

	/**
	 * Source product identifier.
	 */
	private Long srcProductId;

	/**
	 * Source product instance.
	 */
	private ProductInstanceDTO srcProduct;

	/**
	 * Source product model key.
	 */
	private String srcProductModel;

	/**
	 * Default constructor.
	 */
	public ProductActionHistoryDTO() {

	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public ProductInstanceActionDTO getAction() {
		return action;
	}

	public void setAction(ProductInstanceActionDTO action) {
		this.action = action;
	}

	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getDestProductId() {
		return destProductId;
	}

	public void setDestProductId(Long destProductId) {
		this.destProductId = destProductId;
	}

	public String getDestProductModel() {
		return destProductModel;
	}

	public void setDestProductModel(String destProductModel) {
		this.destProductModel = destProductModel;
	}

	public Long getSrcProductId() {
		return srcProductId;
	}

	public void setSrcProductId(Long srcProductId) {
		this.srcProductId = srcProductId;
	}

	public String getSrcProductModel() {
		return srcProductModel;
	}

	public void setSrcProductModel(String srcProductModel) {
		this.srcProductModel = srcProductModel;
	}

	public ProductInstanceDTO getDestProduct() {
		return destProduct;
	}

	public void setDestProduct(ProductInstanceDTO destProduct) {
		this.destProduct = destProduct;
	}

	public ProductInstanceDTO getSrcProduct() {
		return srcProduct;
	}

	public void setSrcProduct(ProductInstanceDTO srcProduct) {
		this.srcProduct = srcProduct;
	}

}
