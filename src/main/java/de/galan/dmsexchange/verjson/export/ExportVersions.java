package de.galan.dmsexchange.verjson.export;

import java.io.IOException;
import java.net.URL;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import de.galan.verjson.core.Versions;
import de.galan.verjson.step.validation.Validation;


/**
 * Version transformation steps for export.
 *
 * @author daniel
 */
public class ExportVersions extends Versions {

	@Override
	public void configure() {
		add(1L, createValidation("1.0.0-beta.2"));
		// nothing so far
	}


	protected Validation createValidation(String version) {
		try {
			URL url = Resources.getResource(getClass(), "export-" + version + ".schema.json");
			String schema = Resources.toString(url, Charsets.UTF_8);
			return new Validation(schema, version);
		}
		catch (IOException ex) {
			throw new RuntimeException("Unable to read schema vor version '" + version + "'");
		}
	}

}
