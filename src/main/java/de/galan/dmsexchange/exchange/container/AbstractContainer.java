package de.galan.dmsexchange.exchange.container;

import java.time.format.DateTimeFormatter;

import de.galan.dmsexchange.meta.Document;
import de.galan.dmsexchange.meta.DocumentFile;
import de.galan.dmsexchange.meta.Revision;
import de.galan.dmsexchange.verjson.document.DocumentVersions;
import de.galan.verjson.core.Verjson;


/**
 * Base for container serialization/deserialization
 */
public abstract class AbstractContainer {

	protected static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");
	protected static final String REVISIONS_DIRNAME = "revisions";
	private Verjson<Document> verjson;


	public AbstractContainer() {
		verjson = Verjson.create(Document.class, new DocumentVersions());
	}


	protected Verjson<Document> getVerjson() {
		return verjson;
	}


	protected String generateRevisionName(DocumentFile df, Revision revision) {
		return REVISIONS_DIRNAME + "/" + revision.getAddedTime().format(FORMATTER) + "_" + df.getFilename();
	}

}
