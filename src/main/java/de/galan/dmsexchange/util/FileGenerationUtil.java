package de.galan.dmsexchange.util;

import java.io.File;
import java.util.UUID;

import org.apache.logging.log4j.Logger;

import de.galan.commons.logging.Logr;


/**
 * Helper for generating or determing filenames.
 *
 * @author daniel
 */
public class FileGenerationUtil {

	private static final Logger LOG = Logr.get();


	/**
	 * Returns the given file if exists and represents a file, otherwise generate a filename and use given file as
	 * directory.
	 */
	public static File prepareFile(File file) {
		File result = file;
		if (file == null) {
			throw new IllegalArgumentException("Invalid file (null)");
		}
		else if (file.isDirectory()) {
			throw new IllegalArgumentException("Invalid file (file can not be a directory)");
		}
		else if (!file.exists()) {
			file.getParentFile().mkdirs();
		}
		else if (file.isFile()) {
			LOG.info("File {} will be overwritten", file.getAbsolutePath());
		}
		return result;
	}


	/** Generates a unique filename (uuid) in the given directory. */
	public static File generateUniqueFilename(File directory) {
		if (directory == null || directory.isFile()) {
			throw new IllegalArgumentException("Invalid directory ('" + directory + "')");
		}
		if (directory.exists() && directory.isDirectory()) {
			directory.mkdirs();
		}
		return new File(directory, UUID.randomUUID().toString() + ".tgz");
	}

}
