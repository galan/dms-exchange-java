package de.galan.dmsexchange.verjson.document;

import de.galan.dmsexchange.verjson.serializer.UserDeserializer;
import de.galan.dmsexchange.verjson.serializer.UserSerializer;
import de.galan.verjson.core.Versions;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class DocumentVersions extends Versions {

	@Override
	public void configure() {
		// custom DeSerializer
		registerSerializer(new UserSerializer());
		registerDeserializer(new UserDeserializer());

		// Add transformations in future versions
	}

}
