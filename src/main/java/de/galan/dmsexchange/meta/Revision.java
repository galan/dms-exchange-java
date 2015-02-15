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


/**
 * A single revision of a document-file, the binary data will be serialized into files and is transient to the
 * meta-data. Read the <a href="https://github.com/galan/dms-exchange-specification">specification</a> for more
 * information.
 *
 * @author daniel
 */
public class Revision implements Validatable {

	private User addedBy;
	private ZonedDateTime tsAdded;
	@JsonIgnore
	private byte[] data;


	public Revision() {
		//nada
	}


	public Revision(ZonedDateTime tsAdded) {
		this(tsAdded, null, null);
	}


	public Revision(ZonedDateTime tsAdded, User addedBy, byte[] data) {
		setTsAdded(tsAdded);
		setAddedBy(addedBy);
		setData(data);
	}


	@Override
	public void validate(ValidationResult result) {
		if (getTsAdded() == null) {
			result.add("No datetime for revision");
		}
		if (getData() == null || getData().length == 0) {
			result.add("No data for revision");
		}
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


	public void setData(InputStream stream) throws IOException {
		data = IOUtils.toByteArray(stream);
		stream.close();
	}


	public void setData(File file) throws IOException {
		setData(Files.toByteArray(file));
	}


	@Override
	public int hashCode() {
		return Objects.hash(addedBy, tsAdded, data);
	}


	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Revision) {
			final Revision other = (Revision)obj;
			return new EqualsBuilder().append(addedBy, other.addedBy).append(tsAdded, other.tsAdded).append(data, other.data).isEquals();
		}
		return false;
	}

}
