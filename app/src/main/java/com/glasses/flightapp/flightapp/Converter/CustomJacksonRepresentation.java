package com.glasses.flightapp.flightapp.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import org.restlet.data.MediaType;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;

/**
 * custom jackson representation to inject app-specific modules
 *
 * @param <T>
 */
public class CustomJacksonRepresentation<T> extends JacksonRepresentation<T> {
    public CustomJacksonRepresentation(MediaType mediaType, T object) {
        super(mediaType, object);
    }

    public CustomJacksonRepresentation(Representation representation, Class<T> objectClass) {
        super(representation, objectClass);
    }

    public CustomJacksonRepresentation(T object) {
        super(object);
    }

    @Override
    protected ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = super.createObjectMapper();

        //JSR310: allows correct parsing of java.time objects
        objectMapper.registerModule(new JSR310Module());

        return objectMapper;
    }
}