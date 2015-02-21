package de.galan.dmsexchange.exchange.read;

import static de.galan.commons.test.Tests.*;
import static org.assertj.core.api.Assertions.*;

import java.io.File;
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
		setReader(DmsExchange.createReader(getFile()));
		DocumentCollector collector = new DocumentCollector();
		getReader().registerListener(collector);
		getReader().readDocuments();

		assertThat(collector.getDocuments()).containsOnly(documents.toArray(new Document[] {}));
	}


	@Test
	public void readArchiveMixedDirectoryAndTgz() throws Exception {
		List<Document> documents = Lists.newArrayList(Documents.createComplexDocument(), Documents.createSimpleDocument1(), Documents.createSimpleDocument2(),
			Documents.createSimpleDocument3(), Documents.createSimpleDocument4(), Documents.createSimpleDocument5());
		createArchive(documents);

		TarTests.explode(getFile(), true);

		TarTests.unexplode(new File(getFile(), "0000/0004"), false);
		FileUtils.moveFile(new File(getFile(), "0000/0004"), new File(getFile(), "doc-4-in-root.tar"));
		TarTests.unexplode(new File(getFile(), "0000/0005"), false);
		FileUtils.forceMkdir(new File(getFile(), "somewhere/deep/down/the/rabbit/hole"));
		FileUtils.moveFile(new File(getFile(), "0000/0005"), new File(getFile(), "somewhere/deep/down/the/rabbit/hole/doc-5.tar"));
		TarTests.unexplode(getFile(), true);

		readAndCompareDocuments(documents);
	}


	@Test
	public void archiveEmpty() throws Exception {
		DocumentCollector collector = readArchiveConsumer("archiveEmpty");
		assertThat(collector.getDocuments()).isEmpty();
	}


	@Test
	public void readArchiveTwice() throws Exception {
		DocumentCollector collector1 = readArchive("readArchiveSingleDirectory");
		assertThat(collector1.getDocuments()).hasSize(1);
		DocumentCollector collector2 = readArchive("readArchiveSingleDirectory", false);
		assertThat(collector2.getDocuments()).hasSize(1);
	}


	@Test
	public void readArchiveWronglyNested() throws Exception {
		List<Document> docsInput = Lists.newArrayList(Documents.createSimpleDocument1(), Documents.createSimpleDocument2(), Documents.createSimpleDocument3());
		createArchive(docsInput);

		TarTests.explode(getFile(), true);

		TarTests.unexplode(new File(getFile(), "0000/0001"), false);
		FileUtils.moveFile(new File(getFile(), "0000/0001"), new File(getFile(), "0000/0000/a-zip-in.zip")); // has to start with "a", since "a" is before "meta.json"
		FileUtils.moveDirectory(new File(getFile(), "0000/0002"), new File(getFile(), "0000/0000/container-in-container"));
		TarTests.unexplode(getFile(), true);

		List<Document> documents = Lists.newArrayList(Documents.createSimpleDocument1());

		readAndCompareDocuments(documents);
	}

}
