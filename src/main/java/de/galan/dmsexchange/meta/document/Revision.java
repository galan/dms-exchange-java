package de.galan.dmsexchange.meta.document;

import java.io.OutputStream;
import java.time.ZonedDateTime;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import de.galan.dmsexchange.meta.User;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class Revision {

	private User addedBy;
	private ZonedDateTime tsAdded;


	public OutputStream getStream() {
		ZipFile zf = null;
		ZipEntry ze = null;
		//zf.getInputStream(ze);
		return null;
	}

}
