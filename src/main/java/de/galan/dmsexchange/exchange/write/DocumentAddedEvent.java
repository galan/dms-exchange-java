package de.galan.dmsexchange.exchange.write;

import de.galan.dmsexchange.exchange.ExchangeEvent;
import de.galan.dmsexchange.meta.Document;


/**
 * A document has been added to an export-archive.
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
