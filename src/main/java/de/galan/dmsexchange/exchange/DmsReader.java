package de.galan.dmsexchange.exchange;

import de.galan.dmsexchange.util.DmsExchangeException;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public interface DmsReader {

	public void readDocuments() throws DmsExchangeException;


	//public void readDocument(String path);

	public void registerListener(Object... listeners);

}
