package de.galan.dmsexchange;

import de.galan.dmsexchange.meta.document.Document;
import de.galan.verjson.core.Verjson;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class Something {

	public static void main(String[] args) {
		Verjson<Document> verjson = Verjson.create(Document.class, null);
	}

}
