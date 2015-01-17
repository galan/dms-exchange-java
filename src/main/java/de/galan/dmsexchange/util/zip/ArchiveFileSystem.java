package de.galan.dmsexchange.util.zip;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.io.output.ByteArrayOutputStream;

import com.google.common.base.Charsets;


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
	default byte[] readFile(String file) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		readFile(file, baos);
		return baos.toByteArray();
	}


	/** Reads the binary content of a file, given the full path to the file */
	default String readFileAsString(String file) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		readFile(file, baos);
		return new String(baos.toByteArray(), Charsets.UTF_8);
	}


	/** Reads the binary content of a file, given the full path to the file */
	public void readFile(String file, OutputStream stream) throws IOException;

}
