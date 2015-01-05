package de.galan.dmsexchange.meta.document;

import static org.apache.commons.lang3.StringUtils.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.galan.dmsexchange.meta.Validatable;
import de.galan.dmsexchange.meta.ValidationResult;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class DocumentFile implements Validatable {

	private String filename;
	private Rotation rotation;
	private List<Revision> revisions;


	public DocumentFile(String filename) {
		this.filename = filename;
		revisions = new ArrayList<>();
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

}
