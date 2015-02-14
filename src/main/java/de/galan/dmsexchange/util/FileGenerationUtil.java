package de.galan.dmsexchange.util;

import java.io.File;
import java.util.UUID;


/**
 * Helper for generating or determing filenames.
 *
 * @author daniel
 */
public class FileGenerationUtil {

	/**
	 * Returns the given file if exists and represents a file, otherwise generate a filename and use given file as
	 * directory.
	 */
	public static File determineFile(File file) {
		File result = file;
		if (file == null) {
			throw new IllegalArgumentException("Invalid file (null)");
		}
		if (file.isDirectory()) {
			result = generateUniqueFilename(file);
		}
		else if (file.isFile()) {
			file.getParentFile().mkdirs();
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
