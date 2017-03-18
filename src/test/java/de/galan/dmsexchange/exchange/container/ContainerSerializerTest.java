package de.galan.dmsexchange.exchange.container;

import static de.galan.commons.test.Tests.*;
import static org.assertj.core.api.Assertions.*;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;
import de.galan.commons.time.ApplicationClock;
import de.galan.dmsexchange.meta.Document;
import de.galan.dmsexchange.test.Documents;
import de.galan.dmsexchange.test.TarTests;
import de.galan.dmsexchange.verjson.document.DocumentVersions;
import de.galan.verjson.core.Verjson;


/**
 * CUT ContainerSerializer
 */
public class ContainerSerializerTest extends AbstractTestParent {

	@Before
	public void before() {
		ApplicationClock.setUtc("2015-01-17T18:12:10Z");
	}


	@Test
	public void serialize() throws Exception {
		ContainerSerializer cs = new ContainerSerializer();
		Document document = Documents.createSimpleDocument1();
		byte[] container = cs.archive(document, false);
		assertThat(container).isNotNull();
		File file = new File(getTestDirectory(true), "container.tar");
		FileUtils.writeByteArrayToFile(file, container);
		TarTests.explode(file, false);
		Verjson<Document> verjson = new Verjson<>(Document.class, new DocumentVersions());
		assertThat(new File(file, "meta.json")).hasContent(verjson.writePlain(document));
		assertThat(new File(file, "revisions/20141228T200015Z_sample.txt")).hasContent("HELLO");
	}

}
