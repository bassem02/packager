package tn.wevioo.model.product.action;

/**
 * The class ProductActionReportTemplate allows modeling template in order to generate reports on specific action. Each
 * ProductInstanceAction is supposed to have its own unique action report template (whose identifier is the name of the
 * action) which can be then used as a usual resources.
 * <p>
 * This class maps the database table 'product_instance_action_report_template'.
 * 
 * @author vberezan
 * @since 2.5.2
 */
public class ProductActionReportTemplate {

	/**
	 * Unique database identifier. The value stored into the database is the equivalent of the value returned by the
	 * method toString() of the enumeration ProductInstanceAction.
	 */
	private ProductInstanceAction id;

	/**
	 * The attribute singleLineTemplate contains a single-line String which can be used to generate a simple action
	 * report. The template can be parametrized with the available defined tags. Examples of a single-line action report
	 * template could be:
	 * <ul>
	 * <li>product [${srcProductId}] is activated</li>
	 * <li>creation of a product [${destProductId}]</li>
	 * </ul>
	 */
	private String singleLineTemplate;

	/**
	 * The constant DEST_PRODUCT_ID_TAG defines the tag which should be replaced by the identifier of the destination
	 * product in the final report.
	 */
	public static final String DEST_PRODUCT_ID_TAG = "${destProductId}";

	/**
	 * The constant DEST_PRODUCT_MODEL_TAG defines the tag which should be replaced by the product model of the
	 * destination product in the final report.
	 */
	public static final String DEST_PRODUCT_MODEL_TAG = "${destProductModel}";

	/**
	 * The constant SRC_PRODUCT_ID_TAG defines the tag which should be replaced by the identifier of the source product
	 * in the final report.
	 */
	public static final String SRC_PRODUCT_ID_TAG = "${srcProductId}";

	/**
	 * The constant SRC_PRODUCT_MODEL_TAG defines the tag which should be replaced by the identifier of the source
	 * product in the final report.
	 */
	public static final String SRC_PRODUCT_MODEL_TAG = "${srcProductModel}";

	public ProductInstanceAction getId() {
		return id;
	}

	public void setId(ProductInstanceAction id) {
		this.id = id;
	}

	public String getSingleLineTemplate() {
		return singleLineTemplate;
	}

	public void setSingleLineTemplate(String singleLineTemplate) {
		this.singleLineTemplate = singleLineTemplate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((singleLineTemplate == null) ? 0 : singleLineTemplate.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ProductActionReportTemplate other = (ProductActionReportTemplate) obj;
		if (singleLineTemplate == null) {
			if (other.singleLineTemplate != null) {
				return false;
			}
		} else if (!singleLineTemplate.equals(other.singleLineTemplate)) {
			return false;
		}
		return true;
	}

}
