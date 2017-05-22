package tn.wevioo.exporter;

import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import tn.wevioo.exceptions.PackagerException;
import tn.wevioo.model.request.PackagerRequest;

/**
 * PackagerExporter interface.
 * 
 * @author bflorea
 * @since 2.10.0
 * 
 */
public interface PackagerExporter {

	/**
	 * The method append allows adding a new request to export. The received
	 * request will be appended to the already exported requests, depending on
	 * the excepted format and action.
	 * 
	 * @param request
	 *            Request to export. Cannot be null.
	 * @throws PackagerException
	 *             custom exception.
	 * @throws NotRespectedRulesException
	 *             custom exception.
	 */
	public void append(PackagerRequest request) throws PackagerException, NotRespectedRulesException;

	/**
	 * The method close allows performing all actions required to close safely
	 * the export. Please refer to the chosen implementation in order to be
	 * aware of the implemented actions.
	 * 
	 * @throws PackagerException
	 *             custom exception.
	 */
	public void close() throws PackagerException;
}
