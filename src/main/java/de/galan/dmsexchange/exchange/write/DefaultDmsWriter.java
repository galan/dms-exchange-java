package de.galan.dmsexchange.exchange.write;

import java.io.File;
import java.io.IOException;

import de.galan.dmsexchange.exchange.DefaultExchange;
import de.galan.dmsexchange.exchange.DmsWriter;
import de.galan.dmsexchange.meta.document.Document;
import de.galan.dmsexchange.meta.export.Export;
import de.galan.dmsexchange.util.DmsExchangeException;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class DefaultDmsWriter extends DefaultExchange implements DmsWriter {

	private Export export;

	private int counterDirectory = 0;
	private int counterContainer = 0;


	public DefaultDmsWriter(File file) throws DmsExchangeException {
		super(file, false);
		export = new Export();
	}


	@Override
	public void addDocument(Document document) {
		// validate document
		// append document to generated directory
	}


	/** Closes the zip file and writes the export-meta data */
	@Override
	public void close() throws DmsExchangeException {
		// add export-meta
		writeExport();

		// close file
		try {
			getZipFs().close();
		}
		catch (IOException ex) {
			throw new DmsExchangeException("Unable to close export-archive", ex);
		}
	}


	protected void writeExport() throws DmsExchangeException {
		try {
			String exportJson = getVerjsonExport().writePlain(export);
			getZipFs().writeTextFile("/export.json", exportJson);
		}
		catch (IOException ex) {
			throw new DmsExchangeException("Unable to write export metadata", ex);
		}
	}

}
