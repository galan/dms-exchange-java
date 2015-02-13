package de.galan.dmsexchange.util.archive;

import java.io.IOException;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class TarWriter {

	protected void addEntry(TarArchiveOutputStream tar, byte[] data, String name) throws IOException {
		TarArchiveEntry entry = new TarArchiveEntry(name);
		entry.setSize(data.length);
		tar.putArchiveEntry(entry);
		tar.write(data);
		tar.closeArchiveEntry();
	}

}
