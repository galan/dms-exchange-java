package de.galan.dmsexchange.exchange.read;

import java.io.File;

import de.galan.dmsexchange.exchange.DefaultExchange;
import de.galan.dmsexchange.exchange.DmsReader;
import de.galan.dmsexchange.util.DmsExchangeException;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class DefaultDmsReader extends DefaultExchange implements DmsReader {

	public DefaultDmsReader(File file) throws DmsExchangeException {
		super(file, true);
	}


	@Override
	public void readDocuments() {
		// check if listeners are registered (correct ones, otherwise DeadEvents will be send out)
		// iterate over directories recursivly
		// read found documents
		// inform listeners
		// close
	}


	@Override
	public void registerListener(Object... listeners) {
	}

}
