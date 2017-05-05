package tn.wevioo.exceptions;

import java.util.ArrayList;
import java.util.List;

import nordnet.architecture.exceptions.NNException;
import nordnet.architecture.exceptions.NNImplicitException;
import nordnet.architecture.exceptions.helper.ExceptionMessageHelper;
import nordnet.architecture.exceptions.utils.ErrorCode;

/**
 * The {@link DeliveryException} exception should be thrown when an exception occurs while managing a delivery process,
 * as computing the delivery request or contacting the Web Service NetDelivery.
 * <p>
 * The {@link DeliveryException} is based on error codes for which correspond specific exception messages. These
 * messages have to be contained in a file located in the project classpath directories. Please refer to the
 * {@link ExceptionMessageHelper} to be aware of this file loading conditions.
 * 
 * @author THUGUERRE
 * @since 2.0.0
 */
public class DeliveryException extends NNException {

	/**
	 * {@link DeliveryException}'s default serial identifier.
	 */
	private static final long serialVersionUID = -8556778449412780557L;

	/**
	 * This attribute contains all the causes which have caused the throwing of the current exception. In the a large
	 * part of the different cases, only one cause will be contained in this list. However, for multithreaded actions,
	 * this list could contain several causes.
	 */
	private List<Throwable> causes = new ArrayList<Throwable>();

	/**
	 * The constructor {@link #DeliveryException(ErrorCode)} instantiates a new {@link DeliveryException}. It only calls
	 * the corresponding parent constructor.
	 * 
	 * @param errorCode
	 *            The error code to set to this exception.
	 */
	public DeliveryException(ErrorCode errorCode) {

		// calling the right constructor
		super(errorCode, (Object[]) null);
	}

	/**
	 * The constructor {@link #DeliveryException(ErrorCode, Object[])} instantiates a new {@link DeliveryException}. It
	 * only calls the corresponding parent constructor.
	 * 
	 * @param errorCode
	 *            The error code to set to this exception.
	 * @param arguments
	 *            The values to set on the exception message.
	 */
	public DeliveryException(ErrorCode errorCode, Object[] arguments) {

		// calling the right constructor
		super(errorCode, arguments, (Throwable) null);
	}

	/**
	 * The constructor {@link #DeliveryException(ErrorCode, Throwable)} instantiates a new {@link DeliveryException}. It
	 * only calls the corresponding parent constructor.
	 * 
	 * @param errorCode
	 *            The error code to set to this exception.
	 * @param cause
	 *            The cause which is the origin of this exception.
	 */
	public DeliveryException(ErrorCode errorCode, Throwable cause) {

		// call the right constructor
		super(errorCode, (Object[]) null);

		if (cause != null) {
			this.causes.add(cause);
		}
	}

	/**
	 * The constructor {@link #DeliveryException(ErrorCode, List)} instantiates a new {@link DeliveryException}. It only
	 * calls the corresponding parent constructor, managing correctly the different received causes.
	 * 
	 * @param errorCode
	 *            The error code to set to this exception.
	 * @param causes
	 *            The causes which are the origin of this exception.
	 */
	public DeliveryException(ErrorCode errorCode, List<Throwable> causes) {

		// calling the right constructor
		super(errorCode, (Object[]) null);

		if (causes != null) {
			this.causes = causes;
		}
	}

	/**
	 * The constructor {@link #DeliveryException(ErrorCode, Object[], Throwable)} instantiates a new
	 * {@link DeliveryException}. It only calls the corresponding parent constructor.
	 * 
	 * @param errorCode
	 *            The error code to set to this exception.
	 * @param arguments
	 *            The values to set on the exception message.
	 * @param cause
	 *            The cause which is the origin of this exception.
	 */
	public DeliveryException(ErrorCode errorCode, Object[] arguments, Throwable cause) {

		// calling the right constructor
		super(errorCode, arguments);

		if (cause != null) {
			this.causes.add(cause);
		}
	}

	/**
	 * The constructor {@link #DeliveryException(ErrorCode, Object[], List)} instantiates a new
	 * {@link DeliveryException}. It only calls the corresponding parent constructor, managing correctly the different
	 * received causes.
	 * 
	 * @param errorCode
	 *            The error code to set to this exception.
	 * @param arguments
	 *            The values to set on the exception message.
	 * @param causes
	 *            The causes which are the origin of this exception.
	 */
	public DeliveryException(ErrorCode errorCode, Object[] arguments, List<Throwable> causes) {

		// calling the right constructor
		super(errorCode, arguments);

		if (causes != null) {
			this.causes = causes;
		}
	}

	/**
	 * The constructor {@link #DeliveryException(ErrorCode, String)} instantiates a new {@link DeliveryException}. It
	 * only calls the corresponding parent constructor.
	 * 
	 * @param errorCode
	 *            The error code to set to this exception.
	 * @param message
	 *            The message to set, despite of the one loaded thanks to the error code.
	 */
	public DeliveryException(ErrorCode errorCode, String message) {

		// calling the right constructor
		super(errorCode, message, (Throwable) null);
	}

	/**
	 * The constructor {@link #DeliveryException(ErrorCode, String, Throwable)} instantiates a new
	 * {@link DeliveryException}. It only calls the corresponding parent constructor.
	 * 
	 * @param errorCode
	 *            The error code to set to this exception.
	 * @param message
	 *            The message to set, despite of the one loaded thanks to the error code.
	 * @param cause
	 *            The cause which is the origin of this exception.
	 */
	public DeliveryException(ErrorCode errorCode, String message, Throwable cause) {

		// calling the super constructor
		super(errorCode, message);

		if (cause != null) {
			this.causes.add(cause);
		}
	}

	/**
	 * The constructor {@link #DeliveryException(NNException)} instantiates a new {@link DeliveryException}. It only
	 * calls the corresponding parent constructor.
	 * 
	 * @param cause
	 *            The cause which is the origin of this exception.
	 */
	public DeliveryException(NNException cause) {

		// calling the right constructor
		super(cause.getErrorCode(), cause.getMessage());

		if (cause != null) {
			this.causes.add(cause);
		}
	}

	/**
	 * The constructor {@link #DeliveryException(NNImplicitException)} instantiates a new {@link DeliveryException}. It
	 * only calls the corresponding parent constructor.
	 * 
	 * @param cause
	 *            The cause which is the origin of this exception.
	 */
	public DeliveryException(NNImplicitException cause) {

		// calling the right constructor
		super(cause.getErrorCode(), cause.getMessage());

		if (cause != null) {
			this.causes.add(cause);
		}
	}

	/**
	 * The method {@link #getCause()} returns the cause which is the origin of this current {@link DeliveryException}.
	 * As this class has been designed to manage multiple causes, the behavior of this method depends on the number of
	 * initialized causes:
	 * <ul>
	 * <li>no cause : the method returns null,</li>
	 * <li>one cause : the method returns the cause itself,</li>
	 * <li>several causes : the method returns a {@link Throwable} object, whose message results in the concatenation of
	 * all the message of all the owned causes.
	 * </ul>
	 * 
	 * @return The throwable cause.
	 */
	@Override
	public Throwable getCause() {

		switch (this.causes.size()) {
		case 0:
			return null;

		case 1:
			return this.causes.get(0);

		default:

			String message = "Multiple causes :\n";
			int indexCause = 1;

			for (Throwable currentCause : causes) {

				if (indexCause > 1) {
					message += "\n";
				}

				message += " - #" + indexCause + " : " + currentCause.getMessage();
				indexCause++;
			}

			return new Throwable(message);
		}
	}
}
