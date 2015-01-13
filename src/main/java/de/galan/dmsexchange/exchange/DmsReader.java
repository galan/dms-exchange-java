package de.galan.dmsexchange.exchange;

import de.galan.dmsexchange.util.DmsExchangeException;


/**
 * Contract for an export-archive importer. Reading a dms-exchange export-archive consists of two simple steps:<br/>
 * <ul>
 * <li>Create the desired implementation, preferably using the DmsExchange factory methods</li>
 * <li>Create and register a listener</li>
 * <li>Call the <code>readDocuments()</code> methods</li>
 * </ul>
 *
 * @author daniel
 */
public interface DmsReader extends AutoCloseable {

	public void readDocuments() throws DmsExchangeException;


	public void registerListener(Object... listeners);

}
