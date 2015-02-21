package de.galan.dmsexchange.exchange;

import java.time.format.DateTimeFormatter;

import com.google.common.eventbus.EventBus;

import de.galan.dmsexchange.meta.Document;
import de.galan.dmsexchange.util.zip.ArchiveFileSystem;
import de.galan.dmsexchange.verjson.document.DocumentVersions;
import de.galan.verjson.core.Verjson;


/**
 * Base-class for dxs-based reader/writer.
 *
 * @author daniel
 */
public abstract class DefaultExchange {

	protected static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");

	private EventBus events;
	private ArchiveFileSystem afs;

	private Verjson<Document> verjsonDocument;


	protected DefaultExchange() {
		verjsonDocument = Verjson.create(Document.class, new DocumentVersions());
		events = new EventBus("dms-exchange"); // TODO include filename in name?
	}


	protected ArchiveFileSystem getFs() {
		return afs;
	}


	protected Verjson<Document> getVerjsonDocument() {
		return verjsonDocument;
	}


	public void registerListener(Object object) {
		events.register(object); // can throw IllegalArgumentException if no method with single-arg and @Subs
	}


	protected void unregisterListener(Object object) {
		events.unregister(object); // can throw IllegalArgumentException if no method with single-arg and @Subs
	}


	protected void postEvent(Object event) {
		events.post(event);
	}

	/*
	protected void closeZipFs() throws DmsExchangeException {
		try {
			getFs().close();
		}
		catch (IOException ex) {
			throw new DmsExchangeException("Unable to close export-archive", ex);
		}
	}
	 */

}
