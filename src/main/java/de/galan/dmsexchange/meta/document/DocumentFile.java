package de.galan.dmsexchange.meta.document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.galan.dmsexchange.meta.User;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class DocumentFile {

	private String filename;

	private User scannedBy; //TODO remove->redundant to addedBy in revision?
	private Rotation rotation;

	private List<Revision> revisions;


	public DocumentFile(String filename) {
		this.filename = filename;
		revisions = new ArrayList<>();
	}


	public String getFilename() {
		return filename;
	}


	public User getScannedBy() {
		return scannedBy;
	}


	public void setScannedBy(User scannedBy) {
		this.scannedBy = scannedBy;
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
