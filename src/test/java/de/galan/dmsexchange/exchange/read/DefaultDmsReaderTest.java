package de.galan.dmsexchange.exchange.read;

import org.junit.Test;


/**
 * CUT DefaultDmsReader
 *
 * @author daniel
 */
public class DefaultDmsReaderTest extends DmsReaderTestParent {

	@Test
	public void readArchiveSingleDirectoryDocument() throws Exception {
		DocumentCollector collector = readArchive("readArchiveSingleDirectoryDocument");
		//assertThat(collector.getDocuments()).isNotEmpty();
	}

}
