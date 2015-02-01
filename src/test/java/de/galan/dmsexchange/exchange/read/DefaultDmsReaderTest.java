package de.galan.dmsexchange.exchange.read;

import static de.galan.commons.test.Tests.*;
import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

import de.galan.dmsexchange.DmsExchange;
import de.galan.dmsexchange.exchange.DmsWriter;
import de.galan.dmsexchange.meta.document.Document;
import de.galan.dmsexchange.test.Documents;
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
		createReadAndCompareListOfDocuments(documents);
	}


	@Test
	public void readArchiveMultipleDirectory() throws Exception {
		List<Document> documents = Lists.newArrayList(Documents.createSimpleDocument1(), Documents.createSimpleDocument2(), Documents.createSimpleDocument3(),
			Documents.createSimpleDocument4(), Documents.createSimpleDocument5());
		createReadAndCompareListOfDocuments(documents);
	}


	protected void createReadAndCompareListOfDocuments(List<Document> documents) throws DmsExchangeException, Exception {
		setFile(new File(getTestDirectory(), "input.zip"));
		DmsWriter writer = DmsExchange.createWriter(getFile());
		writer.add(documents);
		writer.close();

		setReader(DmsExchange.createReader(getFile()));
		DocumentCollector collector = new DocumentCollector();
		getReader().registerListener(collector);
		getReader().readDocuments();

		assertThat(collector.getDocuments()).containsOnly(documents.toArray(new Document[] {}));
	}


	/*
	DocumentCollector collector = readArchive("readArchiveSingleDirectory");
	assertThat(collector.getDocuments()).hasSize(1);

	Document document = collector.getDocuments().get(0);
	 */
	/*
	Verjson<Document> verjson = Verjson.create(Document.class, new DocumentVersions());
	String directoryBase = getClass().getSimpleName() + "-" + "readArchiveSingleDirectory" + "/";
	JsonNode node = verjson.readTree(readFile(getClass(), directoryBase + "0000/0000/meta.json"));
	Document expected = verjson.readPlain(node, Version.getVerjson(Version.SUPPORTED_VERSION));
	assertThat(document.getDirectory()).isEqualTo(expected.getDirectory());
	//assertThat(document.get).isEqualToComparingFieldByField(expected);
	 */

	@Test
	public void readArchiveStream() throws Exception {
		DocumentCollector collector = readArchiveConsumer("readArchiveSingleDirectory");
		assertThat(collector.getDocuments()).hasSize(1);
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

}
