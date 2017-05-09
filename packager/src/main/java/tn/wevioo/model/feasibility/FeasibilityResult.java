package tn.wevioo.model.feasibility;

/**
 * The class FeasibilityResult allows providing a result when verifying if an action is possible or not.
 * 
 * @author vberezan
 * @since 2.0.0
 */
public class FeasibilityResult {

	/**
	 * If the action, tested by the feasibility verification, is possible, this attribute must be set to true. False,
	 * else.
	 */
	private Boolean possible;

	/**
	 * The explanation why the action is not possible. If the action is possible, nothing guarantees this attribute is
	 * not null.
	 */
	private String motive;

	/**
	 * Exception cause.
	 */
	private Throwable exceptionCause;

	/**
	 * Constructor with parameters.
	 * 
	 * @param possible
	 *            Boolean flag.
	 * @param motive
	 *            This parameter is populated only when possible is FALSE.
	 */
	public FeasibilityResult(Boolean possible, String motive) {
		this.possible = possible;
		this.motive = motive;
	}

	/**
	 * Constructor with parameters.
	 * 
	 * @param possible
	 *            Boolean flag.
	 * @param motive
	 *            This parameter is populated only when possible is FALSE.
	 */
	public FeasibilityResult(Boolean possible, String motive, Throwable exceptionCause) {
		this.possible = possible;
		this.motive = motive;
		this.exceptionCause = exceptionCause;
	}

	/**
	 * Default constructor.
	 */
	public FeasibilityResult() {

	}

	public Boolean getPossible() {
		return possible;
	}

	public void setPossible(Boolean possible) {
		this.possible = possible;
	}

	public String getMotive() {
		return motive;
	}

	public void setMotive(String motive) {
		this.motive = motive;
	}

	public Throwable getExceptionCause() {
		return exceptionCause;
	}

	public void setExceptionCause(Throwable exceptionCause) {
		this.exceptionCause = exceptionCause;
	}

}
