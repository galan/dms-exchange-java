package de.galan.dmsexchange.exchange.read;

import static de.galan.commons.test.Tests.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

import de.galan.dmsexchange.meta.document.Document;
import de.galan.dmsexchange.util.Version;
import de.galan.dmsexchange.verjson.document.DocumentVersions;
import de.galan.verjson.core.Verjson;


/**
 * CUT DefaultDmsReader
 *
 * @author daniel
 */
public class DefaultDmsReaderTest extends DmsReaderTestParent {

	@Test
	public void readArchiveSingleDirectoryDocument() throws Exception {
		DocumentCollector collector = readArchive("readArchiveSingleDirectory");
		assertThat(collector.getDocuments()).hasSize(1);

		Document document = collector.getDocuments().get(0);

		Verjson<Document> verjson = Verjson.create(Document.class, new DocumentVersions());
		String directoryBase = getClass().getSimpleName() + "-" + "readArchiveSingleDirectory" + "/";
		JsonNode node = verjson.readTree(readFile(getClass(), directoryBase + "0000/0000/meta.json"));
		Document expected = verjson.readPlain(node, Version.getVerjson(Version.SUPPORTED_VERSION));
		assertThat(document.getDirectory()).isEqualTo(expected.getDirectory());
		//assertThat(document.get).isEqualToComparingFieldByField(expected);
	}


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
