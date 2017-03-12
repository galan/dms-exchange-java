package de.galan.dmsexchange.verjson;

import static java.nio.charset.StandardCharsets.*;

import java.io.IOException;
import java.net.URL;

import com.google.common.io.Resources;

import de.galan.dmsexchange.verjson.serializer.UserDeserializer;
import de.galan.dmsexchange.verjson.serializer.UserSerializer;
import de.galan.verjson.core.Versions;
import de.galan.verjson.step.validation.Validation;


/**
 * Common functionality for verjson transformation registrations.
 *
 * @author daniel
 */
public abstract class DmsExchangeVersions extends Versions {

	@Override
	public void configure() {
		// custom DeSerializer
		registerSerializer(new UserSerializer());
		registerDeserializer(new UserDeserializer());
	}


	protected Validation createValidation(String metafile, String version) {
		try {
			URL url = Resources.getResource(getClass(), metafile + "-" + version + ".schema.json");
			String schema = Resources.toString(url, UTF_8);
			return new Validation(schema, version);
		}
		catch (IOException ex) {
			throw new RuntimeException("Unable to read schema vor version '" + version + "'");
		}
	}

}
