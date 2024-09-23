package com.simeon;

import com.simeon.exceptions.HttpFormatException;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    @Getter
    private String uri = "";
    @Getter
    private String method = "";
    @Getter
    private final Map<String, String> queryParameters = new HashMap<>();
    @Getter
    private String body;


    public HttpRequestParser(String method, String uri, String queryString) {
        parseQueryString(queryString);
    }

    private void parse(String httpRequest) {
        BufferedReader reader = new BufferedReader(new StringReader(httpRequest));

        parseRequestLine(reader);
        parseHeaders(reader);

        parseBody(reader);
    }

    private void parseRequestLine(BufferedReader reader) {
        String line = null;
        try {
            line = reader.readLine();
            String[] parts = line.split(" ");
            this.method = parts[0];
            parseURI(parts[1]);
        } catch (Exception e) {
            throw new HttpFormatException("Invalid Request-Line: %s".formatted(line), e);
        }

    }

    private void parseHeaders(BufferedReader reader) {
        String header = null;
        try {
            for (header = reader.readLine();
                 !header.isBlank();
                 header = reader.readLine()) {
                String[] keyValue = header.split(":");
                headers.put(keyValue[0].trim(), keyValue[1].trim());
            }
        } catch (Exception e) {
            throw new HttpFormatException("Invalid Header Parameter: %s".formatted(header), e);
        }
    }

    private void parseBody(BufferedReader reader) {
        try {
            StringBuilder bodyBuilder = new StringBuilder();
            for (String line = reader.readLine();
                    line != null;
                    line = reader.readLine()) {
                bodyBuilder.append(line);
                bodyBuilder.append("\n");
            }
            body = bodyBuilder.toString();
        } catch (IOException e) {
            throw new HttpFormatException("Invalid Request Body", e);
        }
    }

    private void parseURI(String uri) {
        int sep = uri.indexOf("?");
        this.uri = uri.substring(0, sep);
        parseQueryString(uri.substring(sep + 1));
    }

    private void parseQueryString(String queryString) {
        String[] params = queryString.split("&");
        for (var keyValue : params) {
            String[] pair = keyValue.split("=");
            this.queryParameters.put(pair[0], pair[1]);
        }

    }
}
