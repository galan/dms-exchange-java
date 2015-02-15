package de.galan.dmsexchange.exchange;

import java.io.File;
import java.util.List;

import de.galan.dmsexchange.meta.Document;
import de.galan.dmsexchange.util.DmsExchangeException;


/**
 * Creation of the export-archive as well as adding document-containers to it. Working with a DmsWriter Implementation
 * consists of the following steps:<br>
 * <ul>
 * <li>Create the desired implementation, preferably using the DmsExchange factory methods</li>
 * <li>Adding Documents thru the <code>add(..)</code> or <code>addQuietly(..)</code> methods</li>
 * <li>Closing the Writer using <code>close()</code></li>
 * <li>If the file hasn't been passed directly, or the export-archive consists of multiple files, you receive the
 * created export-archives using the <code>getFiles()</code> method</li>
 * </ul>
 *
 * @author daniel
 */
public interface DmsWriter extends AutoCloseable {

	/**
	 * Validates and adds documents to the export-archive. Will abort on the first invalid document, an alternative is
	 * using addQuiet(..) which swallows the Exceptions.
	 */
	default void add(Document... documents) throws DmsExchangeException {
		for (Document doc: documents) {
			add(doc);
		}
	}


	/**
	 * Validates and adds documents to the export-archive. Will abort on the first invalid document, an alternative is
	 * using addQuiet(..) which swallows the Exceptions.
	 */
	default void add(List<Document> documents) throws DmsExchangeException {
		for (Document doc: documents) {
			add(doc);
		}
	}


	/**
	 * Validates and adds documents to the export-archive. Will swallow quietly the DmsExchangeExceptions, errormessages
	 * will be added to the 'documentsFailed'-list.
	 */
	default void addQuietly(Document... documents) {
		for (Document doc: documents) {
			try {
				add(doc);
			}
			catch (DmsExchangeException ex) {
				// nada
			}
		}
	}


	/**
	 * Validates and adds a document to the export-archive. If the validation fails, the errormessage will be added to
	 * the 'documentsFailed'-list before throwing a DmsExchangeException.
	 */
	public void add(Document document) throws DmsExchangeException;


	/** Returns the list of export-archives files the DmsWriter has created during export. */
	public List<File> getFiles();

}
