package de.galan.dmsexchange;

import static de.galan.commons.test.Tests.*;
import static org.assertj.core.api.Assertions.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;
import de.galan.dmsexchange.exchange.DmsWriter;


/**
 * TODO test
 *
 * @author daniel
 */
public class DmsExchangeTest extends AbstractTestParent {

	private static final String EXPORT_FILENAME = "export.zip";
	private File file;


	@Before
	public void before() {
		file = new File(getTestDirectory(true), EXPORT_FILENAME);
	}


	@After
	public void after() {
		assertThat(file.delete()).isTrue();
	}


	@Test
	public void createEmptyArchive() throws Exception {
		DmsWriter writer = DmsExchange.createWriter(file);
		writer.close();
		assertThat(file).exists();
	}

}
