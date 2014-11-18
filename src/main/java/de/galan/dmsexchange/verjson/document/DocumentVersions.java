package de.galan.dmsexchange.verjson.document;

import de.galan.dmsexchange.verjson.DmsExchangeVersions;


/**
 * daniel should have written a comment here.
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
