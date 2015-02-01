package de.galan.dmsexchange.exchange.read;

import de.galan.dmsexchange.exchange.ExchangeEvent;


/**
 * Reading a document with invalid name failed.
 *
 * @author daniel
 */
public class DocumentReadInvalidEvent extends ExchangeEvent {

	private String documentPath;


	public DocumentReadInvalidEvent(String documentPath) {
		this.documentPath = documentPath;
	}


	public String getDocumentPath() {
		return documentPath;
	}

}
