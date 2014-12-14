package de.galan.dmsexchange.exchange;

/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public interface DmsReader {

	public void readDocuments();


	//public void readDocument(String path);

	public void registerListener(Object... listeners);

}
