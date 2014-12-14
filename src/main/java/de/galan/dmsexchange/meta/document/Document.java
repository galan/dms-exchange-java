package de.galan.dmsexchange.meta.document;

import java.util.ArrayList;
import java.util.List;

import de.galan.dmsexchange.util.Version;


/**
 * Metadata used for a document, will be stored to the file meta.json.
 *
 * @author daniel
 */
public class Document {

	private String version;
	private List<DocumentFile> documentFiles;
	private Context context;
	private String note;
	private String location;
	private List<Comment> comments;
	private String idUser;
	private String idSystem;
	private String project;
	private String directory;
	private List<String> labels;
	private Boolean optionIndexed;
	private Boolean optionOcr;


	public Document() {
		version = Version.SUPPORTED_VERSION;
		documentFiles = new ArrayList<>();
	}


	public void addDocumentFile(DocumentFile file) {
		documentFiles.add(file);
	}

}
