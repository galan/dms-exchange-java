package de.galan.dmsexchange.meta.document;

import java.util.ArrayList;
import java.util.List;

import de.galan.dmsexchange.meta.User;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class DocumentFile {

	private String filename;

	private User scannedBy; //TODO indexedBy
	private Rotation rotation;

	private List<Revision> revisions;


	public DocumentFile(String filename) {
		this.filename = filename;
		revisions = new ArrayList<>();
	}


	public void addRevision(Revision revision) {
		revisions.add(revision);
	}

}
