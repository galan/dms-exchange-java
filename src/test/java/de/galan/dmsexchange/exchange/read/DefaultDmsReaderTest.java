package de.galan.dmsexchange.exchange.read;

import static de.galan.commons.test.Tests.*;
import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.google.common.collect.Lists;

import de.galan.dmsexchange.DmsExchange;
import de.galan.dmsexchange.exchange.DmsWriter;
import de.galan.dmsexchange.meta.Document;
import de.galan.dmsexchange.test.Documents;
import de.galan.dmsexchange.test.TarTests;
import de.galan.dmsexchange.util.DmsExchangeException;


/**
 * CUT DefaultDmsReader
 *
 * @author daniel
 */
public class DefaultDmsReaderTest extends DmsReaderTestParent {

	@Test
	public void readArchiveSingleDirectory() throws Exception {
		List<Document> documents = Lists.newArrayList(Documents.createSimpleDocument1());
		createArchive(documents);
		readAndCompareDocuments(documents);
	}


	@Test
	public void readArchiveMultipleDirectory() throws Exception {
		List<Document> documents = Lists.newArrayList(Documents.createSimpleDocument1(), Documents.createSimpleDocument2(), Documents.createSimpleDocument3(),
			Documents.createSimpleDocument4(), Documents.createSimpleDocument5(), Documents.createComplexDocument());
		createArchive(documents);
		readAndCompareDocuments(documents);
	}


	protected void createArchive(List<Document> documents) throws DmsExchangeException, Exception {
		setFile(new File(getTestDirectory(), "input.tgz"));
		DmsWriter writer = DmsExchange.createWriter(getFile());
		writer.add(documents);
		writer.close();
	}


	protected void readAndCompareDocuments(List<Document> documents) throws DmsExchangeException {
		readAndCompareDocuments(documents, true);
	}


	protected void readAndCompareDocuments(List<Document> documents, boolean createReader) throws DmsExchangeException {
		if (createReader) {
			setReader(DmsExchange.createReader(getFile()));
		}
		DocumentCollector collector = new DocumentCollector();
		getReader().registerListener(collector);
		getReader().readDocuments();

		assertThat(collector.getDocuments()).containsOnly(documents.toArray(new Document[] {}));
	}


	@Test
	public void archiveEmpty() throws Exception {
		DocumentCollector collector = readArchiveConsumer("archiveEmpty");
		assertThat(collector.getDocuments()).isEmpty();
	}


	@Test
	public void readArchiveTwice() throws Exception {
		List<Document> documents = Lists.newArrayList(Documents.createSimpleDocument1());
		createArchive(documents);

		readAndCompareDocuments(documents, true);
		try {
			readAndCompareDocuments(documents, false); // inputstream has already been slurped
		}
		catch (DmsExchangeException ex) {
			assertThat(ex).hasMessage("Unable to read container tar");
			assertThat(ex).hasCauseInstanceOf(IOException.class);
			assertThat(ex.getCause()).hasMessage("Stream Closed");
		}
	}


	@Test
	public void readArchiveWronglyNested() throws Exception {
		List<Document> docsInput = Lists.newArrayList(Documents.createSimpleDocument1(), Documents.createSimpleDocument2(), Documents.createSimpleDocument3(),
			Documents.createSimpleDocument4(), Documents.createSimpleDocument5());
		createArchive(docsInput);

		TarTests.explode(getFile(), true);

		// flat directory, name ends with tar but is directory
		TarTests.explode(new File(getFile(), "0000/0000/0001.tar"), false);

		// Add container in container (tar and flat)
		TarTests.explode(new File(getFile(), "0000/0000/0002.tar"), false);
		TarTests.explode(new File(getFile(), "0000/0000/0004.tar"), false);
		FileUtils.moveDirectory(new File(getFile(), "0000/0000/0002.tar"), new File(getFile(), "0000/0000/0004.tar/flat-in-container"));
		FileUtils.moveFile(new File(getFile(), "0000/0000/0003.tar"), new File(getFile(), "0000/0000/0004.tar/packed-in-container.tar"));
		TarTests.unexplode(new File(getFile(), "0000/0000/0004.tar"), false);

		TarTests.unexplode(getFile(), true);

		List<Document> documents = Lists.newArrayList(Documents.createSimpleDocument1());

		readAndCompareDocuments(documents);
	}

}
