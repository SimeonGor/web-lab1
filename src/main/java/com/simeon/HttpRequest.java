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
    private final String uri;
    @Getter
    private final String method;
    @Getter
    private final Map<String, String> queryParameters = new HashMap<>();
    @Getter
    private final String body;


    public HttpRequest(String method, String uri, String queryString, String body) {
        this.method = method;
        this.uri = uri;
        this.body = body;
        parseQueryString(queryString);
    }

    private void parseQueryString(String queryString) {
        if (queryString != null && !queryString.isBlank()) {
            String[] params = queryString.split("&");
            for (var keyValue : params) {
                String[] pair = keyValue.split("=");
                this.queryParameters.put(pair[0], pair[1]);
            }
        }
    }
}
