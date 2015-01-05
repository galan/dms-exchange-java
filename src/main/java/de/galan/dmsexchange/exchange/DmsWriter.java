package de.galan.dmsexchange.exchange;

import java.io.File;
import java.util.List;

import de.galan.dmsexchange.meta.document.Document;
import de.galan.dmsexchange.util.DmsExchangeException;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public interface DmsWriter extends AutoCloseable {

	/** Validates and adds documents to the export-archive */
	default void add(Document... documents) throws DmsExchangeException {
		for (Document doc: documents) {
			add(doc);
		}
	}


	/** Validates and adds a document to the export-archive */
	public void add(Document document) throws DmsExchangeException;


	/** Returns the list of export-archives files the DmsWriter has created during export. */
	public List<File> getFiles();

}
