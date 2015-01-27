package de.galan.dmsexchange.meta.document;

import static org.apache.commons.lang3.StringUtils.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;

import de.galan.dmsexchange.meta.Validatable;
import de.galan.dmsexchange.meta.ValidationResult;


/**
 * Represents a document-file. Read the <a href="https://github.com/galan/dms-exchange-specification">specification</a>
 * for more information.
 *
 * @author daniel
 */
public class DocumentFile implements Validatable {

	private String filename;
	private Rotation rotation;
	private List<Revision> revisions;


	public DocumentFile() {
		revisions = new ArrayList<>();
	}


	public DocumentFile(String filename) {
		this();
		setFilename(filename);
	}


	@Override
	public void validate(ValidationResult result) {
		if (isBlank(getFilename())) {
			result.add("No filename for document-file");
		}
		if (getRevisions() == null || getRevisions().isEmpty()) {
			result.add("No revisions for document-file");
		}
		validate(result, revisions);
	}


	public String getFilename() {
		return filename;
	}


	public void setFilename(String filename) {
		this.filename = filename;
	}


	public Rotation getRotation() {
		return rotation;
	}


	public void setRotation(Rotation rotation) {
		this.rotation = rotation;
	}


	public List<Revision> getRevisions() {
		return revisions;
	}


	public void addRevision(Revision... revisionToAdd) {
		revisions.addAll(Arrays.asList(revisionToAdd));
	}


	@Override
	public int hashCode() {
		return Objects.hash(filename, rotation, revisions);
	}


	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DocumentFile) {
			final DocumentFile other = (DocumentFile)obj;
			return new EqualsBuilder().append(filename, other.filename).append(rotation, other.rotation).append(revisions, other.revisions).isEquals();
		}
		return false;
	}

}
