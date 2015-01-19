package de.galan.dmsexchange.exchange.read;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import de.galan.dmsexchange.meta.document.DocumentFile;


/**
 * CUT DefaultDmsReader
 *
 * @author daniel
 */
public class DefaultDmsReaderTest extends DmsReaderTestParent {

	@Test
	public void readArchiveSingleDirectoryDocument() throws Exception {
		new DocumentFile("test");
		DocumentCollector collector = readArchive("readArchiveSingleDirectoryDocument");
		assertThat(collector.getDocuments()).isNotEmpty();
	}

}
