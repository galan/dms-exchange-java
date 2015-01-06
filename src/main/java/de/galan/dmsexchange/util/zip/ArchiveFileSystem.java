package de.galan.dmsexchange.util.zip;

import java.io.Closeable;
import java.io.IOException;


/**
 * Contract for accessing archives.
 *
 * @author daniel
 */
public interface ArchiveFileSystem extends Closeable {

	public void addFile(String filename, byte[] data) throws IOException;

}
