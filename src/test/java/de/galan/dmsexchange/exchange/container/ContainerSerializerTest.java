package de.galan.dmsexchange.exchange.container;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;
import de.galan.dmsexchange.test.Documents;


/**
 * CUT ContainerSerializer
 *
 * @author daniel
 */
public class ContainerSerializerTest extends AbstractTestParent {

	@Test
	public void testName() throws Exception {
		ContainerSerializer cs = new ContainerSerializer();
		byte[] archive = cs.archive(Documents.createSimpleDocument1(), false);
		assertThat(archive).isNotNull();
	}

}
