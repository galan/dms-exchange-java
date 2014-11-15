package de.galan.dmsexchange.verjson.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import de.galan.dmsexchange.meta.document.User;


/**
 * Deserializes a User.
 *
 * @author daniel
 */
public class UserDeserializer extends JsonDeserializer<User> {

	@Override
	public User deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		return new User(jp.getText());
	}

}
