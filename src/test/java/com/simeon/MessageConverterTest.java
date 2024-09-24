package com.simeon;

import com.simeon.dto.Request;
import com.simeon.dto.Response;
import com.simeon.exceptions.SerializationException;
import net.javacrumbs.jsonunit.assertj.JsonAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.fail;

class MessageConverterTest {
    private static MessageConverter messageConverter;

    @BeforeAll
    public static void setUp() {
        messageConverter = new MessageConverter();
    }

    @Test
    public void shouldConvertJsonToRequest() {
        String json = """
                {
                    "r": 1.0,
                    "x": 2.1,
                    "y": 2.5
                }""";
        Request expected = new Request();
        expected.setR(new BigDecimal("1.0"));
        expected.setX(new BigDecimal("2.1"));
        expected.setY(BigDecimal.valueOf(2.5));

        try {
            Request actual = messageConverter.deserialize(json, Request.class);
            Assertions.assertEquals(expected, actual);
        } catch (SerializationException e) {
            fail(e);
        }
    }

    @Test
    public void shouldIgnoreFieldsJsonToRequest() {
        String json = """
                {
                    "r": 1.0,
                    "x": 2.1,
                    "y": 2.5,
                    "additionalField": "something"
                }""";
        Request expected = new Request();
        expected.setR(new BigDecimal("1.0"));
        expected.setX(new BigDecimal("2.1"));
        expected.setY(new BigDecimal("2.5"));

        try {
            Request actual = messageConverter.deserialize(json, Request.class);
            Assertions.assertEquals(expected, actual);
        } catch (SerializationException e) {
            fail(e);
        }
    }

    @Test
    public void shouldConvertPartialJsonToRequest() {
        String json = """
                {
                    "x": 2.1,
                    "y": 2.5
                }""";
        Request expected = new Request();
        expected.setR(new BigDecimal("0.0"));
        expected.setX(new BigDecimal("2.1"));
        expected.setY(new BigDecimal("2.5"));

        try {
            Request actual = messageConverter.deserialize(json, Request.class);
            Assertions.assertEquals(expected, actual);
        } catch (SerializationException e) {
            fail(e);
        }
    }

    @Test
    public void shouldConvertResponseToJson() {
        Response response = new Response();
        response.setR(new BigDecimal("1.0"));
        response.setX(new BigDecimal("2.1"));
        response.setY(new BigDecimal("2.5"));
        response.setHit(true);
        response.setCreated_at(LocalDateTime.of(1995, 12, 14, 23, 43, 17));
        response.setWorking_time(5000);

        String expectedJson = """
                {
                    "r": 1.0,
                    "x": 2.1,
                    "y": 2.5,
                    "hit": true,
                    "createdTime": "1995-12-14T23:43:17",
                    "workingTime": 5000
                }""";

        try {
            String actualJson = messageConverter.serialize(response);
            JsonAssertions.assertThatJson(actualJson).isEqualTo(expectedJson);
        } catch (SerializationException e) {
            fail(e);
        }
    }
}