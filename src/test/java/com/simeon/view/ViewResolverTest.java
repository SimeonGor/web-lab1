package com.simeon.view;

import com.simeon.MessageConverter;
import com.simeon.dto.Response;
import com.simeon.dto.ResponseEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ViewResolverTest {
    private static ViewResolver viewResolver;

    @BeforeAll
    public static void setUp() {
        viewResolver = new ViewResolver(new MessageConverter());
    }

    @Test
    public void shouldReturnValidHttpResponse() {
        Response response = new Response();
        response.setX(new BigDecimal("2.1"));
        response.setY(new BigDecimal("2.5"));
        response.setR(new BigDecimal("9"));
        response.setHit(true);
        response.setCreated_at(LocalDateTime.of(1995, 12, 14, 23, 43, 17));
        response.setWorking_time(1234);

        String expectedJson = """
                {
                    "r": 1.0,
                    "x": 2.1,
                    "y": 2.5,
                    "hit": true,
                    "createdTime": "1995-12-14T23:43:17",
                    "workingTime": 5000
                }""";

        String expectedHttpResponse = """
                HTTP/1.1 200 OK
                Content-Type: application/json
                Content-Length: %d
                                
                %s
                
                """.formatted(expectedJson.getBytes(StandardCharsets.UTF_8).length, expectedJson);

        String httpResponse = viewResolver.getHttpResponse(ResponseEntity.builder().body(response).build());

        Assertions.assertEquals(expectedHttpResponse, httpResponse);
    }
}