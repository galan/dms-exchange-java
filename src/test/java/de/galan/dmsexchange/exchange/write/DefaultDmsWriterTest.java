package de.galan.dmsexchange.exchange.write;

import static de.galan.commons.test.Tests.*;
import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.zeroturnaround.zip.ZipUtil;

import com.google.common.base.Charsets;

import de.galan.commons.test.AbstractTestParent;
import de.galan.commons.time.ApplicationClock;
import de.galan.dmsexchange.DmsExchange;
import de.galan.dmsexchange.exchange.DmsWriter;
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


	@Test
	public void createEmptyArchive() throws Exception {
		DmsWriter writer = DmsExchange.createWriter(file);
		writer.close();
		assertArchive("createEmptyArchive");
	}


	protected void assertArchive(String testmethod) throws IOException {
		assertThat(file).exists();
		boolean exists = ZipUtil.containsEntry(file, "export.json");
		assertThat(exists).isTrue();
		String exportJson = new String(ZipUtil.unpackEntry(file, "export.json"), Charsets.UTF_8);
		assertFileEqualsToString(getClass().getSimpleName() + "-" + testmethod + ".json", getClass(), exportJson);
	}


	@Test
	public void createArchiveWithSingleDocument() throws Exception {
		DmsWriter writer = DmsExchange.createWriter(file);

		Document doc = new Document();
		DocumentFile docFile = new DocumentFile("sample.txt");
		Revision rev1 = new Revision(ZonedDateTime.now(ApplicationClock.getClock()));
		rev1.setData(new byte[] {72, 69, 76, 76, 79});
		docFile.addRevision(rev1);
		doc.addDocumentFile(docFile);
		doc.addLabels("hello", "world");
		writer.addDocument(doc);

		writer.close();
		assertArchive("createArchiveWithSingleDocument");
	}


	@Test
	@Ignore
	public void addDocumentAfterClosed() throws Exception {
		//TODO
	}
}
