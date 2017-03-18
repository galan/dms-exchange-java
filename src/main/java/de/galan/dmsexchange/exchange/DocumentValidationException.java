package de.galan.dmsexchange.exchange;

import static org.apache.commons.lang3.StringUtils.*;

import de.galan.dmsexchange.meta.ValidationResult;
import de.galan.dmsexchange.util.DmsExchangeException;


/**
 * Exception that has happened during the validation phase prior adding a document into an archive. It contains the
 * ValidationResult for further output.
 */
public class DocumentValidationException extends DmsExchangeException {

	private ValidationResult validationResult;


	public DocumentValidationException(String message, ValidationResult validationResult) {
		super(message);
		this.validationResult = validationResult;
	}


	public DocumentValidationException(Throwable cause, ValidationResult validationResult) {
		super(cause);
		this.validationResult = validationResult;
	}


	public DocumentValidationException(String message, Throwable cause, ValidationResult validationResult) {
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
