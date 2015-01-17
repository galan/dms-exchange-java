package de.galan.dmsexchange.exchange.read;

import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.Subscribe;

import de.galan.dmsexchange.meta.document.Document;


/** Collects documents for testing purpose */
class DocumentCollector {

	List<Document> docs = new ArrayList<>();


	@Subscribe
	public void read(Document doc) {
		docs.add(doc);
	}


	public List<Document> getDocuments() {
		return docs;
	}

}
