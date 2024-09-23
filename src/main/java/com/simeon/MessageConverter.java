package com.simeon;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simeon.dto.Request;
import com.simeon.exceptions.SerializationException;
import lombok.SneakyThrows;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

public class MessageConverter {
    public final ObjectMapper objectMapper;
    public MessageConverter() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.findAndRegisterModules();
    }

    public <T> T deserialize(String stringRequest, Class<T> type) {
        try {
            return objectMapper.readValue(stringRequest, type);
        } catch (JsonProcessingException e) {
            throw new SerializationException(e);
        }
    }

    public String serialize(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new SerializationException(e);
        }
    }
}
