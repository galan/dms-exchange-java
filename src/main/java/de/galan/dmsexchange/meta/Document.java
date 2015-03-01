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

import de.galan.dmsexchange.util.UtcFormatter;
import de.galan.dmsexchange.util.Version;


/**
 * Represents the document and it's metadata, that will be stored to the file meta.json. Read the <a
 * href="https://github.com/galan/dms-exchange-specification">specification</a> for more information.
 *
 * @author daniel
 */
public class Document implements Validatable {

	private String version;
	private ZonedDateTime createdTime;
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
		// default constructor required for Jackson
		version = Version.SUPPORTED_VERSION;
		createdTime = from(now()).toZdt();
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


	public ZonedDateTime getCreatedTime() {
		return createdTime;
	}


	public void setCreatedTime(ZonedDateTime createdTime) {
		this.createdTime = createdTime;
	}


	public Document createdTime(@SuppressWarnings("hiding") ZonedDateTime createdTime) {
		setCreatedTime(createdTime);
		return this;
	}


	public Document createdTime(@SuppressWarnings("hiding") String createdTime) {
		setCreatedTime(UtcFormatter.parse(createdTime));
		return this;
	}


	public User getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}


	public Document createdBy(@SuppressWarnings("hiding") User createdBy) {
		setCreatedBy(createdBy);
		return this;
	}


	public Document createdBy(@SuppressWarnings("hiding") String createdBy) {
		setCreatedBy(new User(createdBy));
		return this;
	}


	public Source getSource() {
		return source;
	}


	public void setSource(Source source) {
		this.source = source;
	}


	public Document source(@SuppressWarnings("hiding") Source source) {
		setSource(source);
		return this;
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


	public Document documentFile(DocumentFile file) {
		addDocumentFile(file);
		return this;
	}


	public Context getContext() {
		return context;
	}


	public void setContext(Context context) {
		this.context = context;
	}


	public Document context(@SuppressWarnings("hiding") Context context) {
		setContext(context);
		return this;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	public Document note(@SuppressWarnings("hiding") String note) {
		setNote(note);
		return this;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public Document location(@SuppressWarnings("hiding") String location) {
		setLocation(location);
		return this;
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


	public Document comments(Comment... commentsToAdd) {
		addComments(commentsToAdd);
		return this;
	}


	public String getIdUser() {
		return idUser;
	}


	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}


	public Document idUser(@SuppressWarnings("hiding") String idUser) {
		setIdUser(idUser);
		return this;
	}


	public String getIdSystem() {
		return idSystem;
	}


	public void setIdSystem(String idSystem) {
		this.idSystem = idSystem;
	}


	public Document idSystem(@SuppressWarnings("hiding") String idSystem) {
		setIdSystem(idSystem);
		return this;
	}


	public String getProject() {
		return project;
	}


	public void setProject(String project) {
		this.project = project;
	}


	public Document project(@SuppressWarnings("hiding") String project) {
		setProject(project);
		return this;
	}


	public String getDirectory() {
		return directory;
	}


	public void setDirectory(String directory) {
		this.directory = directory;
	}


	public Document diretory(@SuppressWarnings("hiding") String directory) {
		setDirectory(directory);
		return this;
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


	public Document labels(String... labelsToAdd) {
		addLabels(labelsToAdd);
		return this;
	}


	public Boolean getOptionIndexed() {
		return optionIndexed;
	}


	public void setOptionIndexed(Boolean optionIndexed) {
		this.optionIndexed = optionIndexed;
	}


	public Document optionIndexed(@SuppressWarnings("hiding") Boolean optionIndexed) {
		setOptionIndexed(optionIndexed);
		return this;
	}


	public Boolean getOptionOcr() {
		return optionOcr;
	}


	public void setOptionOcr(Boolean optionOcr) {
		this.optionOcr = optionOcr;
	}


	public Document optionOcr(@SuppressWarnings("hiding") Boolean optionOcr) {
		setOptionOcr(optionOcr);
		return this;
	}


	@Override
	public int hashCode() {
		return Objects.hash(version, documentFiles, context, note, location, comments, idUser, idSystem, project, directory, labels, optionIndexed, optionOcr);
	}


	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Document) {
			final Document other = (Document)obj;
			return new EqualsBuilder().append(version, other.version).append(createdTime, other.createdTime).append(createdBy, other.createdBy).append(source,
				other.source).append(documentFiles, other.documentFiles).append(context, other.context).append(note, other.note).append(location,
				other.location).append(comments, other.comments).append(idUser, other.idUser).append(idSystem, other.idSystem).append(project, other.project).append(
				directory, other.directory).append(labels, other.labels).append(optionIndexed, other.optionIndexed).append(optionOcr, other.optionOcr).isEquals();
		}
		return false;
	}

}
