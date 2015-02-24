package de.galan.dmsexchange.exchange;

import java.util.List;

import de.galan.dmsexchange.meta.Document;
import de.galan.dmsexchange.util.DmsExchangeException;


/**
 * Creation of the export-archive as well as adding {@link Document}s (container) to it. Working with a DmsWriter
 * Implementation consists of the following steps:<br>
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
	 *
	 * @param documents The documents to add, fails on the first erroneous {@link Document}
	 * @throws DmsExchangeException Encapsulating parent Exception for erroneous behaviour
	 */
	default void add(Document... documents) throws DmsExchangeException {
		for (Document doc: documents) {
			add(doc);
		}
	}


	/**
	 * Validates and adds documents to the export-archive. Will abort on the first invalid document, an alternative is
	 * using addQuiet(..) which swallows the Exceptions.
	 *
	 * @param documents The documents to add, fails on the first erroneous {@link Document}
	 * @throws DmsExchangeException Encapsulating parent Exception for erroneous behaviour
	 */
	default void add(List<Document> documents) throws DmsExchangeException {
		for (Document doc: documents) {
			add(doc);
		}
	}


	/**
	 * Validates and adds documents to the export-archive. Will swallow quietly the DmsExchangeExceptions
	 *
	 * @param documents Documents to add, does not fail if a Document is erroneous.
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
	 * Validates and adds a {@link Document} to the export-archive.
	 *
	 * @throws DmsExchangeException Encapsulating parent Exception for erroneous behaviour
	 */
	public void add(Document document) throws DmsExchangeException;

}
