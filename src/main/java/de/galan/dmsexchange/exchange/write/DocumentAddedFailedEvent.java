package de.galan.dmsexchange.exchange.write;

import static org.apache.commons.lang3.StringUtils.*;

import com.google.common.base.Joiner;

import de.galan.dmsexchange.exchange.ExchangeEvent;
import de.galan.dmsexchange.meta.Document;
import de.galan.dmsexchange.meta.ValidationResult;


/**
 * Adding a document failed due to eg. validation.
 */
public class DocumentAddedFailedEvent extends ExchangeEvent {

	private Document document;
	private ValidationResult validationResult;
	private Exception reason;


	public DocumentAddedFailedEvent(Document document, ValidationResult validationResult) {
		this(document, validationResult, null);
	}


	public DocumentAddedFailedEvent(Document document, ValidationResult validationResult, Exception reason) {
		this.document = document;
		this.validationResult = validationResult;
		this.reason = reason;
	}


	public Document getDocument() {
		return document;
	}


	public ValidationResult getValidationResult() {
		return validationResult;
	}


	public Exception getReason() {
		return reason;
	}


	public String getErrors() {
		return (getValidationResult() == null) ? EMPTY : Joiner.on(", ").skipNulls().join(getValidationResult().getErrors());
	}

}
