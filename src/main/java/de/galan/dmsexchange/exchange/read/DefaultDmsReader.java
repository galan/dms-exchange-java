package de.galan.dmsexchange.exchange.read;

import java.io.File;
import java.util.Arrays;

import de.galan.dmsexchange.exchange.DefaultExchange;
import de.galan.dmsexchange.exchange.DmsReader;
import de.galan.dmsexchange.util.DmsExchangeException;


/**
 * Reads all document-containers inside the given export-archive. The read document-containers will be posted to the
 * registered listeners.
 *
 * @author daniel
 */
public class DefaultDmsReader extends DefaultExchange implements DmsReader {

	public DefaultDmsReader(File file) throws DmsExchangeException {
		super(file, true);
	}


	@Override
	public void readDocuments() throws DmsExchangeException {
		// TODO check if listeners are registered (correct ones, otherwise DeadEvents will be send out)

		// iterate over directories recursivly
		// read found documents
		// inform listeners
		// close
	}


	@Override
	public void registerListener(Object... listeners) {
		Arrays.asList(listeners).stream().forEach(this::registerListener);
	}

}
