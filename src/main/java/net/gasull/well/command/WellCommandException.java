package net.gasull.well.command;

/**
 * Exception happening on command invoking error.
 */
public class WellCommandException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The message. */
	private final String errorMessage;

	/**
	 * Instantiates a new well command exception.
	 * 
	 * @param message
	 *            the error message to send to the command sender
	 */
	public WellCommandException(String message) {
		this.errorMessage = message;
	}

	/**
	 * Gets the error message.
	 * 
	 * @return the error message
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
}
