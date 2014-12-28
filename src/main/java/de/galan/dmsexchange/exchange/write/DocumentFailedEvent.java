package de.galan.dmsexchange.exchange.write;

import de.galan.dmsexchange.exchange.ExchangeEvent;
import de.galan.dmsexchange.meta.ValidationResult;
import de.galan.dmsexchange.meta.document.Document;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class DocumentFailedEvent extends ExchangeEvent {

	private Document document;
	private ValidationResult validationResult;


	public DocumentFailedEvent(Document document, ValidationResult validationResult) {
		this.document = document;
		this.validationResult = validationResult;
	}


	public Document getDocument() {
		return document;
	}


	public ValidationResult getValidationResult() {
		return validationResult;
	}

}
