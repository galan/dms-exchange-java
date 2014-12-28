package de.galan.dmsexchange.exchange.write;

import de.galan.dmsexchange.meta.ValidationResult;
import de.galan.dmsexchange.util.DmsExchangeException;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class DmsExchangeValidationException extends DmsExchangeException {

	private ValidationResult validationResult;


	public DmsExchangeValidationException(String message, ValidationResult validationResult) {
		super(message);
		this.validationResult = validationResult;
	}


	public DmsExchangeValidationException(Throwable cause, ValidationResult validationResult) {
		super(cause);
		this.validationResult = validationResult;
	}


	public DmsExchangeValidationException(String message, Throwable cause, ValidationResult validationResult) {
		super(message, cause);
		this.validationResult = validationResult;
	}


	public ValidationResult getValidationResult() {
		return validationResult;
	}

}
