package de.galan.dmsexchange.verjson.verjson.export;

import java.time.ZonedDateTime;

import org.junit.Test;
import org.slf4j.Logger;

import de.galan.commons.logging.Logr;
import de.galan.commons.test.AbstractTestParent;
import de.galan.dmsexchange.meta.User;
import de.galan.dmsexchange.meta.export.Export;
import de.galan.dmsexchange.verjson.export.ExportVersions;
import de.galan.verjson.core.Verjson;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class ExportTest extends AbstractTestParent {

	private static final Logger LOG = Logr.get();


	//TODO
	@Test
	public void testName() throws Exception {
		Export export = new Export();
		export.setDescription("test desc");
		export.setExportBy(new User("hello@example.com"));
		export.setTsExport(ZonedDateTime.now());

		Verjson<Export> verjson = Verjson.create(Export.class, new ExportVersions());
		String write = verjson.write(export);
		LOG.info("write: {}", write);
		Export read = verjson.read(write);
		LOG.info("read: {}", read);
	}

}
