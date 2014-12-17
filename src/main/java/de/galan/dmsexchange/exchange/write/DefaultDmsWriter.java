package de.galan.dmsexchange.exchange.write;

import static org.apache.commons.lang3.StringUtils.*;

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

	private int counterBase = 0;
	private int counterContainer = 0;


	public DefaultDmsWriter(File file) throws DmsExchangeException {
		super(file, false);
		export = new Export();
	}


	@Override
	public void addDocument(Document document) throws DmsExchangeException {
		// validate document
		// append document to generated directory
		String nextDir = getNextContainerDirectory();
		try {
			String documentJson = getVerjsonDocument().writePlain(document);
			getZipFs().addFile(nextDir + "meta.json", documentJson.getBytes());
		}
		catch (IOException ex) {
			throw new DmsExchangeException("Unable to add document to export-archive", ex);
		}
	}


	protected String getNextContainerDirectory() {
		if (counterContainer > 9_999) {
			counterContainer = 0;
			counterBase++;
		}
		String dirBase = leftPad("" + counterBase++, 4, "0");
		String dirContainer = leftPad("" + counterContainer++, 4, "0");
		return "/" + dirBase + "/" + dirContainer + "/";
	}


	/** Closes the zip file and writes the export-meta data */
	@Override
	public void close() throws DmsExchangeException {
		// add export-meta
		writeExport();
		// close file
		closeZipFs();
	}


	protected void writeExport() throws DmsExchangeException {
		try {
			String exportJson = getVerjsonExport().writePlain(export);
			getZipFs().addFile("/export.json", exportJson.getBytes());
		}
		catch (IOException ex) {
			throw new DmsExchangeException("Unable to write export metadata", ex);
		}
	}


	protected void closeZipFs() throws DmsExchangeException {
		try {
			getZipFs().close();
		}
		catch (IOException ex) {
			throw new DmsExchangeException("Unable to close export-archive", ex);
		}
	}

}
