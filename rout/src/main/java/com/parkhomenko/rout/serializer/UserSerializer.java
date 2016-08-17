package com.parkhomenko.rout.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.parkhomenko.common.domain.User;

import java.io.IOException;

/**
 * @author Dmytro Parkhomenko
 * Created on 01.08.16.
 */

public class UserSerializer extends StdSerializer<User> {

    public UserSerializer(Class<User> t) {
        super(t);
    }

    @Override
    public void serialize(User user, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", user.getId());
        jsonGenerator.writeStringField("name", user.getName());
        jsonGenerator.writeEndObject();
    }
}
