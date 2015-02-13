package de.galan.dmsexchange.util.zip;

import static org.apache.commons.lang3.StringUtils.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;

import com.google.common.base.Charsets;

import de.galan.commons.logging.Logr;
import de.galan.commons.test.AbstractTestParent;


/**
 * Poc
 *
 * @author daniel
 */
public class GzipPocTest extends AbstractTestParent {

	/*
	tar must be
	- not sparse
	- conform with --format=pax (same as --format=posix)

	tar/gzip:
	+ streaming, O(1) memory
	+ speed, since no reading to memory first required
	+ filesize visible during processing
	- no index of files, resulting in no knowledge how much documents might follow
	- Order of entries important
	- streaming access

	zip:
	+ index - tree-traverse over files
	- memory, O(docs) memory consumption
	- speed, reading to memory prior  processing required
	- filesize not visible during processing
	+ seekable

	 */

	private static final Logger LOG = Logr.get();


	@Test
	@Ignore
	public void create() throws Exception {
		File file = new File("/home/daniel/temp/tartest-01/test-03.tar");
		TarArchiveOutputStream tar = new TarArchiveOutputStream(new FileOutputStream(file));

		TarArchiveEntry entry1 = new TarArchiveEntry("file-in-root.txt");
		byte[] input1 = "hello world".getBytes(Charsets.UTF_8);
		entry1.setSize(input1.length);
		tar.putArchiveEntry(entry1);
		tar.write(input1);
		tar.closeArchiveEntry();

		byte[] fileInput = FileUtils.readFileToByteArray(new File("/home/daniel/temp/tartest-01/Prüfungsordnung.pdf"));

		for (int i = 0; i < 10000; i++) {
			TarArchiveEntry entry2 = new TarArchiveEntry("" + leftPad("" + i, 4, "0") + "/Prüfungsordung.pdf");
			entry2.setSize(fileInput.length);
			tar.putArchiveEntry(entry2);
			tar.write(fileInput);
			tar.closeArchiveEntry();
			if (i % 100 == 0) {
				LOG.info("written {} files", i);
			}
		}

		tar.close(); // finally
	}


	@Test
	public void read() throws Exception {
		File file = new File("/home/daniel/temp/tartest-01/test-03.tar");
		TarArchiveInputStream tar = new TarArchiveInputStream(new FileInputStream(file));

		int i = 0;
		TarArchiveEntry entry = null;
		while((entry = tar.getNextTarEntry()) != null) {
			if (!entry.isDirectory()) {
				byte[] baos = IOUtils.toByteArray(tar);
				//LOG.info("file: {}, content: {}", entry.getName(), new String(baos));
				if (++i % 100 == 0) {
					LOG.info("read {} files ({}/{})", i, entry.getName(), baos.length);
				}
			}
		}
		tar.close(); // finally
	}

}
