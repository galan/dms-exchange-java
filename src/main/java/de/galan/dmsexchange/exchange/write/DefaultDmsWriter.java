package de.galan.dmsexchange.exchange.write;

import static org.apache.commons.lang3.StringUtils.*;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.google.common.collect.ImmutableList;

import de.galan.dmsexchange.exchange.DefaultExchange;
import de.galan.dmsexchange.exchange.DmsWriter;
import de.galan.dmsexchange.meta.ValidationResult;
import de.galan.dmsexchange.meta.document.Document;
import de.galan.dmsexchange.meta.document.DocumentFile;
import de.galan.dmsexchange.meta.document.Revision;
import de.galan.dmsexchange.meta.export.Export;
import de.galan.dmsexchange.util.DmsExchangeException;
import de.galan.dmsexchange.util.FileGenerationUtil;


/**
 * Adds documents to a specified file. Using generated directories and document-container names inside the archive
 * during the process.
 *
 * @author daniel
 */
public class DefaultDmsWriter extends DefaultExchange implements DmsWriter {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");

	private Export export;

	private int counterBase = 0;
	private int counterContainer = 0;


	public DefaultDmsWriter(File file) throws DmsExchangeException {
		super(FileGenerationUtil.determineFile(file), false);
		export = new Export();
	}


	@Override
	public void add(Document document) throws DmsExchangeException {
		try {
			// validate document
			validateDocument(document);
			// add revisions and metadata to next generated directory
			String nextDir = getNextContainerDirectory();
			addRevisions(document, nextDir);
			addMetadata(document, nextDir);
		}
		catch (DmsExchangeValidationException ex) {
			postEvent(new DocumentFailedEvent(document, ex.getValidationResult()));
			throw ex;
		}
		catch (DmsExchangeException ex) {
			postEvent(new DocumentFailedEvent(document, null));
			throw ex;
		}
	}


	protected void addMetadata(Document document, String nextDir) throws DmsExchangeException {
		try {
			//TODO avoid empty lists/arrays
			String documentJson = getVerjsonDocument().writePlain(document);
			getZipFs().addFile(nextDir + "meta.json", documentJson.getBytes());
			export.incrementDocumentsSuccessfulAmount();
			postEvent(new DocumentAddedEvent(document));
		}
		catch (IOException ex) {
			throw new DmsExchangeException("Unable to add document meta-data to export-archive", ex);
		}
	}


	protected void addRevisions(Document document, String nextDir) throws DmsExchangeException {
		try {
			for (DocumentFile df: document.getDocumentFiles()) {
				String filename = df.getFilename();
				for (Revision revision: df.getRevisions()) {
					String generated = revision.getTsAdded().format(FORMATTER) + "_" + filename;
					getZipFs().addFile(nextDir + "revisions/" + generated, revision.getData());
				}
			}
		}
		catch (IOException ex) {
			throw new DmsExchangeException("Unable to add revision to export-archive", ex);
		}
	}


	protected void validateDocument(Document document) throws DmsExchangeValidationException {
		ValidationResult result = document.validate();
		if (result.hasErrors()) {
			throw new DmsExchangeValidationException("Invalid Document", result);
		}
	}


	protected String getNextContainerDirectory() {
		if (counterContainer > 9999) {
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
		writeExport(); // add export-meta
		closeZipFs(); // close zip-file
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


	@Override
	public List<File> getFiles() {
		return ImmutableList.of(getFile());
	}

}
