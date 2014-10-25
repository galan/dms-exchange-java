package de.galan.dmsexchange.meta.document;

import java.time.ZonedDateTime;
import java.util.List;

import de.galan.dmsexchange.util.Version;


/**
 * Metadata used for a document, will be stored to the file meta.json.
 *
 * @author daniel
 */
public class Document {

	String version = Version.VERSION;
	List<DocumentFile> documentFiles;
	Context context;
	public ZonedDateTime tsDocument;
	String note;
	String location;
	List<Comment> comments;
	String idUser;
	String idSystem;
	String project;
	String directory;
	List<String> labels;
	Boolean optionIndexed;
	Boolean optionOcr;

}
