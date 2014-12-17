package de.galan.dmsexchange.meta.document;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.ZonedDateTime;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.io.Files;

import de.galan.dmsexchange.meta.User;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class Revision {

	private User addedBy;
	private ZonedDateTime tsAdded;
	@JsonIgnore
	private byte[] data;


	public Revision(ZonedDateTime tsAdded) {
		this(tsAdded, null, null);
	}


	public Revision(ZonedDateTime tsAdded, User addedBy, byte[] data) {
		setTsAdded(tsAdded);
		setAddedBy(addedBy);
		setData(data);
	}


	public User getAddedBy() {
		return addedBy;
	}


	public void setAddedBy(User addedBy) {
		this.addedBy = addedBy;
	}


	public ZonedDateTime getTsAdded() {
		return tsAdded;
	}


	public void setTsAdded(ZonedDateTime tsAdded) {
		this.tsAdded = tsAdded;
	}


	@JsonIgnore
	public byte[] getData() {
		return data;
	}


	public void setData(byte[] data) {
		this.data = data;
	}


	public void setData(InputStream stream) throws IOException {
		IOUtils.read(stream, data);
	}


	public void setData(File file) throws IOException {
		setData(Files.toByteArray(file));
	}

}
