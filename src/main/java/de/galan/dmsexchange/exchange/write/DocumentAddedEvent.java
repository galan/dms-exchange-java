package de.galan.dmsexchange.exchange.write;

import de.galan.dmsexchange.exchange.ExchangeEvent;
import de.galan.dmsexchange.meta.document.Document;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class DocumentAddedEvent extends ExchangeEvent {

	private Document document;


	public DocumentAddedEvent(Document document) {
		this.document = document;
	}


	public Document getDocument() {
		return document;
	}

}
