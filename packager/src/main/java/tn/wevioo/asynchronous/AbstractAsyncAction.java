package tn.wevioo.asynchronous;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.implicit.NullException.NullCases;
import tn.wevioo.controller.AsynchronousPackagerManagementController;

/**
 * The class AbstractAsyncAction provides a convenience base which makes the
 * development of asynchronous actions easier.
 * 
 * @author mleahu
 * @author vberezan
 * @since 2.0.0
 */

@Component
public class AbstractAsyncAction implements Runnable {

	@Autowired
	private AsynchronousPackagerManagementController asynchronousPackagerManagementController;

	/**
	 * {@link AbstractAsyncAction}'s Logger.
	 */
	private static final Log LOGGER = LogFactory.getLog(AbstractAsyncAction.class);

	/**
	 * This attribute contains the identifier of the action ticket which
	 * contains the information about the status of the current asynchronous
	 * action. No AsyncAction can be instantiated without this identifier ; this
	 * means this value must be given as a parameter of the used constructor.
	 */
	private String ticketIdentifier;

	/**
	 * Default constructor.
	 * 
	 * @param ticketIdentifier
	 *            The identifier of the action ticket whose references the
	 *            current action. Cannot be null.
	 */
	protected AbstractAsyncAction(String ticketIdentifier) {
		if (ticketIdentifier == null) {
			throw new NullException(NullCases.NULL, "ticketIdentifier parameter");
		}

		this.ticketIdentifier = ticketIdentifier;
	}

	/**
	 * The true call to the action which has to be asynchronous. Every exception
	 * is caught and make the action as failed. If no action is thrown, the
	 * action is considered has successed.
	 * <p>
	 * Each asynchronous action has to implement this method.
	 * 
	 * @throws Exception
	 *             custom exception.
	 */
	protected void doAction() throws Exception {
	}

	/**
	 * Run method.
	 */
	public void run() {
		try {
			this.doAction();
			asynchronousPackagerManagementController.completeAction(ticketIdentifier, true, null);
		} catch (Exception e) {
			try {
				asynchronousPackagerManagementController.completeAction(ticketIdentifier, false, e);
			} catch (Exception e1) {
				LOGGER.fatal("The action ticket has not been saved successfuly", e1);
			}
		}

	}

	public String getTicketIdentifier() {
		return ticketIdentifier;
	}

	public void setTicketIdentifier(String ticketIdentifier) {
		this.ticketIdentifier = ticketIdentifier;
	}

	public AbstractAsyncAction() {
	}

}
