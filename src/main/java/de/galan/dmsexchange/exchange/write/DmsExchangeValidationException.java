package de.galan.dmsexchange.exchange.write;

import static org.apache.commons.lang3.StringUtils.*;
import de.galan.dmsexchange.meta.ValidationResult;
import de.galan.dmsexchange.util.DmsExchangeException;


/**
 * Exception that has happened during the validation phase prior adding a document into an archive. It contains the
 * ValidationResult for further output.
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


	@Override
	public String getMessage() {
		String errors = EMPTY;
		if (getValidationResult() != null && getValidationResult().hasErrors()) {
			errors = " (" + getValidationResult().getErrorsJoined() + ")";
		}
		return super.getMessage() + errors;
	}

}
