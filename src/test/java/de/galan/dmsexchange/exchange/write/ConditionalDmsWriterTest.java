package de.galan.dmsexchange.exchange.write;

import static de.galan.commons.test.Tests.*;
import static org.assertj.core.api.Assertions.*;

import java.io.File;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;

import de.galan.commons.logging.Logr;
import de.galan.dmsexchange.DmsExchange;
import de.galan.dmsexchange.test.Documents;


/**
 * CUT ConditionalDmsWriter
 *
 * @author daniel
 */
public class ConditionalDmsWriterTest extends DmsWriterTestParent {

	private static final Logger LOG = Logr.get();


	@Before
	public void beforeConditional() {
		setFile(new File(getTestDirectory(true), "conditional"));
		getFile().mkdirs();
	}


	@Test
	public void createEmptyArchive() throws Exception {
		setWriter(DmsExchange.createWriter(getFile(), 2));
		getWriter().close();
		assertArchiveList("createEmptyArchive", 1);
	}


	protected void assertArchiveList(String testcase, int expectedArchives) {
		assertThat(getWriter().getFiles()).hasSize(expectedArchives);
		for (int i = 0; i < getWriter().getFiles().size() - 1; i++) {
			LOG.info("Testing archive {}: {}", i, getWriter().getFiles().get(i));
			assertArchive(testcase + "-" + i, getWriter().getFiles().get(i));
		}
	}


	@Test
	public void createArchiveSplitAfter2Documents2() throws Exception {
		setWriter(DmsExchange.createWriter(getFile(), 2));
		getWriter().addQuietly(Documents.createSimpleDocument1(), Documents.createSimpleDocument2());
		getWriter().close();
		assertArchiveList("createArchiveSplitAfter2Documents2", 1);
	}


	@Test
	public void createArchiveSplitAfter2Documents3() throws Exception {
		setWriter(DmsExchange.createWriter(getFile(), 2));
		getWriter().addQuietly(Documents.createSimpleDocument1(), Documents.createSimpleDocument2(), Documents.createSimpleDocument3());
		getWriter().close();
		assertArchiveList("createArchiveSplitAfter2Documents3", 2);
	}


	@Test
	@Ignore
	public void createArchiveSplitAfter30KDocuments11K() throws Exception {
		//TODO setWriter(DmsExchange.createWriter(getFile(), null, 1024 * 30)); // TODO unable to split  when underlying archive gets not flushed (and can not be manually)
		getWriter().addQuietly(Documents.createSimpleDocument1(), Documents.createSimpleDocument2(), Documents.createSimpleDocument3(),
			Documents.createSimpleDocument4(), Documents.createSimpleDocument5());
		getWriter().close();
		assertArchiveList("createArchiveSplitAfter10KDocuments11K", 1);
	}


	@Test
	public void createArchiveSplitAfterLoop() throws Exception {
		setWriter(DmsExchange.createWriter(getFile(), null, 1024 * 30));
		for (int i = 0; i < 10_000; i++) {
			getWriter().addQuietly(Documents.createComplexDocument());
		}
		getWriter().close();
		assertArchiveList("createArchiveSplitAfter10KDocuments11K", 1);
	}

}
