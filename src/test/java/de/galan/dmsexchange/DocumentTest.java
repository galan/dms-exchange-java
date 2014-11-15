package de.galan.dmsexchange;

import org.junit.Test;
import org.slf4j.Logger;

import de.galan.commons.logging.Logr;
import de.galan.commons.test.AbstractTestParent;
import de.galan.dmsexchange.meta.document.Document;
import de.galan.dmsexchange.verjson.document.DocumentVersions;
import de.galan.verjson.core.Verjson;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class DocumentTest extends AbstractTestParent {

	private static final Logger LOG = Logr.get();


	@Test
	public void testName() throws Exception {
		Document doc = new Document();
		//doc.tsDocument = ZonedDateTime.now();
		Verjson<Document> verjson = Verjson.create(Document.class, new DocumentVersions());
		String output = verjson.write(doc);
		LOG.info("out: " + output);
	}

}
