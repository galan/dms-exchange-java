package de.galan.dmsexchange.exchange;

import java.util.function.Consumer;

import de.galan.dmsexchange.meta.Document;
import de.galan.dmsexchange.util.DmsExchangeException;


/**
 * Contract for an export-archive importer. Reading an export-archive consists of two simple steps:<br/>
 * <ul>
 * <li>Create the desired implementation, preferably using the DmsExchange factory methods</li>
 * <li>Create and register a listener - or - provide a consumer (which registers a subscriber in the background)</li>
 * <li>Call the <code>readDocuments()</code> methods</li>
 * </ul>
 *
 * Potential event subscriber consumer either {@link Document}s or DocumentReadInvalidEvent (in case of beeing unable to
 * read content from an export-archive). Take a look at the WrappingDocumentConsumer for an example how to implement an
 * event subscriber.
 */
public interface DmsReader {

	/**
	 * Starts reading the provided export-archive and fires the appropiate events.<br/>
	 * <br/>
	 * Potential event subscriber consumer either {@link Document}s or DocumentReadInvalidEvent (in case of beeing
	 * unable to read content from an export-archive). Take a look at the WrappingDocumentConsumer for an example how to
	 * implement an event subscriber.
	 *
	 * @throws DmsExchangeException Encapsulating parent Exception for erroneous behaviour
	 */
	public void readDocuments() throws DmsExchangeException;


	/**
	 * Starts reading the provided export-archive and calls the consumer for every successful {@link Document} that is
	 * read.
	 *
	 * @throws DmsExchangeException Encapsulating parent Exception for erroneous behaviour
	 */
	public void readDocuments(Consumer<Document> consumer) throws DmsExchangeException;


	/**
	 * Registers an event subscriber.<br/>
	 * <br/>
	 * Potential event subscriber consumer either {@link Document}s or DocumentReadInvalidEvent (in case of beeing
	 * unable to read content from an export-archive). Take a look at the WrappingDocumentConsumer for an example how to
	 * implement an event subscriber.
	 *
	 * @param subscriber An event subscriber that is registered to the (Guava) EventBus
	 */
	public void registerSubscriber(Object... subscriber);

}
