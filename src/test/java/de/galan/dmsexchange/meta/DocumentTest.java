package de.galan.dmsexchange.meta;

import static org.assertj.core.api.Assertions.*;

import java.time.ZonedDateTime;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;

import de.galan.commons.logging.Logr;
import de.galan.commons.test.AbstractTestParent;
import de.galan.dmsexchange.meta.Commenimport de.galan.dmsexchange.meta.document.Comment;
import de.galan.dmsexchange.meta.Document;
import de.galan.dmsexchange.meta.User;
import de.galan.dmsexchange.test.Documents;
import de.galan.dmsexchange.verjson.document.DocumentVersions;
import de.galan.verjson.core.Verjson;


/**
 * CUT Document
 *
 * @author daniel
 */
public class DocumentTest extends AbstractTestParent {

	private static final Logger LOG = Logr.get();


	@Test
	@Ignore
	public void testName() throws Exception {
		Document doc = new Document();
		//doc.tsDocument = ZonedDateTime.now();
		Verjson<Document> verjson = Verjson.create(Document.class, new DocumentVersions());
		String output = verjson.write(doc);
		LOG.info("out: " + output);
	}


	@Test
	public void objectEqualsEmpty() throws Exception {
		Document d1 = new Document();
		Document d2 = new Document();
		assertThat(d1).isEqualTo(d2);
	}


	@Test
	public void objectEqualsSet() throws Exception {
		Document d1 = new Document();
		d1.addLabels("a", "b");
		Document d2 = new Document();
		d2.addLabels("a", "b");
		assertThat(d1).isEqualTo(d2);
	}


	@Test
	public void objectEqualsSetNot() throws Exception {
		Document d1 = new Document();
		d1.addLabels("a", "c");
		Document d2 = new Document();
		d2.addLabels("a", "b");
		assertThat(d1).isNotEqualTo(d2);
	}


	@Test
	public void objectEqualsComplex1() throws Exception {
		Document d1 = Documents.createComplexDocument();
		Document d2 = Documents.createComplexDocument();
		assertThat(d1).isEqualTo(d2);
	}


	@Test
	public void objectEqualsComplexDiffComment() throws Exception {
		Document d1 = Documents.createComplexDocument();
		Document d2 = Documents.createComplexDocument();
		d2.getComments().add(new Comment(new User("other@example.com"), ZonedDateTime.now(), "diff"));
		assertThat(d1).isNotEqualTo(d2);
	}


	@Test
	public void objectEqualsComplexDiffRevision() throws Exception {
		Document d1 = Documents.createComplexDocument();
		Document d2 = Documents.createComplexDocument();
		d2.getDocumentFiles().get(0).getRevisions().get(0).setData(new byte[] {21, 22, 23});
		assertThat(d1).isNotEqualTo(d2);
	}

}
