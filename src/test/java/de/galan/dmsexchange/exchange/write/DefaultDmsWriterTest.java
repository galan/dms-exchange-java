package de.galan.dmsexchange.exchange.write;

import static de.galan.commons.test.Tests.*;
import static de.galan.commons.time.Instants.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.ClosedFileSystemException;
import java.time.ZonedDateTime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.zeroturnaround.zip.ZipUtil;

import de.galan.commons.test.AbstractTestParent;
import de.galan.commons.time.ApplicationClock;
import de.galan.dmsexchange.DmsExchange;
import de.galan.dmsexchange.exchange.DmsWriter;
import de.galan.dmsexchange.exchange.test.revisions.Revisions;
import de.galan.dmsexchange.meta.User;
import de.galan.dmsexchange.meta.document.Comment;
import de.galan.dmsexchange.meta.document.Context;
import de.galan.dmsexchange.meta.document.Document;
import de.galan.dmsexchange.meta.document.DocumentFile;
import de.galan.dmsexchange.meta.document.Revision;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class DefaultDmsWriterTest extends AbstractTestParent {

	private static final String EXPORT_FILENAME = "write.zip";
	private File file;


	@Before
	public void before() {
		ApplicationClock.setUtc("2014-12-28T19:21:57Z");
		file = new File(getTestDirectory(true), EXPORT_FILENAME);
	}


	@After
	public void after() {
		if (file.exists()) {
			assertThat(file.delete()).isTrue();
		}
	}


	protected void assertArchive(String testcase) {
		assertThat(file).exists();
		URL url = getClass().getResource(getClass().getSimpleName() + "-" + testcase);
		File dirToPack = new File(url.getFile());
		File expectedArchive = new File(getTestDirectory(), "expected.zip");
		ZipUtil.pack(dirToPack, expectedArchive);
		assertTrue("Export-Archive is not identical to expected one", ZipUtil.archiveEquals(expectedArchive, file));
	}


	protected ZonedDateTime zdt(String utc) {
		return from(instantUtc(utc)).toZdt();
	}


	@Test
	public void createEmptyArchive() throws Exception {
		DmsWriter writer = DmsExchange.createWriter(file);
		writer.close();
		assertArchive("createEmptyArchive");
	}


	@Test
	public void createArchiveWithSingleSimpleDocument() throws Exception {
		DmsWriter writer = DmsExchange.createWriter(file);
		writer.add(createSimpleDocument());
		writer.close();
		assertArchive("createArchiveWithSingleSimpleDocument");
	}


	private Document createSimpleDocument() {
		Document doc = new Document();
		DocumentFile docFile = new DocumentFile("sample.txt");
		Revision rev1 = new Revision(zdt("2014-12-28T20:00:15Z"));
		rev1.setData(new byte[] {72, 69, 76, 76, 79});
		docFile.addRevision(rev1);
		doc.addDocumentFile(docFile);
		doc.addLabels("hello", "world");
		return doc;
	}


	@Test
	public void createArchiveWithSingleComplexDocument() throws Exception {
		DmsWriter writer = DmsExchange.createWriter(file);
		writer.add(createComplexDocument());
		writer.close();
		assertArchive("createArchiveWithSingleComplexDocument");
	}


	private Document createComplexDocument() throws IOException {
		Document doc = new Document();
		doc.setContext(new Context(zdt("2015-01-05T12:30:32Z"), zdt("2015-01-08T16:00:00Z")));
		doc.setDirectory("/projects/exchange");
		doc.setIdSystem("mySystemId-0123456789");
		doc.setIdUser("myUserId-0123456789");
		doc.setLocation("attic");
		doc.setNote("Time for a memo");
		doc.setProject("open-source");
		doc.addLabels("hello", "example");

		doc.addComments(new Comment(new User("me@example.com"), zdt("2015-01-05T12:44:08Z"), "Lorem le ipsum"));
		doc.addComments(new Comment(new User("you@example.com"), zdt("2015-01-05T12:44:23Z"), "abc def ghi"));

		DocumentFile df01 = new DocumentFile("first.pdf");
		df01.addRevision(Revisions.read("lorem-01-01.pdf", "2015-01-05T12:30:32Z", null));
		df01.addRevision(Revisions.read("lorem-01-02.pdf", "2015-01-05T12:37:10Z", "me@example.com"));
		doc.addDocumentFile(df01);
		DocumentFile df02 = new DocumentFile("second.doc");
		df02.addRevision(Revisions.read("lorem-02-01.doc", "2014-12-30T09:10:30Z", "me@example.com"));
		df02.addRevision(Revisions.read("lorem-02-02.doc", "2015-01-03T17:58:12Z", null));
		doc.addDocumentFile(df02);
		DocumentFile df03 = new DocumentFile("third.doc");
		df03.addRevision(Revisions.read("lorem-03-01.doc", "2014-12-30T08:10:11Z", "me@example.com"));
		df03.addRevision(Revisions.read("lorem-03-02.doc", "2015-01-03T17:59:42Z", "him@example.com"));
		df03.addRevision(Revisions.read("lorem-03-03.doc", "2015-01-04T17:59:52Z", "other@example.com"));
		doc.addDocumentFile(df03);
		return doc;
	}


	@Test(expected = ClosedFileSystemException.class)
	public void addDocumentAfterClosed() throws Exception {
		DmsWriter writer = DmsExchange.createWriter(file);
		writer.close();
		writer.add(createSimpleDocument());
	}

}
