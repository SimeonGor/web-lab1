package com.simeon;

import com.simeon.dto.Request;
import com.simeon.dto.Response;
import com.simeon.dto.ResponseEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckAreaControllerTest {
    private static CheckAreaController controller;

    @BeforeAll
    public static void setUp() {
        controller = new CheckAreaController();
    }

    @Test
    public void shouldReturnIsNotHit() {
        Request request = new Request();
        request.setY(-1);
        request.setX(-0.9);
        request.setR(1);

        ResponseEntity<?> responseEntity = controller.handle(request);

        assertFalse(((Response) responseEntity.getBody()).isHit());
    }
}