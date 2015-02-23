package de.galan.dmsexchange.exchange.container;

import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;
import de.galan.commons.time.ApplicationClock;
import de.galan.dmsexchange.meta.Document;
import de.galan.dmsexchange.test.Documents;


/**
 * CUT ContainerDeserializer
 *
 * @author daniel
 */
public class ContainerDeserializerTest extends AbstractTestParent {

	@Before
	public void before() {
		ApplicationClock.setUtc("2015-01-17T18:12:10Z");
	}


	@Test
	public void deserialze() throws Exception {
		ContainerSerializer cs = new ContainerSerializer();
		Document document = Documents.createSimpleDocument1();
		byte[] container = cs.archive(document, false);

		ContainerDeserializer cd = new ContainerDeserializer();
		Document documentDeserialized = cd.unarchive(container, false);
		assertThat(documentDeserialized).isEqualTo(document);
	}

}
