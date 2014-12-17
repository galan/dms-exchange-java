package de.galan.dmsexchange.exchange;

import de.galan.dmsexchange.meta.document.Document;
import de.galan.dmsexchange.util.DmsExchangeException;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public interface DmsWriter extends AutoCloseable {

	public void addDocument(Document document) throws DmsExchangeException;

}
