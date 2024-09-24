package com.simeon.view;

import com.simeon.HttpStatus;
import com.simeon.MessageConverter;
import com.simeon.dto.ResponseEntity;
import com.simeon.exceptions.SerializationException;
import lombok.AllArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@AllArgsConstructor
public class ViewResolver {
    public final MessageConverter messageConverter;

    public <T> String getHttpResponse(ResponseEntity<T> responseEntity) {
        try {
            return tryGetHttpReponse(responseEntity);
        } catch (SerializationException e) {
            return getServerErrorResponse();
        }
    }

    private <T> String tryGetHttpReponse(ResponseEntity<T> responseEntity) {
        String statusLine = getStatusLine(responseEntity.getStatus());
        String headers = getResponseHeaders(responseEntity.getHeaders());
        String body = getResponseBody(responseEntity.getBody());

        return """
                HTTP/1.1 %s
                %sContent-Type: application/json
                Content-Length: %d
                                
                %s
                                
                """.formatted(statusLine, headers, body.getBytes(StandardCharsets.UTF_8).length, body);

    }

    private String getServerErrorResponse() {
        return """
                HTTP/1.1 500 Internal Server Error
                """;
    }

    private String getStatusLine(HttpStatus status) {
        return "%d %s".formatted(status.getStatusCode(), status.getStatusText());
    }

    private String getResponseHeaders(Map<String, String> headers) {
        StringBuilder stringBuilder = new StringBuilder();
        headers.forEach((key, value) -> stringBuilder
                .append(key)
                .append(": ")
                .append(value)
                .append("\n"));

        return stringBuilder.substring(0, stringBuilder.length());
    }

    private <T> String getResponseBody(T body) {
        return messageConverter.serialize(body);
    }
}
