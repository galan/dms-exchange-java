package de.galan.dmsexchange.exchange.read;

import com.google.common.eventbus.Subscribe;

import de.galan.dmsexchange.meta.Document;


/**
 * Counts the documents read
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
