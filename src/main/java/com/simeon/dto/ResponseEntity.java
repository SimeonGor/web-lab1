package com.simeon.dto;

import com.simeon.HttpStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Builder
public class ResponseEntity<T> {
    @Builder.Default
    private final HttpStatus status = HttpStatus.OK;
    private final T body;
    @Builder.Default
    private final Map<String, String> headers = new HashMap<>();
}
