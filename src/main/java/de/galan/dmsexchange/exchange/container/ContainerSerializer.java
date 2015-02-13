package de.galan.dmsexchange.exchange.container;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.io.output.ByteArrayOutputStream;

import com.google.common.base.Charsets;

import de.galan.dmsexchange.exchange.DocumentValidationException;
import de.galan.dmsexchange.meta.ValidationResult;
import de.galan.dmsexchange.meta.document.Document;
import de.galan.dmsexchange.meta.document.DocumentFile;
import de.galan.dmsexchange.meta.document.Revision;
import de.galan.dmsexchange.util.archive.TarUtil;


/**
 * Converts document-containers to archives.
 *
 * @author daniel
 */
public class ContainerSerializer extends AbstractContainer {

	public byte[] archive(Document document, boolean standalone) throws IOException, DocumentValidationException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		archive(document, standalone, baos);
		return baos.toByteArray();
	}


	public void archive(Document document, boolean standalone, OutputStream outputstream) throws IOException, DocumentValidationException {
		validateDocument(document);
		try (TarArchiveOutputStream tar = TarUtil.create(outputstream, standalone)) {

			// serialize metadata
			String documentJson = getVerjson().writePlain(document);
			byte[] metadata = documentJson.getBytes(Charsets.UTF_8);
			TarUtil.addEntry(tar, metadata, "meta.json");
			// serialize documents
			for (DocumentFile df: document.getDocumentFiles()) {
				for (Revision revision: df.getRevisions()) {
					String generated = generateRevisionName(df, revision);
					TarUtil.addEntry(tar, revision.getData(), generated);
				}
			}
			tar.flush();
		}
	}


	protected void validateDocument(Document document) throws DocumentValidationException {
		ValidationResult result = document.validate();
		if (result.hasErrors()) {
			throw new DocumentValidationException("Invalid Document", result);
		}
	}

}
