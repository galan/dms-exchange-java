package de.galan.dmsexchange.meta;

import static de.galan.commons.time.Instants.*;
import static org.apache.commons.lang3.StringUtils.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.galan.dmsexchange.util.Version;


/**
 * Represents the document and it's metadata, that will be stored to the file meta.json. Read the <a
 * href="https://github.com/galan/dms-exchange-specification">specification</a> for more information.
 *
 * @author daniel
 */
public class Document implements Validatable {

	private String version;
	private ZonedDateTime tsCreate;
	private User createdBy;
	private Source source;

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
		tsCreate = from(now()).toZdt();
		documentFiles = new ArrayList<>();
		comments = new ArrayList<>();
		labels = new ArrayList<>();
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
		validate(result, getSource());
	}


	public String getVersion() {
		return version;
	}


	public ZonedDateTime getTsCreate() {
		return tsCreate;
	}


	public void setTsCreate(ZonedDateTime tsCreate) {
		this.tsCreate = tsCreate;
	}


	public User getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}


	public Source getSource() {
		return source;
	}


	public void setSource(Source source) {
		this.source = source;
	}


	@JsonIgnore
	public String getSourceAsString() {
		return (source == null) ? "unknown" : getSource().toString();
	}


	public void addDocumentFile(DocumentFile file) {
		getDocumentFiles().add(file);
	}


	public List<DocumentFile> getDocumentFiles() {
		if (documentFiles == null) {
			documentFiles = new ArrayList<>();
		}
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


	@Override
	public int hashCode() {
		return Objects.hash(version, documentFiles, context, note, location, comments, idUser, idSystem, project, directory, labels, optionIndexed, optionOcr);
	}


	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Document) {
			final Document other = (Document)obj;
			return new EqualsBuilder().append(version, other.version).append(tsCreate, other.tsCreate).append(createdBy, other.createdBy).append(source,
				other.source).append(documentFiles, other.documentFiles).append(context, other.context).append(note, other.note).append(location,
					other.location).append(comments, other.comments).append(idUser, other.idUser).append(idSystem, other.idSystem).append(project, other.project).append(
						directory, other.directory).append(labels, other.labels).append(optionIndexed, other.optionIndexed).append(optionOcr, other.optionOcr).isEquals();
		}
		return false;
	}

}
