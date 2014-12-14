package de.galan.dmsexchange.exchange;

import de.galan.dmsexchange.meta.document.Document;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public interface DmsWriter extends AutoCloseable {

	public void addDocument(Document document);

}
