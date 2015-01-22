package de.galan.dmsexchange.exchange.read;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;


/**
 * CUT DefaultDmsReader
 *
 * @author daniel
 */
public class DefaultDmsReaderTest extends DmsReaderTestParent {

	@Test
	public void readArchiveSingleDirectoryDocument() throws Exception {
		DocumentCollector collector = readArchive("readArchiveSingleDirectory");
		assertThat(collector.getDocuments()).hasSize(2);
	}


	@Test
	public void readArchiveStream() throws Exception {
		DocumentCollector collector = readArchiveConsumer("readArchiveSingleDirectory");
		assertThat(collector.getDocuments()).hasSize(2);
	}


	@Test
	public void archiveEmpty() throws Exception {
		DocumentCollector collector = readArchiveConsumer("archiveEmpty");
		assertThat(collector.getDocuments()).isEmpty();
	}


	@Test
	public void readArchiveTwice() throws Exception {
		DocumentCollector collector1 = readArchive("readArchiveSingleDirectory");
		assertThat(collector1.getDocuments()).hasSize(2);
		DocumentCollector collector2 = readArchive("readArchiveSingleDirectory", false);
		assertThat(collector2.getDocuments()).hasSize(2);
	}

}
