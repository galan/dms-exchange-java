package de.galan.dmsexchange.meta.document;

import java.time.ZonedDateTime;

import de.galan.dmsexchange.meta.User;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class Revision {

	private User addedBy;
	private ZonedDateTime tsAdded;
	private transient byte[] data;


	public Revision(ZonedDateTime tsAdded) {
		this.tsAdded = tsAdded;
	}


	public void setData(byte[] data) {
		this.data = data;
	}

}
