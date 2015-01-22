package de.galan.dmsexchange.exchange.read;

import java.util.function.Consumer;

import com.google.common.eventbus.Subscribe;

import de.galan.dmsexchange.meta.document.Document;


/**
 * Passes readed documents to the consumer.
 *
 * @author daniel
 */
public class WrappingDocumentConsumer {

	private Consumer<Document> consumer;


	public WrappingDocumentConsumer(Consumer<Document> consumer) {
		this.consumer = consumer;
	}


	@Subscribe
	public void documentRead(Document doc) {
		consumer.accept(doc);
	}

}