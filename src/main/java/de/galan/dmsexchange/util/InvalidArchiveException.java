package de.galan.dmsexchange.util;

/**
 * Exchange-Archive does not comply with the specification.
 */
public class InvalidArchiveException extends DmsExchangeException {

	public InvalidArchiveException(String message, Throwable cause) {
		super(message, cause);
	}


	public InvalidArchiveException(String message) {
		super(message);
	}

}
