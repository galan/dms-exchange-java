package de.galan.dmsexchange;

import java.io.File;

import de.galan.dmsexchange.exchange.DmsReader;
import de.galan.dmsexchange.exchange.DmsWriter;
import de.galan.dmsexchange.exchange.read.DefaultDmsReader;
import de.galan.dmsexchange.exchange.write.DefaultDmsWriter;
import de.galan.dmsexchange.exchange.write.DocumentAddedListener;
import de.galan.dmsexchange.exchange.write.DocumentFailedListener;
import de.galan.dmsexchange.util.DmsExchangeException;


/**
 * Simplified entry-point for DmsExchange use-cases.
 *
 * @author daniel
 */
public class DmsExchange {

	public static DmsReader createReader(File file) throws DmsExchangeException {
		return new DefaultDmsReader(file);
	}


	public static DmsWriter createWriter(File file) throws DmsExchangeException {
		DefaultDmsWriter writer = new DefaultDmsWriter(file);
		writer.registerListener(new DocumentAddedListener());
		writer.registerListener(new DocumentFailedListener());
		return writer;
	}

}
