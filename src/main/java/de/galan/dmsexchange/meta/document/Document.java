package de.galan.dmsexchange.meta.document;

import static org.apache.commons.lang3.StringUtils.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.galan.dmsexchange.meta.Validatable;
import de.galan.dmsexchange.meta.ValidationResult;
import de.galan.dmsexchange.util.Version;


/**
 * Represents the document and it's metadata, that will be stored to the file meta.json. Read the <a
 * href="https://github.com/galan/dms-exchange-specification">specification</a> for more information.
 *
 * @author daniel
 */
public class Document implements Validatable {

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


	@Override
	public void validate(ValidationResult result) {
		if (isBlank(getVersion())) {
			result.add("Version is not set");
		}
		if (getDocumentFiles() == null || getDocumentFiles().isEmpty()) {
			result.add("Document does not contain any DocumentFile");
		}
		validate(result, getDocumentFiles());
		validate(result, getComments());
	}


	public String getVersion() {
		return version;
	}


	public void addDocumentFile(DocumentFile file) {
		documentFiles.add(file);
	}


	public List<DocumentFile> getDocumentFiles() {
		return documentFiles;
	}


	public Context getContext() {
		return context;
	}


	public void setContext(Context context) {
		this.context = context;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public List<Comment> getComments() {
		if (comments == null) {
			comments = new ArrayList<>();
		}
		return comments;
	}


	public void addComments(Comment... commentsToAdd) {
		getComments().addAll(Arrays.asList(commentsToAdd));
	}


	public String getIdUser() {
		return idUser;
	}


	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}


	public String getIdSystem() {
		return idSystem;
	}


	public void setIdSystem(String idSystem) {
		this.idSystem = idSystem;
	}


	public String getProject() {
		return project;
	}


	public void setProject(String project) {
		this.project = project;
	}


	public String getDirectory() {
		return directory;
	}


	public void setDirectory(String directory) {
		this.directory = directory;
	}


	public List<String> getLabels() {
		if (labels == null) {
			labels = new ArrayList<>();
		}
		return labels;
	}


	public void addLabels(String... labelsToAdd) {
		getLabels().addAll(Arrays.asList(labelsToAdd));
	}


	public Boolean getOptionIndexed() {
		return optionIndexed;
	}


	public void setOptionIndexed(Boolean optionIndexed) {
		this.optionIndexed = optionIndexed;
	}


	public Boolean getOptionOcr() {
		return optionOcr;
	}


	public void setOptionOcr(Boolean optionOcr) {
		this.optionOcr = optionOcr;
	}

}
