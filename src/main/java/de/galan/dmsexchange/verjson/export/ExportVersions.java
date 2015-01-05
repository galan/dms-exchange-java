package de.galan.dmsexchange.verjson.export;

import de.galan.dmsexchange.verjson.DmsExchangeVersions;


/**
 * Version transformation steps for exports.
 *
 * @author daniel
 */
public class ExportVersions extends DmsExchangeVersions {

	@Override
	public void configure() {
		super.configure();
		// steps
		add(1L, createValidation("export", "1.0.0-beta.3"));
	}

}
