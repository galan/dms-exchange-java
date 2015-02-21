package de.galan.dmsexchange.exchange.read;

import static de.galan.commons.test.Tests.*;
import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;

import de.galan.commons.test.AbstractTestParent;
import de.galan.commons.time.ApplicationClock;
import de.galan.dmsexchange.DmsExchange;
import de.galan.dmsexchange.exchange.DmsReader;
import de.galan.dmsexchange.test.TarTests;
import de.galan.dmsexchange.util.DmsExchangeException;


/**
 * Parent for DmsReader tests
 *
 * @author daniel
 */
public abstract class DmsReaderTestParent extends AbstractTestParent {

	private DmsReader reader;
	private File file;


	@Before
	public void before() {
		ApplicationClock.setUtc("2015-01-17T18:12:10Z");
	}


	@After
	public void after() throws Exception {
		if (getReader() != null) {
			getReader().close(); // ensure file is closed
			getReader().close(); // test multiple calls to close() should be safe
		}
		if (getFile().exists()) {
			assertThat(FileUtils.deleteQuietly(getFile())).isTrue();
		}
	}


	protected DocumentCollector readArchiveConsumer(String testcase) throws DmsExchangeException, IOException {
		prepareArchive(testcase);
		setReader(DmsExchange.createReader(getFile()));
		DocumentCollector collector = new DocumentCollector();
		getReader().readDocuments(collector::read);
		return collector;
	}


	protected DocumentCollector readArchive(String testcase) throws DmsExchangeException, IOException {
		return readArchive(testcase, true);
	}


	protected DocumentCollector readArchive(String testcase, boolean createReader) throws DmsExchangeException, IOException {
		prepareArchive(testcase);
		if (createReader) {
			setReader(DmsExchange.createReader(getFile()));
		}
		DocumentCollector collector = new DocumentCollector();
		getReader().registerListener(collector);
		getReader().readDocuments();
		return collector;
	}


	protected void prepareArchive(String testcase) throws IOException {
		URL url = getClass().getResource(getClass().getSimpleName() + "-" + testcase);
		File dirToPack = new File(url.getFile());
		setFile(new File(getTestDirectory(), "input.tgz"));
		TarTests.pack(dirToPack, getFile(), true);
	}


	public DmsReader getReader() {
		return reader;
	}


	public void setReader(DmsReader reader) {
		this.reader = reader;
	}


	public File getFile() {
		return file;
	}


	public void setFile(File file) {
		this.file = file;
	}

}
