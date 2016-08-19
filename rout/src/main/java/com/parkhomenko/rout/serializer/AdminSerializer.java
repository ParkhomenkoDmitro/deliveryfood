package com.parkhomenko.rout.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.parkhomenko.common.domain.Admin;

import java.io.IOException;

/**
 * @author Dmytro Parkhomenko
 * Created on 01.08.16.
 */

public class AdminSerializer extends StdSerializer<Admin> {

    public AdminSerializer(Class<Admin> t) {
        super(t);
    }

    @Override
    public void serialize(Admin user, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", user.getId());
        jsonGenerator.writeStringField("name", user.getName());
        jsonGenerator.writeEndObject();
    }
}
