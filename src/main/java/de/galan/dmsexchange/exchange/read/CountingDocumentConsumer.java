package de.galan.dmsexchange.exchange.read;

import com.google.common.eventbus.Subscribe;

import de.galan.dmsexchange.meta.document.Document;


/**
 * Counts the documents read
 *
 * @author daniel
 */
public class CountingDocumentConsumer {

	private int countedDocuments = 0;


	@Subscribe
	public void count(Document doc) {
		countedDocuments++;
	}


	public int getCountedDocuments() {
		return countedDocuments;
	}

}
