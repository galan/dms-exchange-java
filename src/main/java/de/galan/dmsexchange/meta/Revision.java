package de.galan.dmsexchange.meta;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.ZonedDateTime;
import java.util.Objects;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.builder.EqualsBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.io.Files;

import de.galan.dmsexchange.util.UtcFormatter;


/**
 * A single revision of a document-file, the binary data will be serialized into files and is transient to the
 * meta-data. Read the <a href="https://github.com/galan/dms-exchange-specification">specification</a> for more
 * information.
 *
 * @author daniel
 */
public class Revision implements Validatable {

	private User addedBy;
	private ZonedDateTime addedTime;
	@JsonIgnore
	private byte[] data;


	public Revision() {
		// empty, default constructor required for Jackson
	}


	public Revision(ZonedDateTime addedTime) {
		this(addedTime, null, null);
	}


	public Revision(ZonedDateTime addedTime, User addedBy, byte[] data) {
		setAddedTime(addedTime);
		setAddedBy(addedBy);
		setData(data);
	}


	@Override
	public void validate(ValidationResult result) {
		if (getAddedTime() == null) {
			result.add("No addedTime for revision");
		}
		if (getData() == null || getData().length == 0) {
			result.add("No data for revision");
		}
		if (getAddedBy() != null) {
			getAddedBy().validate(result);
		}
	}


	public User getAddedBy() {
		return addedBy;
	}


	public void setAddedBy(User addedBy) {
		this.addedBy = addedBy;
	}


	public Revision addedBy(@SuppressWarnings("hiding") User addedBy) {
		setAddedBy(addedBy);
		return this;
	}


	public Revision addedBy(@SuppressWarnings("hiding") String addedBy) {
		setAddedBy(new User(addedBy));
		return this;
	}


	public ZonedDateTime getAddedTime() {
		return addedTime;
	}


	public void setAddedTime(ZonedDateTime addedTime) {
		this.addedTime = addedTime;
	}


	public Revision addedTime(@SuppressWarnings("hiding") ZonedDateTime addedTime) {
		setAddedTime(addedTime);
		return this;
	}


	public Revision addedTime(@SuppressWarnings("hiding") String addedTime) {
		setAddedTime(UtcFormatter.parse(addedTime));
		return this;
	}


	@JsonIgnore
	public byte[] getData() {
		return data;
	}


	@JsonIgnore
	public InputStream getDataInputStream() {
		return new ByteArrayInputStream(getData());
	}


	@JsonIgnore
	public OutputStream getDataOutputStream() throws IOException {
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		result.write(getDataInputStream());
		return result;
	}


	public void writeDataToFile(File file) throws IOException {
		FileUtils.writeByteArrayToFile(file, getData());
	}


	public void setData(byte[] data) {
		this.data = data;
	}


	public Revision data(@SuppressWarnings("hiding") byte[] data) {
		setData(data);
		return this;
	}


	public void setData(InputStream stream) throws IOException {
		data = IOUtils.toByteArray(stream);
		stream.close();
	}


	public Revision data(InputStream stream) throws IOException {
		setData(stream);
		return this;
	}


	public void setData(File file) throws IOException {
		setData(Files.toByteArray(file));
	}


	public Revision data(File file) throws IOException {
		setData(file);
		return this;
	}


	@Override
	public int hashCode() {
		return Objects.hash(addedBy, addedTime, data);
	}


	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Revision) {
			final Revision other = (Revision)obj;
			return new EqualsBuilder().append(addedBy, other.addedBy).append(addedTime, other.addedTime).append(data, other.data).isEquals();
		}
		return false;
	}

}
