package de.galan.dmsexchange.exchange;

import java.io.File;
import java.io.IOException;

import com.google.common.eventbus.EventBus;

import de.galan.dmsexchange.meta.export.Export;
import de.galan.dmsexchange.util.DmsExchangeException;
import de.galan.dmsexchange.util.ZipFileSystem;
import de.galan.dmsexchange.verjson.export.ExportVersions;
import de.galan.verjson.core.Verjson;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class DefaultExchange {

	private EventBus events;
	private File file;
	private ZipFileSystem zfs;

	private Verjson<Export> verjsonExport;


	protected DefaultExchange(File file, boolean readonly) throws DmsExchangeException {
		this.file = file;
		try {
			zfs = new ZipFileSystem(file, readonly);
		}
		catch (IOException ex) {
			throw new DmsExchangeException(ex.getMessage(), ex);
		}
		verjsonExport = Verjson.create(Export.class, new ExportVersions());
		events = new EventBus("dms-exchange"); // TODO include filename in name?
	}


	protected ZipFileSystem getZipFs() {
		return zfs;
	}


	protected Verjson<Export> getVerjsonExport() {
		return verjsonExport;
	}


	protected void registerListener(Object object) {
		events.register(object); // can throw IllegalArgumentException if no method with single-arg and @Subs
	}


	protected void postEvent(Object event) {
		events.post(event);
	}

}