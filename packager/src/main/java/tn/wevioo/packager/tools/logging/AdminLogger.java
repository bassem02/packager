package tn.wevioo.packager.tools.logging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The class {@link AdminLogger} has been designed in order to send easily
 * report to Packager application's administrators. All business processes which
 * have to warn administrators about something wrong or anything else, can use
 * this class' methods, setting the right message to send.
 * <p>
 * This class is supposed to be correctly configured by log4j in order to send
 * reports to administrators by email, for example.
 */
public class AdminLogger {

	/**
	 * {@link AdminLogger}'s Logger.
	 */
	private static final Log LOGGER = LogFactory.getLog(AdminLogger.class);

	/**
	 * The method {@link #error(String, Throwable)} allows logging a message to
	 * the administrator at an error level.
	 * 
	 * @param message
	 *            Message to log.
	 */
	public static void error(String message) {

		if (LOGGER.isErrorEnabled()) {
			LOGGER.error(message);
		}
	}

	/**
	 * The method {@link #error(String, Throwable)} allows logging a message to
	 * the administrator at an error level.
	 * 
	 * @param message
	 *            Message to log.
	 * @param ex
	 *            Exception cause.
	 */
	public static void error(String message, Throwable ex) {

		if (LOGGER.isErrorEnabled()) {
			LOGGER.error(message, ex);
		}
	}

	/**
	 * The method {@link #fatal(String, Throwable)} allows logging a message to
	 * the administrator at an fatal level.
	 * 
	 * @param message
	 *            Message to log.
	 */
	public static void fatal(String message) {

		if (LOGGER.isFatalEnabled()) {
			LOGGER.fatal(message);
		}
	}

	/**
	 * The method {@link #fatal(String, Throwable)} allows logging a message to
	 * the administrator at an fatal level.
	 * 
	 * @param message
	 *            Message to log.
	 * @param ex
	 *            Exception cause.
	 */
	public static void fatal(String message, Throwable ex) {

		if (LOGGER.isFatalEnabled()) {
			LOGGER.fatal(message, ex);
		}
	}

	/**
	 * The method {@link #info(String, Throwable)} allows logging a message to
	 * the administrator at an info level.
	 * 
	 * @param message
	 *            Message to log.
	 */
	public static void info(String message) {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(message);
		}
	}

	/**
	 * The method {@link #info(String, Throwable)} allows logging a message to
	 * the administrator at an info level.
	 * 
	 * @param message
	 *            Message to log.
	 * @param ex
	 *            Exception cause.
	 */
	public static void info(String message, Throwable ex) {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(message, ex);
		}
	}

	/**
	 * The method {@link #warn(String, Throwable)} allows logging a message to
	 * the administrator at an warn level.
	 * 
	 * @param message
	 *            Message to log.
	 */
	public static void warn(String message) {

		if (LOGGER.isWarnEnabled()) {
			LOGGER.warn(message);
		}
	}

	/**
	 * The method {@link #warn(String, Throwable)} allows logging a message to
	 * the administrator at an warn level.
	 * 
	 * @param message
	 *            Message to log.
	 * @param ex
	 *            Exception cause.
	 */
	public static void warn(String message, Throwable ex) {

		if (LOGGER.isWarnEnabled()) {
			LOGGER.warn(message, ex);
		}
	}
}
