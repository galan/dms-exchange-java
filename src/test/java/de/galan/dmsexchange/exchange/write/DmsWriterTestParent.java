package de.galan.dmsexchange.exchange.write;

import static de.galan.commons.test.Tests.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

import java.io.File;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.zeroturnaround.zip.ZipUtil;

import de.galan.commons.test.AbstractTestParent;
import de.galan.commons.time.ApplicationClock;
import de.galan.dmsexchange.exchange.DmsWriter;


/**
 * Parent for DmsWriter tests
 *
 * @author daniel
 */
public class DmsWriterTestParent extends AbstractTestParent {

	private DmsWriter writer;
	private File file;


	@Before
	public void before() {
		ApplicationClock.setUtc("2014-12-28T19:21:57Z");
	}


	@After
	public void after() throws Exception {
		if (getWriter() != null) {
			getWriter().close(); // ensure file is closed
			getWriter().close(); // test multiple calls to close() should be safe
		}
		if (getFile().exists()) {
			assertThat(FileUtils.deleteQuietly(getFile())).isTrue();
		}
	}


	protected void assertArchive(String testcase) {
		assertArchive(testcase, getFile());
	}


	protected void assertArchive(String testcase, File zipFile) {
		assertThat(zipFile).exists();
		URL url = getClass().getResource(getClass().getSimpleName() + "-" + testcase);
		File dirToPack = new File(url.getFile());
		File expectedArchive = new File(getTestDirectory(), "expected.zip");
		ZipUtil.pack(dirToPack, expectedArchive);
		assertTrue("Export-Archive is not identical to expected one", ZipUtil.archiveEquals(expectedArchive, zipFile));
	}


	public File getFile() {
		return file;
	}


	public void setFile(File file) {
		this.file = file;
	}


	public DmsWriter getWriter() {
		return writer;
	}


	public void setWriter(DmsWriter writer) {
		this.writer = writer;
	}

}
