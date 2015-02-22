package de.galan.dmsexchange.meta;

import static org.assertj.core.api.Assertions.*;

import java.time.ZonedDateTime;

import org.junit.Before;
import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;
import de.galan.commons.time.ApplicationClock;
import de.galan.dmsexchange.test.Documents;


/**
 * CUT Document
 *
 * @author daniel
 */
public class DocumentTest extends AbstractTestParent {

	@Before
	public void before() {
		ApplicationClock.setUtc("2015-02-22T23:18:40Z");
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
