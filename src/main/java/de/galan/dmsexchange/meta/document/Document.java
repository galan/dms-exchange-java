package de.galan.dmsexchange.meta.document;

import java.time.LocalDateTime;
import java.util.List;


/**
 * Metadata used for a document, will be stored to the file meta.json.
 *
 * @author daniel
 */
public class Document {

	String version = "1.0.0-beta.2";
	List<DocumentFile> documentFiles;
	Context context;
	LocalDateTime tsDocument;
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
