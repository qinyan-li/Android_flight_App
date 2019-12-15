package com.glasses.flightapp.flightapp.Converter;

import org.restlet.data.MediaType;
import org.restlet.ext.jackson.JacksonConverter;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;

/**
 * inject custom jackson representation to add app-specific modules
 */
public class CustomJacksonConverter extends JacksonConverter {
    protected <T> JacksonRepresentation<T> create(MediaType mediaType, T source) {
        return new CustomJacksonRepresentation<>(mediaType, source);
    }

    protected <T> JacksonRepresentation<T> create(Representation source,
                                                  Class<T> objectClass) {
        return new CustomJacksonRepresentation<>(source, objectClass);
    }
}