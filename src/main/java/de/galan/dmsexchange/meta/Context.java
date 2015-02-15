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

	private ZonedDateTime tsDocument;
	private ZonedDateTime tsDueDate;


	public Context() {
		//nada
	}


	public Context(ZonedDateTime tsDocument, ZonedDateTime tsDueDate) {
		setTsDocument(tsDocument);
		setTsDueDate(tsDueDate);
	}


	@Override
	public void validate(ValidationResult result) {
		//noop
	}


	public ZonedDateTime getTsDocument() {
		return tsDocument;
	}


	public void setTsDocument(ZonedDateTime tsDocument) {
		this.tsDocument = tsDocument;
	}


	public ZonedDateTime getTsDueDate() {
		return tsDueDate;
	}


	public void setTsDueDate(ZonedDateTime tsDueDate) {
		this.tsDueDate = tsDueDate;
	}


	@Override
	public int hashCode() {
		return Objects.hash(tsDocument, tsDueDate);
	}


	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Context) {
			final Context other = (Context)obj;
			return new EqualsBuilder().append(tsDocument, other.tsDocument).append(tsDueDate, other.tsDueDate).isEquals();
		}
		return false;
	}

}
