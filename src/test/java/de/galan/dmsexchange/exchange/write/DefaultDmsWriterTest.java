package de.galan.dmsexchange.exchange.write;

import static de.galan.commons.test.Tests.*;
import static org.assertj.core.api.Assertions.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import de.galan.dmsexchange.DmsExchange;
import de.galan.dmsexchange.exchange.DocumentValidationException;
import de.galan.dmsexchange.meta.Document;
import de.galan.dmsexchange.test.Documents;
import de.galan.dmsexchange.util.DmsExchangeException;


/**
 * CUT DefaultDmsWriter
 *
 * @author daniel
 */
public class DefaultDmsWriterTest extends DmsWriterTestParent {

	private static final String EXPORT_FILENAME = "write.tgz";


	@Before
	public void createWriter() throws DmsExchangeException {
		setFile(new File(getTestDirectory(true), EXPORT_FILENAME));
		setWriter(DmsExchange.createWriter(getFile()));
	}


	@Test
	public void createEmptyArchive() throws Exception {
		getWriter().close();
		assertArchive("createEmptyArchive");
	}


	@Test
	public void createArchiveWithSingleSimpleDocument() throws Exception {
		getWriter().add(Documents.createSimpleDocument1());
		getWriter().close();
		assertArchive("createArchiveWithSingleSimpleDocument");
	}


	@Test
	public void createArchiveWithSingleComplexDocument() throws Exception {
		getWriter().add(Documents.createComplexDocument());
		getWriter().close();
		assertArchive("createArchiveWithSingleComplexDocument");
	}


	@Test
	public void createArchiveWithMultipleDocuments() throws Exception {
		getWriter().add(Documents.createComplexDocument());
		getWriter().add(Documents.createSimpleDocument1(), Documents.createSimpleDocument2());
		getWriter().close();
		assertArchive("createArchiveWithMultipleDocuments");
	}


	@Test
	public void createArchiveWithEmptyDocument() throws Exception {
		try {
			getWriter().add(new Document());
		}
		catch (DocumentValidationException ex) {
			assertThat(ex.getMessage()).isEqualTo("Invalid Document (Document does not contain any DocumentFile)");
			assertThat(ex.getValidationResult().getErrors()).containsOnly("Document does not contain any DocumentFile");
		}
		getWriter().close();
		assertArchive("createArchiveWithEmptyDocument");
	}


	@Test
	public void createArchiveWithInvalidDocument() throws Exception {
		try {
			getWriter().add(Documents.createInvalidDocument());
		}
		catch (DocumentValidationException ex) {
			assertThat(ex.getMessage()).isEqualTo("Invalid Document (No data for revision, No user for comment, No time for comment, No content for comment)");
			assertThat(ex.getValidationResult().getErrors()).containsOnly("No data for revision", "No user for comment", "No time for comment",
				"No content for comment");
		}
		getWriter().close();
		assertArchive("createArchiveWithInvalidDocument");
	}


	@Test
	public void createArchiveWithMultipleInvalidDocuments() throws Exception {
		getWriter().addQuietly(new Document(), Documents.createInvalidDocument());
		getWriter().close();
		assertArchive("createArchiveWithMultipleInvalidDocuments");
	}


	@Test(expected = DmsExchangeException.class)
	public void addDocumentAfterClosed() throws Exception {
		getWriter().close();
		getWriter().add(Documents.createSimpleDocument1());
	}

}
