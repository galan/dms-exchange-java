package de.galan.dmsexchange.exchange;

import java.io.File;
import java.io.IOException;

import com.google.common.eventbus.EventBus;

import de.galan.dmsexchange.meta.document.Document;
import de.galan.dmsexchange.meta.export.Export;
import de.galan.dmsexchange.util.DmsExchangeException;
import de.galan.dmsexchange.util.zip.ArchiveFileSystem;
import de.galan.dmsexchange.util.zip.NioZipFileSystem;
import de.galan.dmsexchange.verjson.document.DocumentVersions;
import de.galan.dmsexchange.verjson.export.ExportVersions;
import de.galan.verjson.core.Verjson;


/**
 * Base-class for dxs-based reader/writer.
 *
 * @author daniel
 */
public abstract class DefaultExchange implements AutoCloseable {

	private EventBus events;
	private File file;
	private ArchiveFileSystem afs;
	private boolean closed = false;

	private Verjson<Export> verjsonExport;
	private Verjson<Document> verjsonDocument;


	protected DefaultExchange(File file, boolean readonly) throws DmsExchangeException {
		this.file = file;
		try {
			afs = new NioZipFileSystem(file, readonly);
			//afs = new ZtZipFileSystem(file, readonly);
		}
		catch (IOException ex) {
			throw new DmsExchangeException(ex.getMessage(), ex);
		}
		verjsonExport = Verjson.create(Export.class, new ExportVersions());
		verjsonDocument = Verjson.create(Document.class, new DocumentVersions());
		events = new EventBus("dms-exchange"); // TODO include filename in name?
	}


	protected ArchiveFileSystem getFs() {
		return afs;
	}


	protected Verjson<Export> getVerjsonExport() {
		return verjsonExport;
	}


	protected Verjson<Document> getVerjsonDocument() {
		return verjsonDocument;
	}


	public void registerListener(Object object) {
		events.register(object); // can throw IllegalArgumentException if no method with single-arg and @Subs
	}


	protected void postEvent(Object event) {
		events.post(event);
	}


	protected File getFile() {
		return file;
	}


	/** Closes and releases the access to the zip file. */
	@Override
	public void close() throws DmsExchangeException {
		if (!isClosed()) {
			closeZipFs(); // close zip-file
			closed = true;
		}
	}


	protected boolean isClosed() {
		return closed;
	}


	protected void closeZipFs() throws DmsExchangeException {
		try {
			getFs().close();
		}
		catch (IOException ex) {
			throw new DmsExchangeException("Unable to close export-archive", ex);
		}
	}

}
