package de.galan.dmsexchange.util.zip;

import static org.apache.commons.lang3.StringUtils.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
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
public class ZipPocTest extends AbstractTestParent {

	private static final Logger LOG = Logr.get();


	@Ignore
	@Test
	public void create() throws Exception {
		File file = new File("/home/daniel/temp/tartest-02/test-01.zip");

		ZipArchiveOutputStream zip = new ZipArchiveOutputStream(new FileOutputStream(file));
		zip.setUseZip64(Zip64Mode.Always);

		ZipArchiveEntry entry1 = new ZipArchiveEntry("file-in-root.txt");
		byte[] input1 = "hello world".getBytes(Charsets.UTF_8);
		entry1.setSize(input1.length);
		zip.putArchiveEntry(entry1);
		zip.write(input1);
		zip.closeArchiveEntry();

		byte[] fileInput = FileUtils.readFileToByteArray(new File("/home/daniel/temp/tartest-01/Prüfungsordnung.pdf"));

		for (int i = 0; i < 40000; i++) {
			ZipArchiveEntry entry2 = new ZipArchiveEntry("" + leftPad("" + i, 4, "0") + "/Prüfungsordung.pdf");
			entry2.setSize(fileInput.length);
			zip.putArchiveEntry(entry2);
			zip.write(fileInput);
			zip.closeArchiveEntry();
			if (i % 100 == 0) {
				LOG.info("written {} files", i);
			}
		}

		zip.close(); // finally
	}


	@Test
	@Ignore
	public void readStreaming() throws Exception {
		File file = new File("/home/daniel/temp/tartest-02/test-01.zip");
		ZipArchiveInputStream zip = new ZipArchiveInputStream(new FileInputStream(file));

		Runtime runtime = Runtime.getRuntime();
		long mb = 1024 * 1024;

		int i = 0;
		ZipArchiveEntry entry = null;
		while((entry = zip.getNextZipEntry()) != null) {
			if (!entry.isDirectory()) {
				byte[] baos = IOUtils.toByteArray(zip);
				//LOG.info("file: {}, content: {}", entry.getName(), new String(baos));
				if (++i % 100 == 0) {
					//LOG.info("read {} files ({}/{})", i, entry.getName(), baos.length);
					long used = (runtime.totalMemory() - runtime.freeMemory()) / mb;
					long free = runtime.freeMemory() / mb;
					baos = null;
					//runtime.gc();
					//LOG.info("Memory: {}", used);
					LOG.info("Memory {}/{}", used, free);
					//Print total available memory
					//System.out.println("Total Memory:" + runtime.totalMemory() / mb);

					//Print Maximum available memory
					//System.out.println("Max Memory:" + runtime.maxMemory() / mb);
				}
			}
		}
		zip.close(); // finally
	}

}
