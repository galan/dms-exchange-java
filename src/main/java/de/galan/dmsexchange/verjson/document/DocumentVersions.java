package de.galan.dmsexchange.verjson.document;

import de.galan.dmsexchange.verjson.DmsExchangeVersions;


/**
 * Verjson transformation steps for documents.
 *
 * @author daniel
 */
public class DocumentVersions extends DmsExchangeVersions {

	@Override
	public void configure() {
		super.configure();
		// steps
		add(1L, createValidation("meta", "1.0.0-beta.3"));
	}

}
