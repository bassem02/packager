package tn.wevioo.packager.model;

import nordnet.architecture.exceptions.NNException;
import nordnet.architecture.exceptions.NNImplicitException;
import nordnet.tools.converter.exceptions.ConverterException;

/**
 * The class AbstractFacade provides a convenience support to all the facades of
 * the packager core. Common services and methods are implemented and
 * automatically reachable from all other facades.
 * 
 * @author mleahu
 * @since 2.0.0
 */
public abstract class AbstractFacade {

	/**
	 * Web Service User Service.
	 */
	// private WebServiceUserService webServiceUserService = null;

	/**
	 * The method authenticate allows the user of the facade to authenticate
	 * with a login and password in order to have access to all functionalities.
	 * The authentication is available for all the current thread's life.
	 * 
	 * @param login
	 *            The login to use to authenticate. Cannot be null or empty.
	 * @param password
	 *            The plain text password to use to authenticate. Cannot be null
	 *            or empty.
	 * @throws PackagerException
	 *             custom exception.
	 * @throws DataSourceException
	 *             custom exception.
	 */
	/*
	 * public void authenticate(String login, String password) throws
	 * PackagerException, DataSourceException {
	 * webServiceUserService.authenticate(login, password); }
	 */

	/**
	 * The method authenticate allows the user of the facade to authenticate
	 * with a login, password and ip address in order to have access to all
	 * functionalities. The authentication is available for all the current
	 * thread's life.
	 * 
	 * @param login
	 *            The login to use to authenticate. Cannot be null or empty.
	 * @param password
	 *            The plain text password to use to authenticate. Cannot be null
	 *            or empty.
	 * @param ipAddress
	 *            IP Address from which the user is trying to connect. Cannot be
	 *            null or empty.
	 * @throws PackagerException
	 *             custom exception.
	 * @throws DataSourceException
	 *             custom exception.
	 * @since 2.8.2
	 */
	/*
	 * public void authenticate(String login, String password, String ipAddress)
	 * throws PackagerException, DataSourceException {
	 * webServiceUserService.authenticate(login, password, ipAddress); }
	 * 
	 * /** {@inheritDoc}
	 */
	/*
	 * public String getAuthenticatedUserLogin() throws PackagerException,
	 * DataSourceException { return
	 * webServiceUserService.getAuthenticatedUser().getLogin(); }
	 */

	/**
	 * The method logout allows unsetting the user currently authenticated for
	 * the current thread. If no user is authenticated, the method has no impact
	 * and does not throw any exception.
	 * 
	 * @throws PackagerException
	 *             custom exception.
	 */
	/*
	 * public void logout() throws PackagerException {
	 * webServiceUserService.logout(); }
	 * 
	 * public void setWebServiceUserService(WebServiceUserService
	 * webServiceUserService) { this.webServiceUserService =
	 * webServiceUserService; }
	 * 
	 * public WebServiceUserService getWebServiceUserService() { return
	 * webServiceUserService; }
	 */

	/**
	 * Helper, recursive method that finds the first cause of type NNException
	 * or NNImplicitException of the given exception.
	 * 
	 * @param ex
	 *            The exception for which should be found the cause of type
	 *            NNException or NNImplicitException
	 * @return the first NNException or NNImplicitException found.
	 */
	private Throwable findInnerException(Throwable ex) {
		if (ex == null || ex instanceof NNException || ex instanceof NNImplicitException) {
			return ex;
		}
		return findInnerException(ex.getCause());
	}

	/**
	 * This method browses the cause of the received converter exception.
	 * 
	 * @param converterException
	 *            The exception for which should be found the cause of type
	 *            NNException or NNImplicitException
	 * @return the first NNException or NNImplicitException found. If none of
	 *         these ones is found, the method should return the received
	 *         ConverterException.
	 */
	protected Throwable findOriginalException(ConverterException converterException) {
		Throwable originalException = findInnerException(converterException.getCause());
		if (originalException == null) {
			return converterException;
		}

		return originalException;

	}
}
