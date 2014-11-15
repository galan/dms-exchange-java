package de.galan.dmsexchange.verjson.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import de.galan.dmsexchange.meta.document.User;


/**
 * Serializes a user (email)
 *
 * @author daniel
 */
public class UserSerializer extends JsonSerializer<User> {

	@Override
	public void serialize(User value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		jgen.writeString(value.getEmail());
	}


	@Override
	public Class<User> handledType() {
		return User.class;
	}

}
