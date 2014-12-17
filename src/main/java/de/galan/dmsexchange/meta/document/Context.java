package de.galan.dmsexchange.meta.document;

import java.time.ZonedDateTime;


/**
 * Information that are inherit to the document-content
 *
 * @author daniel
 */
public class Context {

	private ZonedDateTime tsDocument;
	private ZonedDateTime tsDueDate;


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
