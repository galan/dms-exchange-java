package de.galan.dmsexchange.exchange;

import com.google.common.eventbus.EventBus;

import de.galan.dmsexchange.meta.Document;
import de.galan.dmsexchange.verjson.document.DocumentVersions;
import de.galan.verjson.core.Verjson;


/**
 * Base-class for reader and writer.
 *
 * @author daniel
 */
public abstract class DefaultExchange {

	private EventBus events;
	private Verjson<Document> verjsonDocument;


	protected DefaultExchange() {
		verjsonDocument = Verjson.create(Document.class, new DocumentVersions());
		events = new EventBus("dms-exchange");
	}


	protected Verjson<Document> getVerjsonDocument() {
		return verjsonDocument;
	}


	public void registerListener(Object object) {
		events.register(object); // can throw IllegalArgumentException if no method with single-arg and @Subscribe
	}


	protected void unregisterListener(Object object) {
		events.unregister(object); // can throw IllegalArgumentException if no method with single-arg and @Subscribe
	}


	protected void postEvent(Object event) {
		events.post(event);
	}

}
