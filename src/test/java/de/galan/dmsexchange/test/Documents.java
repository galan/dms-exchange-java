package de.galan.dmsexchange.test;

import java.io.IOException;

import de.galan.dmsexchange.exchange.test.revisions.Revisions;
import de.galan.dmsexchange.meta.Comment;
import de.galan.dmsexchange.meta.Context;
import de.galan.dmsexchange.meta.Document;
import de.galan.dmsexchange.meta.DocumentFile;
import de.galan.dmsexchange.meta.Revision;
import de.galan.dmsexchange.meta.Source;
import de.galan.dmsexchange.meta.User;
import de.galan.dmsexchange.util.UtcFormatter;


/**
 * Test-Document creating test-helper methods.
 */
public class Documents {

	public static Document createInvalidDocument() {
		Document doc = new Document();
		doc.setIdSystem("systemId-123");
		doc.setIdUser("userId-456");
		DocumentFile docFile = new DocumentFile("sample.txt");
		Revision rev1 = new Revision(UtcFormatter.parse("2014-12-28T20:00:15Z"));
		docFile.addRevision(rev1);
		doc.addDocumentFile(docFile);
		doc.addLabels("hello", "world");
		doc.addComments(new Comment());
		return doc;
	}


	public static Document createSimpleDocument1() {
		Document doc = new Document();
		DocumentFile docFile = new DocumentFile("sample.txt");
		Revision rev1 = new Revision(UtcFormatter.parse("2014-12-28T20:00:15Z"));
		rev1.setData(new byte[] {72, 69, 76, 76, 79});
		docFile.addRevision(rev1);
		doc.addDocumentFile(docFile);
		doc.addLabels("hello", "world");
		return doc;
	}


	public static Document createSimpleDocument2() throws IOException {
		Document doc = new Document();
		doc.setCreatedBy(new User("who@example.com"));
		doc.setIdUser("id123");
		DocumentFile docFile = new DocumentFile("lorem.txt");
		docFile.addRevision(Revisions.read("lorem-04-01.txt", "2012-01-09T18:11:22Z", null));
		doc.addDocumentFile(docFile);
		return doc;
	}


	public static Document createSimpleDocument3() throws IOException {
		Document doc = new Document();
		doc.setContext(new Context(UtcFormatter.parse("2015-01-05T12:30:32Z"), UtcFormatter.parse("2015-01-08T16:00:00Z")));
		doc.setIdSystem("mySystemId-0123456789");

		DocumentFile df = new DocumentFile("first.pdf");
		df.addRevision(Revisions.read("lorem-01-01.pdf", "2015-01-05T12:30:32Z", null));
		df.addRevision(Revisions.read("lorem-01-02.pdf", "2015-01-05T12:37:10Z", "me@example.com"));
		doc.addDocumentFile(df);
		return doc;
	}


	public static Document createSimpleDocument4() throws IOException {
		Document doc = new Document();
		doc.setContext(new Context(UtcFormatter.parse("2015-01-05T12:30:32Z"), UtcFormatter.parse("2015-01-08T16:00:00Z")));
		doc.setIdSystem("mySystemId-0123456789");

		DocumentFile df = new DocumentFile("second.doc");
		df.addRevision(Revisions.read("lorem-02-01.doc", "2014-12-30T09:10:30Z", "me@example.com"));
		df.addRevision(Revisions.read("lorem-02-02.doc", "2015-01-03T17:58:12Z", null));
		doc.addDocumentFile(df);
		return doc;
	}


	public static Document createSimpleDocument5() throws IOException {
		Document doc = new Document();
		doc.setContext(new Context(UtcFormatter.parse("2015-01-05T12:30:32Z"), UtcFormatter.parse("2015-01-08T16:00:00Z")));
		doc.setIdSystem("mySystemId-0123456789");

		DocumentFile df = new DocumentFile("third.doc");
		df.addRevision(Revisions.read("lorem-03-01.doc", "2014-12-30T08:10:11Z", "me@example.com"));
		df.addRevision(Revisions.read("lorem-03-02.doc", "2015-01-03T17:59:42Z", "him@example.com"));
		df.addRevision(Revisions.read("lorem-03-03.doc", "2015-01-04T17:59:52Z", "other@example.com"));
		doc.addDocumentFile(df);
		return doc;
	}


	public static Document createComplexDocument() throws IOException {
		Document doc = new Document();
		doc.setCreatedBy(new User("creator@example.com"));
		doc.setSource(new Source("SourceName", "SourceVersion", "http://www.example.com/dms", "SourceEmai@example.com"));
		doc.setContext(new Context(UtcFormatter.parse("2015-01-05T12:30:32Z"), UtcFormatter.parse("2015-01-08T16:00:00Z")));
		doc.setDirectory("/projects/exchange");
		doc.setIdSystem("mySystemId-0123456789");
		doc.setIdUser("myUserId-0123456789");
		doc.setLocation("attic");
		doc.setNote("Time for a memo");
		doc.setProject("open-source");
		doc.addLabels("hello", "example");

		doc.addComments(new Comment(new User("me@example.com"), UtcFormatter.parse("2015-01-05T12:44:08Z"), "Lorem le ipsum"));
		doc.addComments(new Comment(new User("you@example.com"), UtcFormatter.parse("2015-01-05T12:44:23Z"), "abc def ghi"));

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

}
