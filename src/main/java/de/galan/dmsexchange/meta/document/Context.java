package de.galan.dmsexchange.meta.document;

import java.time.ZonedDateTime;

import de.galan.dmsexchange.meta.Validatable;
import de.galan.dmsexchange.meta.ValidationResult;


/**
 * Information that are inherit to the document-content
 *
 * @author daniel
 */
public class Context implements Validatable {

	private ZonedDateTime tsDocument;
	private ZonedDateTime tsDueDate;


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

}
