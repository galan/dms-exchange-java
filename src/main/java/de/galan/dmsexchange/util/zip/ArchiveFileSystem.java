package de.galan.dmsexchange.util.zip;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;


/**
 * Contract for accessing archives.
 *
 * @author daniel
 */
public interface ArchiveFileSystem extends Closeable {

	/**
	 * Adds a file with the given data to zip file. The file has to include the path to the file, a directory will be
	 * created if it does not exists. Root starts with a slash.
	 */
	public void addFile(String file, byte[] data) throws IOException;


	/**
	 * List the elements inside a directory. If an element is a directory, it ends with a slash. Root starts with a
	 * slash.
	 */
	public List<String> listFiles(String directory) throws IOException;


	/** Reads the binary content of a file, given the full path to the file */
	public byte[] readFile(String file) throws IOException;

}
