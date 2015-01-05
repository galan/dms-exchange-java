package de.galan.dmsexchange.meta.document;

import java.time.ZonedDateTime;

import de.galan.dmsexchange.meta.Validatable;
import de.galan.dmsexchange.meta.ValidationResult;


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

}
