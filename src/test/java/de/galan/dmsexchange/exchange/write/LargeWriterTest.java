package de.galan.dmsexchange.exchange.write;

import static de.galan.commons.test.Tests.*;

import java.io.File;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;

import de.galan.commons.logging.Logr;
import de.galan.dmsexchange.DmsExchange;
import de.galan.dmsexchange.test.Documents;
import de.galan.dmsexchange.util.DmsExchangeException;


/**
 * CUT DefaultDmsWriter
 *
 * @author daniel
 */
@Ignore
public class LargeWriterTest extends DmsWriterTestParent {

	private static final Logger LOG = Logr.get();

	private static final String EXPORT_FILENAME = "write.zip";


	@Before
	public void createWriter() throws DmsExchangeException {
		setFile(new File(getTestDirectory(true), EXPORT_FILENAME));
		setWriter(DmsExchange.createWriter(getFile()));
	}


	@Test
	public void createArchiveWithMultipleDocuments() throws Exception {
		for (int i = 0; i < 10_000; i++) {
			getWriter().add(Documents.createComplexDocument(), Documents.createSimpleDocument1(), Documents.createSimpleDocument2());
			LOG.info("Added {} documents", i * 3);
		}
		getWriter().close();
		//assertArchive("createArchiveWithMultipleDocuments");
	}

}