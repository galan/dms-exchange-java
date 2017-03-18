package de.galan.dmsexchange.util;

/**
 * Base Exception for all exception thrown by the library
 */
public class DmsExchangeException extends Exception {

	public DmsExchangeException(String message, Throwable cause) {
		super(message, cause);
	}


	public DmsExchangeException(String message) {
		super(message);
	}


	public DmsExchangeException(Throwable cause) {
		super(cause);
	}

}
