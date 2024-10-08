package com.simeon;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class HttpRequest {
    private final String uri;
    private final String method;
    private final Map<String, String> queryParameters = new HashMap<>();
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
