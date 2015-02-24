package de.galan.dmsexchange.exchange.write;

import static de.galan.commons.test.Tests.*;
import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import de.galan.commons.logging.Logr;
import de.galan.dmsexchange.DmsExchange;
import de.galan.dmsexchange.exchange.ConditionalDmsWriter;
import de.galan.dmsexchange.test.Documents;


/**
 * CUT ConditionalDmsWriter
 *
 * @author daniel
 */
public class DefaultConditionalDmsWriterTest extends DmsWriterTestParent {

	private static final Logger LOG = Logr.get();


	@Before
	public void beforeConditional() {
		setFile(new File(getTestDirectory(true), "conditional"));
		getFile().mkdirs();
	}


	protected ConditionalDmsWriter getConditional() {
		return (ConditionalDmsWriter)getWriter();
	}


	protected void assertArchiveList(String testcase, int expectedArchives) throws IOException {
		List<File> files = getConditional().getFiles();
		assertThat(files).hasSize(expectedArchives);
		for (int i = 0; i < files.size(); i++) {
			LOG.info("Testing archive {}: {}", i, files.get(i));
			assertArchive(testcase + "-" + i, files.get(i));
		}
	}


	@Test
	public void createEmptyArchive() throws Exception {
		setWriter(DmsExchange.createWriter(getFile(), 2));
		getWriter().close();
		assertArchiveList("createEmptyArchive", 1);
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
	public void createArchiveSplitAfter20KbDocuments4() throws Exception {
		setWriter(DmsExchange.createWriter(getFile(), null, 1024 * 20));
		getWriter().addQuietly(Documents.createComplexDocument(), Documents.createComplexDocument(), Documents.createSimpleDocument1());
		getWriter().close();
		assertArchiveList("createArchiveSplitAfter20KbDocuments4", 2);
	}

}
