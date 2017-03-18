package de.galan.dmsexchange.util.archive;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;


/**
 * Common operations with tar streams
 */
public class TarUtil {

	// GzipCompressorOutputStream will be closed on TarArchiveOutputStream.close()
	//@SuppressWarnings("resource")
	public static TarArchiveOutputStream create(OutputStream outputstream, boolean compress) throws IOException {
		return new TarArchiveOutputStream(compress ? new GzipCompressorOutputStream(outputstream) : outputstream);
	}


	public static void addEntry(TarArchiveOutputStream tar, byte[] data, String name) throws IOException {
		TarArchiveEntry entry = new TarArchiveEntry(name);
		entry.setSize(data.length);
		tar.putArchiveEntry(entry);
		tar.write(data);
		tar.closeArchiveEntry();
		tar.flush();
	}

}
