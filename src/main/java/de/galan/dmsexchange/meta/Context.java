package de.galan.dmsexchange.meta;

import java.time.ZonedDateTime;
import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;


/**
 * Information that are inherit to the document-content. Read the <a
 * href="https://github.com/galan/dms-exchange-specification">specification</a> for more information.
 *
 * @author daniel
 */
public class Context implements Validatable {

	private ZonedDateTime documentTime;
	private ZonedDateTime dueDateTime;


	public Context() {
		//nada
	}


	public Context(ZonedDateTime documentTime, ZonedDateTime dueDateTime) {
		setDocumentTime(documentTime);
		setDueDateTime(dueDateTime);
	}


	@Override
	public void validate(ValidationResult result) {
		//noop
	}


	public ZonedDateTime getDocumentTime() {
		return documentTime;
	}


	public void setDocumentTime(ZonedDateTime documentTime) {
		this.documentTime = documentTime;
	}


	public ZonedDateTime getDueDateTime() {
		return dueDateTime;
	}


	public void setDueDateTime(ZonedDateTime dueDateTime) {
		this.dueDateTime = dueDateTime;
	}


	@Override
	public int hashCode() {
		return Objects.hash(documentTime, dueDateTime);
	}


	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Context) {
			final Context other = (Context)obj;
			return new EqualsBuilder().append(documentTime, other.documentTime).append(dueDateTime, other.dueDateTime).isEquals();
		}
		return false;
	}

}
