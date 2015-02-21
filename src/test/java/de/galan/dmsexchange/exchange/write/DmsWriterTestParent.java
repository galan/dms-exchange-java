package de.galan.dmsexchange.exchange.write;

import static de.galan.commons.test.Tests.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;

import de.galan.commons.test.AbstractTestParent;
import de.galan.commons.time.ApplicationClock;
import de.galan.dmsexchange.exchange.DmsWriter;
import de.galan.dmsexchange.test.TarTests;


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


	protected void assertArchive(String testcase) throws IOException {
		assertArchive(testcase, getFile());
	}


	protected void assertArchive(String testcase, File archiveFile) throws IOException {
		assertThat(archiveFile).exists();
		URL url = getClass().getResource(getClass().getSimpleName() + "-" + testcase);
		File dirToPack = new File(url.getFile());
		File expectedArchive = new File(getTestDirectory(), "expected.tgz");
		TarTests.pack(dirToPack, expectedArchive, true);
		assertTrue("Export-Archive is not identical to expected one", TarTests.archiveEquals(expectedArchive, archiveFile));
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
